package tester;

import entity.Employee;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class Tester {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(new Employee("xa12tt", "Kurt", "Wonnegut", new BigDecimal(335567)));
            em.persist(new Employee("hyu654", "Hanne", "Olsen", new BigDecimal(435867)));
            em.persist(new Employee("uio876", "Jan", "Olsen", new BigDecimal(411567)));
            em.persist(new Employee("klo999", "Irene", "Petersen", new BigDecimal(33567)));
            em.persist(new Employee("jik666", "Tian", "Wonnegut", new BigDecimal(56567)));
            em.getTransaction().commit();

            //Complete all these small tasks. Your will find the solution to all, but the last,
            //In this document: https://en.wikibooks.org/wiki/Java_Persistence/JPQL#JPQL_supported_functions
            //1) Create a query to fetch all employees with a salary > 100000 and print out all the salaries
            Query query = em.createQuery("Select e FROM Employee e WHERE e.salary > 100000");
            List<Employee> result = query.getResultList();
            for (Employee e : result) {
                System.out.println("****************");
                System.out.println("ID: " + e.getId());
                System.out.println("First name: " + e.getFirstName());
                System.out.println("Last name: " + e.getLastName());
                System.out.println("Salary: " + e.getSalary());
                System.out.println("****************");
            }

            //2) Create a query to fetch the employee with the id "klo999" and print out the firstname
            System.out.println("Opgave 2:");
            String id = "klo999";
            Query query2 = em.createQuery("Select e FROM Employee e WHERE e.id = :id");
            query2.setParameter("id", id);
            Employee result2 = (Employee) query2.getSingleResult();
            System.out.println(result2.getFirstName());

            //3) Create a query to fetch the highest salary and print the value
            System.out.println("Opgave 3:");
            Query query3 = em.createQuery("Select MAX(e.salary) FROM Employee e");
            BigDecimal result3 = (BigDecimal) query3.getSingleResult();
            System.out.println("Salary: " + result3);

            //4) Create a query to fetch the firstName of all Employees and print the names
            System.out.println("Opgave 4: ");
            Query query4 = em.createQuery("Select e.firstName FROM Employee e");
            List<String> result4 = query4.getResultList();
            System.out.println("First name of all Employees: " + result4);

            //5 Create a query to calculate the number of employees and print the number
            System.out.println("Opgave 5: ");
            Query query5 = em.createQuery("Select COUNT(e) FROM Employee e");
            List<Integer> result5 = query5.getResultList();
            System.out.println("Number of employees: " + result5);

            //6 Create a query to fetch the Employee with the higest salary and print all his details
            System.out.println("Opgave 6: ");
            Query query6 = em.createQuery("SELECT e FROM Employee e WHERE e.salary = (SELECT MAX(e.salary) FROM Employee e)");
            //  List<Employee> result6 = query6.getResultList();
            Employee result6 = (Employee) query6.getSingleResult();
            System.out.println("****************");
            System.out.println("ID: " + result6.getId());
            System.out.println("First name: " + result6.getFirstName());
            System.out.println("Last name: " + result6.getLastName());
            System.out.println("Salary: " + result6.getSalary());
            System.out.println("****************");

        } finally {
            em.close();
            emf.close();
        }
    }

}
