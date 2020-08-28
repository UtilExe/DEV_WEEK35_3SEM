package facades;

import dto.CustomerDTO;
import entities.BankCustomer;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class BankFacade {

    private static BankFacade instance;
    private static EntityManagerFactory emf;
    
    //Private Constructor to ensure Singleton
    private BankFacade() {}
    
    
    /**
     * 
     * @param _emf
     * @return an instance of this facade class.
     */
    public static BankFacade getFacadeExample(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new BankFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public CustomerDTO getCustomerByID(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            BankCustomer customer = em.find(BankCustomer.class, id);
            return new CustomerDTO(customer);
        } finally {
            em.close();
        }
    }

    public CustomerDTO getCustomerByName(String firstName) {
        EntityManager em = emf.createEntityManager();
        try {
            BankCustomer query = em.createQuery(
                    "SELECT p from BankCustomer p WHERE p.firstName = :firstName", BankCustomer.class).
                    setParameter("firstName", firstName).getSingleResult();
            // Consider changing to getResultList(), so it displays the list with all customers that has lastName X. 
            return new CustomerDTO(query);
        } catch (NoResultException nre) {
            /* If the lastName doesn't exist, it will result in an Exception crash.
           Added an exception check to prevent it from crashing. persistence.NoResultException.
             */
            System.out.println("Could not find: " + firstName);
            return null;
        } finally {
            em.close();
        }
    }
    
    public BankCustomer addCustomer(BankCustomer cust) {
        BankCustomer customer = new BankCustomer(cust.getFirstName(), cust.getLastName(), 
                cust.getAccountNumber(),  cust.getBalance(), cust.getCustomerRanking(), cust.getInternalInfo());
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(cust);
            em.getTransaction().commit();
            return customer;
          //  return result;
        } finally {
            em.close();
        }
    }

    public List<BankCustomer> getAllBankCustomers() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<BankCustomer> query 
                    = em.createQuery("Select p from BankCustomer p", BankCustomer.class);
            List<BankCustomer> result = query.getResultList();

            return result;
        } finally {
            em.close();
        }
    }

}
