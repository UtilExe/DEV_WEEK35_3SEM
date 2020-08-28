package facadess;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.EmployeeDTO;
import entities.Employee;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

public class EmployeeFacade {

    private static EmployeeFacade instance;
    private static EntityManagerFactory emf;
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    //Private Constructor to ensure Singleton
    private EmployeeFacade() {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static EmployeeFacade getFacadeExample(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new EmployeeFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public String getEmployeeById(long id) {
        EntityManager em = emf.createEntityManager();
        try {
            Employee employee = em.find(Employee.class, id);
            return GSON.toJson(new EmployeeDTO(employee));
        } finally {
            em.close();
        }
    }

    public String getEmployeesByName(String name) {
        EntityManager em = emf.createEntityManager();
        try {
            Employee employee = em.createQuery(
                    "SELECT p from Employee p WHERE p.name = :name", Employee.class).
                    setParameter("name", name).getSingleResult();

             return GSON.toJson(new EmployeeDTO(employee));
        } catch (NoResultException nre) {
            /* If the lastName doesn't exist, it will result in an Exception crash.
           Added an exception check to prevent it from crashing. persistence.NoResultException.
             */
            System.out.println("Could not find: " + name);
            return null;
        } finally {
            em.close();
        }
    }

    public String getAllEmployees() {
        EntityManager em = emf.createEntityManager();
        
        List<EmployeeDTO> listen = new ArrayList<>();
        try {
            TypedQuery<Employee> query = em.createQuery("SELECT e FROM Employee e", Employee.class);
            List<Employee> list = query.getResultList();
            for (Employee employee : list) {
                listen.add(new EmployeeDTO(employee));
            }

            return GSON.toJson(listen);
        } finally {
            em.close();
        }
    }

    public String getEmployeesWithHighestSalary() {
     EntityManager em = emf.createEntityManager();
        try {
            List<EmployeeDTO> listen = new ArrayList<>();
            TypedQuery<Employee> query
                    = em.createQuery("SELECT e FROM Employee e WHERE e.salary = (SELECT MAX(e.salary) FROM Employee e)", Employee.class);
            List<Employee> list = query.getResultList();
            for (Employee employee : list) {
                listen.add(new EmployeeDTO(employee));
            }
            
            return GSON.toJson(listen);
        } finally {
            em.close();
        }
    }
    
    public Employee createEmployee(Employee entity) {
        Employee employee = new Employee(entity.getName(), entity.getAddress(), entity.getSalary());
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
            return employee;
          //  return result;
        } finally {
            em.close();
        }
    }

}
