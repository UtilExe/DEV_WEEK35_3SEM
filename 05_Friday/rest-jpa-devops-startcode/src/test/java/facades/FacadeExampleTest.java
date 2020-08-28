package facades;

import dto.CustomerDTO;
import utils.EMF_Creator;
import entities.BankCustomer;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.*;
import org.junit.jupiter.api.Disabled;

//Uncomment the line below, to temporarily disable this test
 @Disabled
public class FacadeExampleTest {

    private static EntityManagerFactory emf;
    private static BankFacade facade;

    public FacadeExampleTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        EntityManagerFactory EMF = Persistence.createEntityManagerFactory("pu"); 
        facade = BankFacade.getFacadeExample(emf);
    }
    
    /*
    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }*/

    // Setup the DataBase in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the script below to use YOUR OWN entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            // Giver compile fejl:
            //    em.createNamedQuery("BankCustomer.deleteAllRows").executeUpdate();
            // Lavet en midlertidig anden løsning med samme formål som ovenfor
            Query q3 = em.createQuery("DELETE FROM BankCustomer");
            q3.executeUpdate();
            em.createNativeQuery("ALTER TABLE BANKCUSTOMER AUTO_INCREMENT = 1").executeUpdate();
            em.persist(new BankCustomer("Jannich", "Madsen", "914504", 500000, 1, "Expert"));
            em.persist(new BankCustomer("Jens", "Hansen", "014504", 6000, 1, "Student"));

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    /*
    @AfterEach
    public void tearDown() {
//        Remove any data after each test was run
    }*/

      @Test
    public void getCustomerByID() {
        CustomerDTO result = facade.getCustomerByID(1);
        int expected = 1;
        assertEquals(expected, result.getCustomerID());
    }
    
    @Test
    public void getCustomerByName() {
        CustomerDTO result = facade.getCustomerByName("Jannich");
        String expected = "Jannich";
        String actually = result.getFullName().split(" ")[0];
        assertEquals(expected, actually);
    }
    
    @Test
    public void getAllBankCustomers() {
        List<BankCustomer> result = facade.getAllBankCustomers();
        assertThat(result, hasSize(2));
    }

    @Test
    public void addCustomer() {
        BankCustomer customer = new BankCustomer("Kurt", "Zik", "525012", 1, 2, "new");
        // Jeg tilføjer en kunde:
        facade.addCustomer(customer);

        // Så henter vi kunderne og tjekker om den er blevet tilføjet.
        List<BankCustomer> customerList = facade.getAllBankCustomers();

        assertThat(customerList, hasSize(3));
    }

}
