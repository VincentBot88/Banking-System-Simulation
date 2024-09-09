package coe528.project;

/**
 * Represents a customer profile in the bank system.
 * Extends the Profile class to inherit common attributes and methods.
 * 
 * @author Vincent Cheng
 */
public class Customer extends Profile {

    private Account account; // The customer's bank account
    
    /**
     * Constructs a new customer profile with the specified name, password, and initial account balance.
     * 
     * @param name the username of the customer
     * @param password the password of the customer
     * @param account the customer's bank account
     * @param acc the initial account balance
     */
    public Customer(String name, String password, Account account, int acc) {
        super.setPassword(password); // Set the password for the customer
        super.setUser(name); // Set the username for the customer
        setUserType(); // Specify the user type as customer
        this.account = new Account(acc); // Initialize a new account for the customer
    }
    
    /**
     * Retrieves the account associated with the customer.
     * 
     * @return the customer's bank account
     */
    public Account getAccount() {
        return account;
    }
    
    /**
     * Specifies the user type as customer.
     */
    @Override
    public void setUserType() {
        super.role = "Customer"; // Set the role to customer
    }
}
