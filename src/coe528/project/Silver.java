package coe528.project;

/**
 * Represents the Silver level of an account in the bank system.
 * 
 * @author Vincent Cheng
 */
public class Silver extends Level{

    /**
     * Changes the level of the specified account to Silver based on its balance.
     * If the balance is between 10000 and 20000, the level changes to Gold.
     * If the balance is greater than 20000, the level changes to Platinum.
     * 
     * @param account the account whose level needs to be changed
     */
    @Override
    public void changeLevel(Account account){
        if (account.getBalance() > 10000 && account.getBalance() < 20000) {
            account.setLevel(new Gold());
        }
        if (account.getBalance() > 20000){
            account.setLevel(new Platinum());
        }
    }

    /**
     * Allows the specified Silver level account to make an online purchase with the given price.
     * The purchase is successful if the price is less than the account balance.
     * 
     * @param account the Silver level account making the purchase
     * @param price the price of the item being purchased
     */
    @Override
    public void onlinePurchase(Account account,int price){
        if ((price >= 50) && (price < account.getBalance())){
            account.withdraw(price);
        }
    }
}
