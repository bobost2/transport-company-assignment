package bstefanov.transportOrg.dao;

import bstefanov.transportOrg.configuration.SessionFactoryUtil;
import bstefanov.transportOrg.dto.*;
import bstefanov.transportOrg.entity.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

// Or transitDao
public class RouteDao {
    public static void createRoute(RouteDto route, LinkClientDto clientDto, List<RouteExpenseDto> expenses,
                                   BaseCargo cargo, List<QualificationDto> qualifications) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            Company company = session.get(Company.class, route.getCompanyId());
            Vehicle vehicle = session.get(Vehicle.class, route.getVehicleId());
            Employee employee = session.get(Employee.class, route.getEmployeeId());
            Transit transit = new Transit(route.getStartingPoint(), route.getDestinationPoint(), route.getDistance(),
                    route.getDepartureTime(), route.getArrivalTime(), company, vehicle, employee);
            session.persist(transit);

            cargo.setTransit(transit);
            session.persist(cargo);

            for (QualificationDto qualificationDto : qualifications) {
                Qualification qualification = session.get(Qualification.class, qualificationDto.getId());
                cargo.getQualifications().add(qualification);
            }
            session.merge(cargo);

            Client client = session.get(Client.class, clientDto.getClientId());
            TransitClient transitClient = new TransitClient(clientDto.getPrice(), false, transit, client);
            session.persist(transitClient);

            for (RouteExpenseDto expenseDto : expenses) {
                TransitSpendings spending = new TransitSpendings(expenseDto.getAmount(), expenseDto.getReason(), transit);
                if (expenseDto.isEmployee()) {
                    Employee expenseEmployee = session.get(Employee.class, expenseDto.getEmployeeId());
                    spending.setEmployee(expenseEmployee);
                }
                session.persist(spending);
            }

            transaction.commit();
        }
    }

    public static List<RouteDto> getTransits(long companyId) {
        List<RouteDto> routes = new ArrayList<>();

        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Transit> criteria = cb.createQuery(Transit.class);
            Root<Transit> root = criteria.from(Transit.class);

            criteria.select(root).where(cb.equal(root.get("company").get("id"), companyId));
            criteria.orderBy(cb.desc(root.get("distance")));

            List<Transit> transits = session.createQuery(criteria).getResultList();

            for (Transit transit : transits) {
                routes.add(new RouteDto(transit.getStartingPoint(), transit.getDestinationPoint(), transit.getDistance(),
                        transit.getDepartureTime(), transit.getArrivalTime(), companyId, transit.getVehicle().getId(),
                        transit.getEmployee().getId()));
            }
        }

        return routes;
    }

    public static RouteDto getTransit(long transitId) {
        RouteDto route = null;

        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            Transit transit = session.get(Transit.class, transitId);

            if (transit != null) {
                route = new RouteDto(transit.getStartingPoint(), transit.getDestinationPoint(), transit.getDistance(),
                        transit.getDepartureTime(), transit.getArrivalTime(), transit.getCompany().getId(),
                        transit.getVehicle().getId(), transit.getEmployee().getId());
            }
        }

        return route;
    }

    public static List<RouteCostDto> getTransitCost(long companyId) {
        List<RouteCostDto> routes = new ArrayList<>();

        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<RouteCostDto> criteria = cb.createQuery(RouteCostDto.class);
            Root<Transit> root = criteria.from(Transit.class);
            Join<Transit, TransitSpendings> spendingsJoin = root.join("transitSpendings");

            //RouteCostDto contains the id of the route and the sum of all spendings
            criteria.select(cb.construct(RouteCostDto.class, root.get("id"), cb.sum(spendingsJoin.get("amount"))))
                    .where(cb.equal(root.get("company").get("id"), companyId))
                    .groupBy(root.get("id"));

            routes = session.createQuery(criteria).getResultList();
        }

        return routes;
    }
}
