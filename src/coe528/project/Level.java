package coe528.project;

/**
 * Abstract class representing the level of an account in the bank system.
 * Different account levels may have different behaviors.
 * 
 * @author Vincent Cheng
 */
public abstract class Level {

    /**
     * Changes the level of the specified account based on its balance.
     * 
     * @param account the account whose level needs to be changed
     */
    public abstract void changeLevel(Account account);
    
    /**
     * Allows the specified account to make an online purchase with the given price.
     * 
     * @param account the account making the purchase
     * @param price the price of the item being purchased
     */
    abstract void onlinePurchase(Account account, int price);
}
