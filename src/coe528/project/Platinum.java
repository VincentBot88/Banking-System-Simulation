package coe528.project;

/**
 * Represents the Platinum level of an account in the bank system.
 * 
 * @author Vincent Cheng
 */
public class Platinum extends Level{

    /**
     * Changes the level of the specified account to Platinum based on its balance.
     * If the balance is less than 10000, the level changes to Silver.
     * If the balance is between 10000 and 20000, the level changes to Gold.
     * 
     * @param account the account whose level needs to be changed
     */
    @Override
    public void changeLevel(Account account){
        if (account.getBalance() < 10000){
            account.setLevel(new Silver());
        }
        if (account.getBalance() < 20000 && account.getBalance() > 10000){
            account.setLevel(new Gold());
        }
    }

    /**
     * Allows the specified Platinum level account to make an online purchase with the given price.
     * The purchase is successful if the price is less than the account balance.
     * 
     * @param account the Platinum level account making the purchase
     * @param price the price of the item being purchased
     */
    @Override
    public void onlinePurchase(Account account, int price){
        if ((price >= 50) && (price < account.getBalance())){
            account.withdraw(price);
        }
    }       
}
