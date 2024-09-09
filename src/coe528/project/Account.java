package coe528.project;
/**
 *
 * @author Vincent Cheng
 */
/**
 * The Account class represents a bank account with functionality for depositing, withdrawing, 
 * setting the account level, and getting the account level. This class is mutable 
 * since it contains methods to modify its state.
 * 
 * Abstraction Function:
 * AF(c) = an account, c, such that
 *    c.setBalance = balance
 *    c.getBalance = balance
 *    c.deposit = balance + deposit
 *    c.withdraw = balance - withdraw
 *    c.setLevel = a
 *    c.getLevel = a
 * 
 * Rep Invariant:
 *   c.setBalance >= 0 &&
 *   c.getBalance >= 0 &&
 *   c.setLevel != null &&
 *   c.getLevel != null
 */

public class Account {
   private int balance;
   private Level level;

    /**
     * Initializes an account with the given balance.
     * 
     * @param balance the initial balance for the account
     */
    public Account(int balance){
        this.balance = balance;
    }

    /**
     * Sets the balance for the account.
     * 
     * @param balance the balance to set
     */
    public void setBalance(int balance) {
        // MODIFIES: this
        // EFFECTS: Sets the balance of the account.
        this.balance = balance;
    }

    /**
     * Retrieves the current balance in the account.
     * 
     * @return the current balance in the account
     */
    public int getBalance() {
        // EFFECTS: Returns the current balance in the account.
        return balance;
    }
   
    /**
     * Deposits the specified amount into the account.
     * 
     * @param deposit the amount to deposit
     */
    public void deposit(int deposit){
        // REQUIRES: deposit > 0
        // MODIFIES: this
        // EFFECTS: Adds the deposit amount to the account.
        if(deposit < 1){
            throw new IllegalArgumentException("Deposit amount must be greater than 0");
        }
        balance += deposit;
    }
    
    /**
     * Withdraws the specified amount from the account.
     * 
     * @param withdraw the amount to withdraw
     */
    public void withdraw(int withdraw){
        // REQUIRES: withdraw > 0 && (balance - withdraw) >= 0
        // MODIFIES: this
        // EFFECTS: Subtracts the withdrawal amount from the account.
        if(withdraw < 1 || ((balance - withdraw) < 0)){
            throw new IllegalArgumentException("Invalid withdrawal amount");
        }
        balance -= withdraw;
    }
    
    /**
     * Sets the level of the account.
     * 
     * @param a the level to set
     */
    public void setLevel(Level a){
        // MODIFIES: this
        // EFFECTS: Sets the level of the account.
        level = a;
    }

    /**
     * Retrieves the level of the account.
     * 
     * @return the level of the account
     */
    public Level getLevel(){
        // EFFECTS: Returns the level of the account based on the balance.
        if(balance < 10000){
            return new Silver();
        } else if(balance < 20000){
            return new Gold();
        } else {
            return new Platinum();
        }
    }
    
    /**
     * Checks if the rep invariant holds for this object.
     * 
     * @return true if the rep invariant holds, false otherwise
     */
    public boolean repOK(){
        // EFFECTS: Returns true if the rep invariant holds for this object; otherwise returns false
        return balance >= 0 && level != null;
    }

    @Override
    public String toString() {
        // EFFECTS: Returns a string representation of the account.
        return "Level: " + level + ", Amount: " + balance;
    }
}
