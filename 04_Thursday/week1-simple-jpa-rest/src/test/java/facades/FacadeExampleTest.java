package facades;

import dto.EmployeeDTO;
import entities.Employee;
import facadess.EmployeeFacade;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FacadeExampleTest {
    
    private static EntityManagerFactory emf;
    private static EmployeeFacade facade;
    
    public FacadeExampleTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
        EntityManagerFactory EMF = Persistence.createEntityManagerFactory("pu"); 
        facade = EmployeeFacade.getFacadeExample(emf);
    }
    
     @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            // Giver compile fejl:
            //    em.createNamedQuery("BankCustomer.deleteAllRows").executeUpdate();
            // Lavet en midlertidig anden løsning med samme formål som ovenfor
            Query q3 = em.createQuery("DELETE FROM EMPLOYEE");
            q3.executeUpdate();
            em.createNativeQuery("ALTER TABLE EMPLOYEE AUTO_INCREMENT = 1").executeUpdate();
            em.persist(new Employee("Jannich", "Dalgas", 124560.353));
            em.persist(new Employee("Jimmy", "Boulevard", 54560.353));

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
    
    // Hmm, hvordan skal jeg teste metoderne når de returnerer en Streng via GSON.toJson()?
    // Jeg har lavet testet på en anden måde i fredags-opgaven (jpa-rest-devops-starter),
    // iogmed de ikke returnerer en Streng, men et objekt i stedet, hvilket gjorde det muligt.
    
    /*
     @Test
    public void getEmployeeById() {
        String result = facade.getEmployeeById(1);
        int expected = 1;
        assertEquals(expected, result);
    }
    */
 
    /*
    @Test
    public void getCustomerByName() {
        String result = facade.getEmployeesByName("Jannich");
        String expected = "Jannich";
        assertEquals(expected, result);
    }
    */

}
