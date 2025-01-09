package bstefanov.transportOrg.configuration;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class SessionFactoryUtil {
    private static SessionFactory sessionFactory;
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            Configuration configuration = new Configuration();

            configuration.addAnnotatedClass(bstefanov.transportOrg.entity.Company.class);
            configuration.addAnnotatedClass(bstefanov.transportOrg.entity.Employee.class);
            configuration.addAnnotatedClass(bstefanov.transportOrg.entity.Vehicle.class);
            configuration.addAnnotatedClass(bstefanov.transportOrg.entity.Qualification.class);
            configuration.addAnnotatedClass(bstefanov.transportOrg.entity.Transit.class);
            configuration.addAnnotatedClass(bstefanov.transportOrg.entity.BaseCargo.class);
            configuration.addAnnotatedClass(bstefanov.transportOrg.entity.TransitSpendings.class);

            ServiceRegistry serviceRegistry
                    = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();

            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        }
        return sessionFactory;
    }
}
