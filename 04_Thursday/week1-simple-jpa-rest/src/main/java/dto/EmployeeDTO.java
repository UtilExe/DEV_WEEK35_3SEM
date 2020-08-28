
package dto;

import entities.Employee;
import java.util.List;

public class EmployeeDTO {
    
    private Long id;
    private String name;
    private String address;

    public EmployeeDTO(Employee employee) {
        this.id = employee.getId();
        this.name = employee.getName();
        this.address = employee.getAddress();
    }    
    
}
