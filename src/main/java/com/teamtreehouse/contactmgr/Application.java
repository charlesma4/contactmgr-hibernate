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
        int id = save(contact);

        //Display list of contacts before the update
        System.out.printf("%n%nBefore Update:%n%n");
        fetchAllContacts().stream().forEach(System.out::println);

        //Get persisted contact
        Contact c = findContactById(id);

        //Update contact
        c.setFirstName("max");
        c.setLastName("willis");
        c.setEmail("mwillis@gmail.com");
        c.setPhone(9876543210L);

        //Persist the changes
        System.out.printf("%n%nUpdating...%n%n");
        update(c);
        System.out.printf("%n%nUpdate complete!%n%n");

        //Display updated list of products
        fetchAllContacts().stream().forEach(System.out::println);

    }

    private static Contact findContactById(int id) {
        // Open a session
        Session session = sessionFactory.openSession();

        //Retrieve persistent object (or null if not found)
        Contact contact = session.get(Contact.class, id);

        //Close session
        session.close();

        //Return object
        return contact;
    }

    private static void update(Contact contact) {
        //Open a session
        Session session = sessionFactory.openSession();

        //Begin a transaction
        session.beginTransaction();

        //Update the contact
        session.update(contact);

        //Commit the transaction
        session.getTransaction().commit();

        //Close the session
        session.close();
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

    public static int save(Contact contact) {
        //Open session
        Session session = sessionFactory.openSession();

        //Begin transaction
        session.beginTransaction();

        //Use session to save contact
        int id = (int) session.save(contact);

        //Commit transaction
        session.getTransaction().commit();

        //Close session
        session.close();

        return id;
    }
}
