package bstefanov.transportOrg.dao;

import bstefanov.transportOrg.configuration.SessionFactoryUtil;
import bstefanov.transportOrg.dto.CompanyListEntityDto;
import bstefanov.transportOrg.dto.CreateCompanyDto;
import bstefanov.transportOrg.entity.Company;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.math.BigDecimal;
import java.util.ArrayList;

public class CompanyDao {
    public static void createCompany(CreateCompanyDto createCompanyDto) {
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Company company = new Company(createCompanyDto.getName(), createCompanyDto.getAddress());
            session.persist(company);
            transaction.commit();
        }
    }

    public static ArrayList<CompanyListEntityDto> getCompanies() {
        ArrayList<CompanyListEntityDto> companies = new ArrayList<>();
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createQuery("from Company", Company.class).list().forEach(company ->
                    companies.add(new CompanyListEntityDto(company.getId(), company.getName(),
                            company.getAddress(), BigDecimal.ZERO)));
        }
        return companies;
    }

    public static void editCompany(CompanyListEntityDto company) {
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Company companyEntity = session.get(Company.class, company.getId());
            companyEntity.setName(company.getName());
            companyEntity.setAddress(company.getAddress());
            session.merge(companyEntity);
            transaction.commit();
        }
    }

    public static void deleteCompany(long id) {
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Company company = session.get(Company.class, id);
            // TODO: Handle constraint violation
            session.remove(company);
            transaction.commit();
        }
    }
}
