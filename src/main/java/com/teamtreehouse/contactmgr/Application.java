package com.teamtreehouse.contactmgr;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javax.imageio.spi.ServiceRegistry;

/**
 * Created by charl on 6/20/2017.
 */
public class Application {
    //Hold reusable reference to SessionFactory (since we only need one)
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        //Create standard service registry object
        final ServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
    }

    public static void  main(String[] args) {

    }
}
