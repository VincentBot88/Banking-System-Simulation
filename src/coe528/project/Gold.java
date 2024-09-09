package coe528.project;

/**
 * Represents the Gold level of an account in the bank system.
 * 
 * @author Vincent Cheng
 */
public class Gold extends Level{

    /**
     * Changes the level of the specified account to Gold based on its balance.
     * If the balance is less than 10000, the level changes to Silver.
     * If the balance is greater than 20000, the level changes to Platinum.
     * 
     * @param account the account whose level needs to be changed
     */
    @Override
    public void changeLevel(Account account){
        if (account.getBalance() > 20000) {
            account.setLevel(new Platinum());
        }
        if (account.getBalance() < 10000){
            account.setLevel(new Silver());
        }       
    }

    /**
     * Allows the specified Gold level account to make an online purchase with the given price.
     * The purchase is successful if the price is less than the account balance.
     * 
     * @param account the Gold level account making the purchase
     * @param price the price of the item being purchased
     */
    @Override
    public void onlinePurchase(Account account,int price){
        if ((price >= 50) && (price < account.getBalance())){
            account.withdraw(price);
        }
    }
}
