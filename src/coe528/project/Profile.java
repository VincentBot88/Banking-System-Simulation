package coe528.project;

/**
 * Abstract class representing a user profile.
 * Defines common attributes and methods for user profiles.
 * Specific types of profiles, such as Customer and Manager, will extend this class.
 * 
 * @author Vincent Cheng
 */
public abstract class Profile {
    protected String username;  // Username of the profile
    protected String password;  // Password of the profile
    protected String role;      // Role of the profile (e.g., customer, manager)

    /**
     * Retrieves the role of the profile.
     * 
     * @return the role of the profile
     */
    public String getRole() {   
        return role;
    }

    /**
     * Retrieves the username of the profile.
     * 
     * @return the username of the profile
     */
    public String getUser() {
        return username;
    }

    /**
     * Sets the username of the profile.
     * 
     * @param username the username to set
     */
    public void setUser(String username) {
        this.username = username;
    }
    
    /**
     * Sets the password of the profile.
     * 
     * @param pass the password to set
     */
    public void setPassword(String pass) {
        this.password = pass;
    }
    
    /**
     * Retrieves the password of the profile.
     * 
     * @return the password of the profile
     */
    public String getPassword() { 
        return password;
    }

    /**
     * Sets the user type of the profile.
     * 
     * This method must be implemented by subclasses to specify the user type.
     */
    public abstract void setUserType();    
}
