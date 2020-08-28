
package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
public class Customer implements Serializable {
    
    @Column(name="name", nullable = false)
    private String firstName;
    private String lastName;
    
    @Temporal(TemporalType.DATE)
    private Date created;

    public Customer(String firstName, String lastName/*, Date created*/) {
        this.firstName = firstName;
        this.lastName = lastName;
        created = new java.util.Date();
    }
    
    public Customer() {
        
    }

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getCreated() {
        return created;
    }

     @Override
    public String toString() {
       return "Customer{" + "firstName=" + firstName + ", lastName=" + lastName + ", created=" + created + '}';
    }
    
    
    
    
}
