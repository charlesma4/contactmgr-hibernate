package com.teamtreehouse.contactmgr;

import com.teamtreehouse.contactmgr.model.Contact;
import com.teamtreehouse.contactmgr.model.Contact.ContactBuilder;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

import java.util.List;

/**
 * Created by charl on 6/20/2017.
 */
public class Application {
    //Hold reusable reference to SessionFactory (since we only need one)
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        //Create standard service registry object
        final ServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        return new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }

    public static void  main(String[] args) {
        Contact contact = new ContactBuilder("Charles","Ma")
                .withEmail("cma4@bu.edu")
                .withPhone(8575403371L)
                .build();
        save(contact);

        //Display list of contacts
        for(Contact c: fetchAllContacts()) {
            System.out.println(c);
        }
    }

    @SuppressWarnings("unchecked")
    private static List<Contact> fetchAllContacts() {
        //Open session
        Session session = sessionFactory.openSession();

        //Create criteria
        Criteria criteria = session.createCriteria(Contact.class);

        //Store list of contact objects based on criteria
        List<Contact> contacts = criteria.list();

        //Close session
        session.close();

        return contacts;
    }

    public static void save(Contact contact) {
        //Open session
        Session session = sessionFactory.openSession();

        //Begin transaction
        session.beginTransaction();

        //Use session to save contact
        session.save(contact);

        //Commit transaction
        session.getTransaction().commit();

        //Close session
        session.close();
    }
}
