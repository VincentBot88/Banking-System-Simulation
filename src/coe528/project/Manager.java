package coe528.project;

import java.util.ArrayList;

/**
 * Represents a manager profile in the bank system.
 * Extends the Profile class to inherit common attributes and methods.
 * 
 * @author Vincent Cheng
 */
public class Manager extends Profile {
    /**
     * Initializes a new manager profile with default username and password.
     */
    public Manager() {
        // Set default username and password for the manager
        super.setUser("admin");
        super.setPassword("admin");        
        setUserType(); // Specify the user type as manager
    }
    
    /**
     * Specifies the user type as manager.
     */
    @Override
    public void setUserType() {
        super.role = "manager"; // Set the role to manager
    }
}
