package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import model.AnimalNoDB;


@Path("animals")
public class AnimalDemo {

    @Context
    private UriInfo context;
    private static Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static List<String> animals = new ArrayList<>();

    /**
     * Creates a new instance of AnimalDemo
     */
    public AnimalDemo() {
        if (animals.isEmpty()) {
            animals.add("Dog");
            animals.add("Cat");
            animals.add("Mouse");
            animals.add("Bird");
        }
    }

    /**
     * Retrieves representation of an instance of rest.AnimalDemo
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getJson() {
        
       return "Vuf";
    }
    
    @Path("/animal_list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson2() {
        
        String jsonString = GSON.toJson(animals);
        return jsonString; 
    }
    
    @Path("/animal")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson3() {
        AnimalNoDB animal1  = new AnimalNoDB("Duck", "Quack");
        return new Gson().toJson(animal1);  
    }

    /**
     * PUT method for updating or creating an instance of AnimalDemo
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}
