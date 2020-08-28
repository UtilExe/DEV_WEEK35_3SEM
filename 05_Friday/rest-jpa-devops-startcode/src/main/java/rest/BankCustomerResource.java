package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.CustomerDTO;
import entities.BankCustomer;
import utils.EMF_Creator;
import facades.BankFacade;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

//Todo Remove or change relevant parts before ACTUAL use
@Path("bank")
public class BankCustomerResource {

    private static final EntityManagerFactory EMF = Persistence.createEntityManagerFactory("pu"); 
            //= EMF_Creator.createEntityManagerFactory();
    
    //An alternative way to get the EntityManagerFactory, whithout having to type the details all over the code
    //EMF = EMF_Creator.createEntityManagerFactory(DbSelector.DEV, Strategy.CREATE);
    
    private static final BankFacade facade =  BankFacade.getFacadeExample(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
            
    @Path("id/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public CustomerDTO getEmployeeById(@PathParam("id") int id) {
        return facade.getCustomerByID(id);
    }
    
    @Path("all/")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<BankCustomer> getAllEmployees() {
        return facade.getAllBankCustomers();
    }
    
    @Path("name/{name}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public CustomerDTO getEmployeesByName(@PathParam("name") String name) {
        return facade.getCustomerByName(name);
    }

}
