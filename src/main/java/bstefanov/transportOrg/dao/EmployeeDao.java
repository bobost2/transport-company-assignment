package bstefanov.transportOrg.dao;

import bstefanov.transportOrg.configuration.SessionFactoryUtil;
import bstefanov.transportOrg.data.EmployeeSort;
import bstefanov.transportOrg.dto.EmployeeDto;
import bstefanov.transportOrg.dto.QualificationDto;
import bstefanov.transportOrg.entity.Company;
import bstefanov.transportOrg.entity.Employee;
import bstefanov.transportOrg.entity.Qualification;
import bstefanov.transportOrg.entity.Vehicle;
import jakarta.persistence.criteria.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.sql.ast.tree.predicate.Predicate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EmployeeDao {
    public static void createEmployee(EmployeeDto employee) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            Company company = session.get(Company.class, employee.getCompanyId());
            Employee employeeEntity = new Employee(employee.getFirstName(), employee.getLastName(),
                    employee.getPayRateByHour(), company);

            // Get qualifications from db
            Set<Qualification> qualifications = new HashSet<>();
            if (employee.getQualifications() != null && !employee.getQualifications().isEmpty()) {
                for (QualificationDto qualificationDto : employee.getQualifications()) {
                    Qualification qualification = session.get(Qualification.class, qualificationDto.getId());
                    qualifications.add(qualification);
                }
            }

            employeeEntity.setQualifications(qualifications);

            session.persist(employeeEntity);
            transaction.commit();
        }
    }

    public static List<EmployeeDto> getEmployees(long companyId, EmployeeSort sort,
                                                 ArrayList<Long> qualificationFilterIds) {
        List<EmployeeDto> employees = new ArrayList<>();
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Employee> criteria = cb.createQuery(Employee.class);
            Root<Employee> root = criteria.from(Employee.class);
            Join<Employee, Qualification> qualificationsJoin = root.join("qualifications", JoinType.LEFT);

            List<jakarta.persistence.criteria.Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(root.get("company").get("id"), companyId));

            if (qualificationFilterIds != null && !qualificationFilterIds.isEmpty()) {
                predicates.add(qualificationsJoin.get("id").in(qualificationFilterIds));
            }

            criteria.select(root).where(cb.and(predicates.toArray(new jakarta.persistence.criteria.Predicate[0])));

            switch (sort) {
                case PROFIT_ASC -> criteria.orderBy(cb.asc(root.get("payRateByHour")));
                case PROFIT_DESC -> criteria.orderBy(cb.desc(root.get("payRateByHour")));
            }

            List<Employee> employeeList = session.createQuery(criteria).getResultList();

            for (Employee employee : employeeList) {
                employees.add(new EmployeeDto(
                        employee.getId(),
                        employee.getFirstName(),
                        employee.getLastName(),
                        employee.getPayRateByHour(),
                        new ArrayList<>(),
                        employee.getCompany().getId()
                ));

                for (Qualification qualification : employee.getQualifications()) {
                    employees.get(employees.size() - 1).getQualifications().add(
                            new QualificationDto(qualification.getId(), qualification.getName())
                    );
                }
            }
        }
        return employees;
    }

    public static void updateEmployee(EmployeeDto employee) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Employee employeeEntity = session.get(Employee.class, employee.getId());
            employeeEntity.setFirstName(employee.getFirstName());
            employeeEntity.setLastName(employee.getLastName());
            employeeEntity.setPayRateByHour(employee.getPayRateByHour());

            Company company = session.get(Company.class, employee.getCompanyId());
            employeeEntity.setCompany(company);

            // Get qualifications from db
            Set<Qualification> qualifications = new HashSet<>();
            if (employee.getQualifications() != null && !employee.getQualifications().isEmpty()) {
                for (QualificationDto qualificationDto : employee.getQualifications()) {
                    Qualification qualification = session.get(Qualification.class, qualificationDto.getId());
                    qualifications.add(qualification);
                }
            }

            employeeEntity.setQualifications(qualifications);

            session.merge(employeeEntity);
            transaction.commit();
        }
    }

    public static void deleteEmployee(long id) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Employee employeeEntity = session.get(Employee.class, id);
            session.remove(employeeEntity);
            transaction.commit();
        }
    }
}
