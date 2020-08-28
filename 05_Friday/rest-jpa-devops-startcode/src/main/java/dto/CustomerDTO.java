
package dto;

import entities.BankCustomer;


public class CustomerDTO {
    private int customerID;
    private String fullName; 
    private String accountNumber;
    private double balance;
    
    public CustomerDTO(BankCustomer bankCust) {
        customerID = bankCust.getId();
        fullName = bankCust.getFirstName() + " " + bankCust.getLastName();
        accountNumber = bankCust.getAccountNumber();
        balance = bankCust.getBalance();
    }

    public int getCustomerID() {
        return customerID;
    }

    public String getFullName() {
        return fullName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }
    
    
    
    

}
