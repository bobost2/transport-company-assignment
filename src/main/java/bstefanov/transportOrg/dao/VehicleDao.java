package bstefanov.transportOrg.dao;

import bstefanov.transportOrg.configuration.SessionFactoryUtil;
import bstefanov.transportOrg.dto.VehicleDto;
import bstefanov.transportOrg.entity.Company;
import bstefanov.transportOrg.entity.Vehicle;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class VehicleDao {
    public static void createVehicle(VehicleDto vehicle) {
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Company company = session.get(Company.class, vehicle.getCompanyId());
            Vehicle vehicleEntity = new Vehicle(vehicle.getLicensePlate(), vehicle.getType(), vehicle.getBrand(),
                    vehicle.getModel(), vehicle.getFuelPricePerKM(), company);

            session.persist(vehicleEntity);
            transaction.commit();
        }
    }

    public static List<VehicleDto> getVehicles(long companyId) {
        List<VehicleDto> vehicles = new ArrayList<>();
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Vehicle> criteria = cb.createQuery(Vehicle.class);
            Root<Vehicle> root = criteria.from(Vehicle.class);
            criteria.select(root).where(cb.equal(root.get("company").get("id"), companyId));

            List<Vehicle> vehicleEntities = session.createQuery(criteria).getResultList();
            for (Vehicle vehicle : vehicleEntities) {
                vehicles.add(new VehicleDto(
                        vehicle.getId(),
                        vehicle.getLicensePlate(),
                        vehicle.getType(),
                        vehicle.getBrand(),
                        vehicle.getModel(),
                        vehicle.getFuelPricePerKM(),
                        vehicle.getCompany().getId()
                ));
            }
        }
        return vehicles;
    }

    public static void editVehicle(VehicleDto vehicle) {
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Vehicle vehicleEntity = session.get(Vehicle.class, vehicle.getId());
            vehicleEntity.setLicensePlate(vehicle.getLicensePlate());
            vehicleEntity.setType(vehicle.getType());
            vehicleEntity.setBrand(vehicle.getBrand());
            vehicleEntity.setModel(vehicle.getModel());
            vehicleEntity.setFuelPricePerKM(vehicle.getFuelPricePerKM());
            session.merge(vehicleEntity);
            transaction.commit();
        }
    }

    public static void deleteVehicle(long id) {
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Vehicle vehicle = session.get(Vehicle.class, id);
            session.remove(vehicle);
            transaction.commit();
        }
    }
}
