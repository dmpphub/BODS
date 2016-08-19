package com.dataprocess.bods.util.connectionutil;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public final class HibernateSessionManager {

    public static Session getHibernateSession() throws Exception {
        Session session = null;
        Configuration configuration = null;
        try {
            configuration = new Configuration();
            // Pass hibernate configuration file
            configuration.configure("hibernate.cfg.xml");

            // Since version 4.x, service registry is being used
            ServiceRegistry serviceRegistry =
                new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();

            // Create session factory instance
            SessionFactory factory = configuration.buildSessionFactory(serviceRegistry);

            session = factory.openSession();

        } catch (Exception exception) {
            exception.printStackTrace();
            throw exception;
        }
        return session;

    }

    public static void main(String[] args) throws Exception {
        // new HibernateSessionManager().getHibernateSession();
    }

}
