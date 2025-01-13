package bstefanov.transportOrg.dao;

import bstefanov.transportOrg.configuration.SessionFactoryUtil;
import bstefanov.transportOrg.dto.QualificationDto;
import bstefanov.transportOrg.entity.Qualification;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class QualificationsDao {
    public static void addQualification(String qualification) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            Qualification qualificationEntity = new Qualification(qualification);

            session.persist(qualificationEntity);
            session.getTransaction().commit();
        }
    }

    public static List<QualificationDto> getQualifications() {
        List<QualificationDto> qualifications = new ArrayList<>();
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createQuery("from Qualification", Qualification.class).list().forEach(qualification ->
                    qualifications.add(new QualificationDto(qualification.getId(), qualification.getName())));
        }
        return qualifications;
    }

    public static void updateQualification(QualificationDto qualification) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Qualification qualificationEntity = session.get(Qualification.class, qualification.getId());
            qualificationEntity.setName(qualification.getName());
            session.merge(qualificationEntity);
            session.getTransaction().commit();
        }
    }

    public static void deleteQualification(long id) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Qualification qualification = session.get(Qualification.class, id);
            // TODO: Handle foreign key constraint violation
            session.remove(qualification);
            session.getTransaction().commit();
        }
    }
}
