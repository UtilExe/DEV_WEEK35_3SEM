package dbfacade;

import entity.Customer;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

public class CustomerFacade {

    private static EntityManagerFactory emf;
    private static CustomerFacade instance;

    private CustomerFacade() {
    }

    public static CustomerFacade getCustomerFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new CustomerFacade();
        }
        return instance;
    }

    public Customer addCustomer(String fName, String lName) {
        Customer customer = new Customer(fName, lName);
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(customer);
            em.getTransaction().commit();
            return customer;
        } finally {
            em.close();
        }
    }

    public Customer findByID(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            Customer customer = em.find(Customer.class, id);
            return customer;
        } finally {
            em.close();
        }
    }

    /* Source for the SQL code: https://stackoverflow.com/a/31733724 */
    public Customer findByLastName(String lastName) {
        EntityManager em = emf.createEntityManager();
        try {
            Customer customer = em.createQuery(
                    "SELECT p from Customer p WHERE p.lastName = :lastName", Customer.class).
                    setParameter("lastName", lastName).getSingleResult();
            // Consider changing to getResultList(), so it displays the list with all customers that has lastName X. 
            return customer;
        } catch (NoResultException nre) {
            /* If the lastName doesn't exist, it will result in an Exception crash.
           Added an exception check to prevent it from crashing. persistence.NoResultException.
             */
            System.out.println("Could not find: " + lastName);
            return null;
        } finally {
            em.close();
        }
    }

    public int getNumberOfCustomers() {
        EntityManager em = emf.createEntityManager();
        try {
            Query q1 = em.createQuery("Select COUNT(p) from Customer p");

            long temp = (Long) q1.getSingleResult();
            int result = (int) temp;
            return result;
        } finally {
            em.close();
        }
    }

    public List<Customer> allCustomers() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Customer> query
                    = em.createQuery("Select p from Customer p", Customer.class);
            List<Customer> results = query.getResultList();

            return results;
        } finally {
            em.close();
        }
    }

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pus");
        CustomerFacade facade = CustomerFacade.getCustomerFacade(emf);
        Customer b1 = facade.addCustomer("Hans", "Erik");
        Customer b2 = facade.addCustomer("Jens", "Jensen");
        Customer b3 = facade.addCustomer("Test", "Test2");
        
        //Find customers by ID
        System.out.println("Customer 1: " + facade.findByID(b1.getId()).getFirstName());
        System.out.println("Customer 2: " + facade.findByID(b2.getId()).getFirstName());
        System.out.println("Customer 3: " + facade.findByID(b3.getId()).getFirstName()
                + ", Last name: " + b3.getLastName());
        
        //Find all customers
        System.out.println("All customers: " + facade.allCustomers().toString());
        System.out.println("Number of Customers: " + facade.getNumberOfCustomers());
        
        System.out.println("Customer name: " + facade.findByLastName(b2.getLastName()));
        System.out.println("Customer name: " + facade.findByLastName("Erik"));
        System.out.println("Customer name: " + facade.findByLastName("asdbsadba"));
    }
}
