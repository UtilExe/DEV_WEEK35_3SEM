package rest;

import com.google.gson.Gson;
import dto.EmployeeDTO;
import entities.Employee;
import facadess.EmployeeFacade;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("employees")
public class EmployeeResource {
    
    //NOTE: Change Persistence unit name according to your setup
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu"); 
    EmployeeFacade facade =  EmployeeFacade.getFacadeExample(emf);

    @Path("id/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getEmployeeById(@PathParam("id") long id) {
        return facade.getEmployeeById(id);
    }
    
    @Path("name/{name}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getEmployeesByName(@PathParam("name") String name) {
        return facade.getEmployeesByName(name);
    }
    
    @Path("all/")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllEmployees() {
        return facade.getAllEmployees();
    }
    
    @Path("highestpaid/")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getEmployeesWithHighestSalary() {
        return facade.getEmployeesWithHighestSalary();
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Employee create(Employee entity) {
        return facade.createEmployee(entity);
    }
    
    @PUT
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    public void update(Employee entity, @PathParam("id") int id) {
        throw new UnsupportedOperationException();
    }
}
