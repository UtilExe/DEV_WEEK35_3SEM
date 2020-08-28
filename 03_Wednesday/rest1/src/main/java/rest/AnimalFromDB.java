package rest;

import com.google.gson.Gson;
import entity.Animal;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

@Path("animals_db")
public class AnimalFromDB {

    @Context
    private UriInfo context;
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");

    /**
     * Creates a new instance of AnimalFromDB
     */
    public AnimalFromDB() {
    }

    @Path("animals")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getAnimals() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            TypedQuery<Animal> query = em.createQuery("SELECT a FROM Animal a", Animal.class);
            List<Animal> animals = query.getResultList();
            em.getTransaction().commit();
            return new Gson().toJson(animals);
        } finally {
            em.close();
        }
    }

    @Path("animalbyid/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getAnimalsbyID(@PathParam("id") int id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            List<Animal> animals = em.createQuery("SELECT p from Animal p WHERE p.id = :id",
                    Animal.class).setParameter("id", id).getResultList();
            em.getTransaction().commit();
            // Check if animal list has data:
            if (!animals.isEmpty()) {
                return new Gson().toJson(animals);
            } // Else, we assume animal is null ("check for null if no animal with this id exists")
            else {
                return null;
            }
        } finally {
            em.close();
        }
    }

    @Path("animalbytype/{type}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getAnimalsbyType(@PathParam("type") String type) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            List<Animal> animals = em.createQuery("SELECT p from Animal p WHERE p.type = :type",
                    Animal.class).setParameter("type", type).getResultList();
            em.getTransaction().commit();
            // Check if animal list has data:
            if (!animals.isEmpty()) {
                return new Gson().toJson(animals);
            } // Else, we assume animal is null ("check for null if no animal with this id exists")
            else {
                return null;
            }
        } finally {
            em.close();
        }
    }

    @Path("/random_animal")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getAnimalsbyType() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            TypedQuery<Animal> query = (TypedQuery<Animal>) em.createQuery(
                    "SELECT u FROM Animal u order by function('RAND')");

            List<Animal> animals = query.setMaxResults(1).getResultList();
            em.getTransaction().commit();
            // Check if animal list has data:
            if (!animals.isEmpty()) {
                return new Gson().toJson(animals);
            } // Else, we assume animal is null ("check for null if no animal with this id exists")
            else {
                return null;
            }
        } finally {
            em.close();
        }
    }

    /**
     * Retrieves representation of an instance of rest.AnimalFromDB
     *
     * @return an instance of java.lang.String
     */
    /* @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }
     */
    /**
     * PUT method for updating or creating an instance of AnimalFromDB
     *
     * @param content representation for the resource
     */
    /*
@PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
     */
}
