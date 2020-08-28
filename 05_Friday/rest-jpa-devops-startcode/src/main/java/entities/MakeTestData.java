
package entities;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class MakeTestData {
    
    public static void main(String[] args) {
     EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
     EntityManager em = emf.createEntityManager();
        BankCustomer customer1 = new BankCustomer("Jens", "Hansen", "014504", 6000, 1, "Student");
        BankCustomer customer2 = new BankCustomer("Madsen", "Denkeys", "930303", 50000, 1, "Senior");
        try {
            em.getTransaction().begin();
            em.persist(customer1);
            em.persist(customer2);
            em.getTransaction().commit();
            //Verify that books are managed and has been given a database id
            System.out.println(customer1.getId());
            System.out.println(customer2.getId());

        } finally {
            em.close();
        }
    }
    
}
