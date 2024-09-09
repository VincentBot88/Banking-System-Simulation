package coe528.project;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.scene.Scene;

import java.io.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vincent Cheng
 */
 
public class UserInterface extends Application {
    final static TextField USERNAME_TEXT_FIELD = new TextField();
    final static PasswordField PASSWORD_TEXT_FIELD = new PasswordField();
    final Button LOGIN_BUTTON = new Button("Login");
    final Button CLEAR_BUTTON = new Button("Clear");
    public static int purchaseCost = 0;

    // Method to read customer data from file
    public static Customer readCustomerData(String username, String password) throws FileNotFoundException {
        Customer customer = null;
        File file = new File(username + ".txt");
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.equals(username)) {
                if (scanner.nextLine().equals(password) && scanner.hasNextLine() && scanner.nextLine().equals("Customer")) {
                    int balance = Integer.parseInt(scanner.nextLine());
                    customer = new Customer(username, password, null, balance);
                }
            }
        }
        return customer;
    }

    // Method to write user data to file
    public static void writeUserData(Profile profile) throws IOException {
        try (BufferedWriter write = new BufferedWriter(new FileWriter(profile.getUser() + ".txt", true))) {
            write.write("\n" + profile.getUser() + System.getProperty("line.separator"));
            write.write(profile.getPassword() + System.getProperty("line.separator"));
            write.write(profile.getRole() + System.getProperty("line.separator"));
            if (profile.getRole().equals("Customer")) {
                Customer customer = (Customer) profile;
                write.write(customer.getAccount().getBalance() + System.getProperty("line.separator"));
            }
            write.newLine();
        }
    }
    
        // Method to delete a file
    public static boolean deleteFile(String textFile) {
    File file = new File(textFile + ".txt");
    if (!file.exists()) {
        return false; // File does not exist
    }

    if (!file.getName().equals("admin.txt")) {
        return file.delete(); // Attempt to delete the file
    } else {
        throw new IllegalArgumentException("Admin file cannot be deleted");
    }
}



    @Override
    public void start(final Stage primaryStage) {
        // Create UI components
        GridPane signinLayout = new GridPane();
        signinLayout.add(new Label("Username:"), 0, 6);
        signinLayout.add(USERNAME_TEXT_FIELD, 1, 6);
        signinLayout.add(new Label("Password:"), 0, 8);
        signinLayout.add(PASSWORD_TEXT_FIELD, 1, 8);
        signinLayout.add(LOGIN_BUTTON, 1, 10);
        signinLayout.add(CLEAR_BUTTON, 1, 10);
        signinLayout.setHgap(6);
        signinLayout.setVgap(6);
        
        // Set properties for UI
        signinLayout.setAlignment(Pos.CENTER);
        USERNAME_TEXT_FIELD.setAlignment(Pos.BOTTOM_RIGHT);
        PASSWORD_TEXT_FIELD.setAlignment(Pos.BOTTOM_RIGHT);
        GridPane.setHalignment(LOGIN_BUTTON, HPos.LEFT);
        GridPane.setHalignment(CLEAR_BUTTON, HPos.RIGHT);

        // Create title label
        final Text titleLabel = new Text("Sign In");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.ITALIC, 35));
        
        
        // Process events for clear button
        CLEAR_BUTTON.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                USERNAME_TEXT_FIELD.clear();
                PASSWORD_TEXT_FIELD.clear();
            }
        });

        // Process events for login button
        LOGIN_BUTTON.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                boolean managerAccessGranted = false;
                boolean customerAccessGranted = false;
                String username = USERNAME_TEXT_FIELD.getText();
                String password = PASSWORD_TEXT_FIELD.getText();
                File adminFile = new File("admin.txt");
                File customerFile = new File(username + ".txt");

                try (Scanner adminScanner = new Scanner(adminFile); Scanner customerScanner = new Scanner(customerFile)) {
                    while (adminScanner.hasNextLine()) {
                        if (adminScanner.nextLine().equals(username)) {
                            if (adminScanner.hasNextLine() && adminScanner.nextLine().equals(password) && adminScanner.nextLine().equals("manager")) {
                                managerAccessGranted = true;
                                break;
                            }
                        }
                    }
                    while (customerScanner.hasNextLine()) {
                        if (customerScanner.nextLine().equals(username)) {
                            if (customerScanner.hasNextLine() && customerScanner.nextLine().equals(password) && customerScanner.nextLine().equals("Customer")) {
                                customerAccessGranted = true;
                                break;
                            }
                        }
                    }

                    if (managerAccessGranted) {
                        primaryStage.close();
      
                        Label welcomeLabel = new Label("You have been logged in.");
                        VBox pane = new VBox(10);
                        Button logout = new Button("Logout");
                        Button addCustomer = new Button("Add Customer");
                        Button deleteCustomer = new Button("Delete Customer");
                        pane.setAlignment(Pos.CENTER);
                        pane.getChildren().addAll(welcomeLabel,addCustomer,deleteCustomer,logout);

                        Scene scene2 = new Scene(pane,600,500);

                        final Stage newWindow = new Stage();
                        newWindow.setTitle(USERNAME_TEXT_FIELD.getText() + "'s Bank Account");
                        newWindow.setScene(scene2);

                        newWindow.show();

                       logout.setOnAction(
                           new EventHandler<ActionEvent>(){
                               @Override
                               public void handle(ActionEvent e){
                                   USERNAME_TEXT_FIELD.clear();
                                   PASSWORD_TEXT_FIELD.clear();
                                   newWindow.close();
                                   primaryStage.show();
                                   titleLabel.setText("You have logged out");
                               }
                       }
                       );
                                   deleteCustomer.setOnAction(
                           new EventHandler<ActionEvent>(){
                               @Override
                               public void handle(ActionEvent e){
                         newWindow.close();   
                        Label customer = new Label("Enter Customer Name: ");
                        final TextField customerNameToDeleteTextField = new TextField();
                        Button deleteButton = new Button("Delete Customer");
                        final Label deletion = new Label("Confirm Customer Deletion: ");
                        Button back = new Button("Return");
                        customerNameToDeleteTextField.setPrefWidth(60);
                        customerNameToDeleteTextField.setMaxWidth(150);
                        VBox pane = new VBox(10);
                        pane.setAlignment(Pos.CENTER);
                        pane.getChildren().addAll(customer, customerNameToDeleteTextField,deletion, deleteButton,back);

                        Scene scene7 = new Scene(pane,600,500);

                        final Stage deleteCustomerStage = new Stage();
                        deleteCustomerStage.setTitle("Delete a Customer");
                        deleteCustomerStage.setScene(scene7);

                        deleteCustomerStage.show();  

                                 back.setOnAction(
                           new EventHandler<ActionEvent>(){
                               @Override
                               public void handle(ActionEvent e){
                                   deleteCustomerStage.close();
                                   newWindow.show();
                               }
                       }
                       );

                         deleteButton.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent e) {
                                    String temporary = customerNameToDeleteTextField.getText();
                                    customerNameToDeleteTextField.clear();

                                    try {
                                        if (deleteFile(temporary)) {
                                            deletion.setText("Customer Successfully Deleted");
                                        } else {
                                            deletion.setText("File not found: " + temporary);
                                        }
                                    } catch (IllegalArgumentException ex) {
                                        Logger.getLogger(UserInterface.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            });

        
                                        }
                       }
                       );   

                          addCustomer.setOnAction(
                           new EventHandler<ActionEvent>(){
                               @Override
                               public void handle(ActionEvent e){
                        newWindow.close();
                        Label customer = new Label("Enter Customer Name: ");
                        final TextField newCustomerNameTextField = new TextField();
                        Label password = new Label("Enter Password: ");
                        final TextField depositAmountTextField = new TextField();
                        Label amount = new Label("Enter Initial Amount: ");
                        final TextField withdrawalAmountTextField = new TextField();
                        final Label addition = new Label("Confirm Customer Addition: ");
                        final Label blank = new Label("");
                        Button addButton = new Button("Add Customer");
                        Button back = new Button("Return");
                        newCustomerNameTextField.setPrefWidth(60);
                        newCustomerNameTextField.setMaxWidth(150);
                        depositAmountTextField.setPrefWidth(60);
                        depositAmountTextField.setMaxWidth(150);
                        withdrawalAmountTextField.setPrefWidth(60);
                        withdrawalAmountTextField.setMaxWidth(150);
                        VBox pane = new VBox(10);

                        pane.setAlignment(Pos.CENTER);
                        pane.getChildren().addAll(customer, newCustomerNameTextField, password, depositAmountTextField, amount, withdrawalAmountTextField,addition, blank, addButton, back);

                        Scene scene6 = new Scene(pane,600,500);

                        final Stage addCustomerStage = new Stage();
                        addCustomerStage.setTitle("Add a Customer");
                        addCustomerStage.setScene(scene6);

                        addCustomerStage.show();                

                        back.setOnAction(
                           new EventHandler<ActionEvent>(){
                               @Override
                               public void handle(ActionEvent e){
                                   addCustomerStage.close();
                                   newWindow.show();
                               }
                       }
                       );

                        addButton.setOnAction(
                           new EventHandler<ActionEvent>(){
                               @Override
                               public void handle(ActionEvent e){
                                        String name, password1;
                               int amount1;  
                               name = null;
                               password1 = null;
                               amount1 = -1;
                               Account account = null;
                               String i = "";
                        name = newCustomerNameTextField.getText();
                        password1 = depositAmountTextField.getText();
                        try{
                        if(name.equals("")){
                            throw new NumberFormatException();
                        }
                        amount1 = Integer.parseInt(withdrawalAmountTextField.getText());

                        }catch (NumberFormatException ex){
                          i = "Invalid inputs";
                          blank.setText(i);
                          addition.setText("Confirm Customer Addition: ");
                        }
                        newCustomerNameTextField.clear();
                        depositAmountTextField.clear();
                        withdrawalAmountTextField.clear();

                        Customer cust  = new Customer(name,password1,account,amount1);
                                   try {
                                       if(amount1 > 99){
                                           writeUserData(cust);
                                       if(i.equals("")){
                                       addition.setText("Customer Successfully Added");
                                       blank.setText("");
                                       }
                                       if(i.equals("Invalid inputs")){
                                          blank.setText("");
                                          addition.setText("Confirm Customer Addition: ");
                                       }
                                   }
                                       else{
                                            i = "Enter a valid initial balance (at least $100)";
                          blank.setText(i);
                          addition.setText("Confirm Customer Addition: ");
                                       }

                                   } catch (NullPointerException ex1){
                                   addition.setText("Confirm Customer Addition: ");
                                   blank.setText("Username already in use");
                               } 
                                    catch (IOException ex2) {
                                       Logger.getLogger(UserInterface.class.getName()).log(Level.SEVERE, null, ex2);
                                   }
                                            catch (NumberFormatException ex){
                          i = "Invalid inputs";
                          blank.setText(i);
                          addition.setText("Confirm Customer Addition: ");
                        }
                               }}

                       );     



                               }
                       }
                       );
                        
                    } else if (customerAccessGranted) {
                        primaryStage.close();
                        final Customer c1 = readCustomerData(USERNAME_TEXT_FIELD.getText(),PASSWORD_TEXT_FIELD.getText());
                        final Label welcomeLabel = new Label("You have been logged in.");
                        welcomeLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
                      
                        VBox pane = new VBox(12);
                        Button logout = new Button("Logout");
                        final TextField customerNameTextField = new TextField();
                        customerNameTextField.setPrefWidth(70);
                        customerNameTextField.setMaxWidth(160);

                        Button deposit = new Button("Deposit");
                        final TextField depositAmountTextField = new TextField();
                        depositAmountTextField.setPrefWidth(70);
                        depositAmountTextField.setMaxWidth(160);

                        Button withdraw = new Button("Withdraw");
                        Button getbalance = new Button("Check Balance");
                        Button onlinepurchase = new Button("ONLINE SHOPPING");
                        pane.setAlignment(Pos.CENTER);
                        pane.getChildren().addAll(welcomeLabel,customerNameTextField,deposit,depositAmountTextField,withdraw,getbalance,onlinepurchase,logout);

                        Scene scene2 = new Scene(pane,600,500);

                        final Stage newWindow = new Stage();
                        newWindow.setTitle(USERNAME_TEXT_FIELD.getText() + "'s Bank Account");
                        newWindow.setScene(scene2);

                        newWindow.show();

                                 logout.setOnAction(
                           new EventHandler<ActionEvent>(){
                               @Override
                               public void handle(ActionEvent e){
                                   USERNAME_TEXT_FIELD.clear();
                                   PASSWORD_TEXT_FIELD.clear();
                                   newWindow.close();
                                   primaryStage.show();
                               }
                       }
                       );
                                 getbalance.setOnAction(new EventHandler<ActionEvent>(){
                               @Override
                               public void handle(ActionEvent e){
                                   welcomeLabel.setText("Account Balance: " + c1.getAccount().getBalance() );
                               }

                               });
                                  deposit.setOnAction(new EventHandler<ActionEvent>(){
                               @Override
                               public void handle(ActionEvent e){
                                   try{
                                       int i = Integer.parseInt(customerNameTextField.getText());
                                       c1.getAccount().deposit(i);
                                       welcomeLabel.setText("Deposit Successful");
                                       customerNameTextField.clear();
                                   }catch (NumberFormatException |NullPointerException ex){
                                       welcomeLabel.setText("Invalid Deposit");
                                       customerNameTextField.clear();
                                   }


                               }});                 


                                withdraw.setOnAction(new EventHandler<ActionEvent>() {
                                    @Override
                                    public void handle(ActionEvent e) {
                                        try {
                                            int withdrawalAmount = Integer.parseInt(depositAmountTextField.getText());
                                            if (withdrawalAmount > c1.getAccount().getBalance()) {
                                                welcomeLabel.setText("Withdrawal amount exceeds balance");
                                            } else {
                                                c1.getAccount().withdraw(withdrawalAmount);
                                                welcomeLabel.setText("Withdrawal Successful");
                                            }
                                            depositAmountTextField.clear();
                                        } catch (NumberFormatException | NullPointerException ex) {
                                            welcomeLabel.setText("Invalid Withdrawal");
                                            depositAmountTextField.clear();
                                        }
                                    }
                                });                   
                                onlinepurchase.setOnAction(new EventHandler<ActionEvent>(){
                               @Override
                               public void handle(ActionEvent e){
                                   newWindow.close();
                        String a = null;
                        if (c1.getAccount().getBalance() < 10000){
                            a = "Silver ($20 fee)";
                            purchaseCost +=20;
                        }
                        if (c1.getAccount().getBalance() >= 10000 && c1.getAccount().getBalance() < 20000){
                            a = "Gold ($10 fee)";
                            purchaseCost += 10;
                        }
                        if (c1.getAccount().getBalance() >= 20000){
                            a = "Platinum ($0 fee)";
                            purchaseCost +=0;
                        }

                        final Label currentLevelLabel = new Label("Current Level: " + a);
                        Label carItemLabel = new Label("Car 🚗 $1000");
                        Label bikeItemLabel = new Label("Bike 🚲 $100");
                        Label hatItemLabel = new Label("Hat 🎩 $40");
                        Label purchaseConfirmationLabel = new Label("Confirm Purchase");
                        final Label orderSummaryLabel = new Label("Order: \n");
                        final Label totalCostLabel = new Label("Total Cost: $" + purchaseCost);
                        Button carButton = new Button("Add To Shopping Cart");
                        Button bikeButton = new Button("Add To Shopping Cart");
                        Button hatButton = new Button("Add To Shopping Cart");
                        Button checkoutButton = new Button("Proceed to Checkout");
                        Button backButton = new Button("Back");
                        VBox pane = new VBox(10);

                        pane.setAlignment(Pos.CENTER);
                        pane.getChildren().addAll(currentLevelLabel,carItemLabel,carButton,bikeItemLabel,bikeButton,hatItemLabel,hatButton,purchaseConfirmationLabel,orderSummaryLabel,totalCostLabel,checkoutButton,backButton);

                        Scene scene2 = new Scene(pane,600,500);

                        final Stage purchaseStage = new Stage();
                        purchaseStage.setTitle("Make an Online Purchase");
                        purchaseStage.setScene(scene2);

                        purchaseStage.show();

                                 backButton.setOnAction(new EventHandler<ActionEvent>(){
                               @Override
                               public void handle(ActionEvent e){
                                   purchaseCost = 0;
                                   welcomeLabel.setText("Balance: " + c1.getAccount().getBalance() );
                                   newWindow.show();
                                   purchaseStage.close();
                               }

                               });
                        carButton.setOnAction(new EventHandler<ActionEvent>(){
                               @Override
                               public void handle(ActionEvent e){
                                   orderSummaryLabel.setText("Cost: ");
                                   orderSummaryLabel.setText(orderSummaryLabel.getText()+ "Car $1000\n");

                                   purchaseCost += 1000;
                                   totalCostLabel.setText("Total Cost: ");
                                   totalCostLabel.setText(totalCostLabel.getText() + purchaseCost);
                               }

                               });
                        bikeButton.setOnAction(new EventHandler<ActionEvent>(){
                               @Override
                               public void handle(ActionEvent e){
                                   orderSummaryLabel.setText("Cost: ");
                                   orderSummaryLabel.setText(orderSummaryLabel.getText()+ "Bike $100\n");
                                   purchaseCost += 100;
                                   totalCostLabel.setText("Total Cost: ");
                                   totalCostLabel.setText(totalCostLabel.getText() + purchaseCost);
                               }

                               });  
                        hatButton.setOnAction(new EventHandler<ActionEvent>(){
                               @Override
                               public void handle(ActionEvent e){
                                   orderSummaryLabel.setText("Cost: ");
                                   orderSummaryLabel.setText(orderSummaryLabel.getText()+ "Hat $40\n");
                                   purchaseCost += 40;
                                   totalCostLabel.setText("Total Cost: ");
                                   totalCostLabel.setText(totalCostLabel.getText() + purchaseCost);
                               }

                               });  
                        checkoutButton.setOnAction(new EventHandler<ActionEvent>(){
                               @Override
                               public void handle(ActionEvent e){
                                   if(purchaseCost > c1.getAccount().getBalance()){
                                   totalCostLabel.setText("Insufficient Balance");
                                   orderSummaryLabel.setText("Restart Order");
                                   purchaseCost = 0;
                        if (c1.getAccount().getBalance() < 10000){
                            purchaseCost +=20;
                        }
                        if (c1.getAccount().getBalance() >= 10000 && c1.getAccount().getBalance() < 20000){
                            purchaseCost += 10;
                        }
                        if (c1.getAccount().getBalance() >= 20000){
                            purchaseCost +=0;
                        }

                                   }
                                   if(purchaseCost < 50){
                                   orderSummaryLabel.setText("Purchase must be at least $50");
                                   }
                                   if(purchaseCost >=50 && purchaseCost < c1.getAccount().getBalance()){
                                   totalCostLabel.setText("Total Cost: $");

                                   c1.getAccount().getLevel().onlinePurchase(c1.getAccount(),purchaseCost);
                                   orderSummaryLabel.setText("Purchase Successful of: $" + purchaseCost);
                                   purchaseCost = 0;
                                   String b = null;
                     if (c1.getAccount().getBalance() < 10000){
                           c1.getAccount().getLevel().changeLevel(c1.getAccount());
                           b ="Silver, $20 fee on all purchases.";
                           purchaseCost += 20;
                        }
                        if (c1.getAccount().getBalance() >= 10000 && c1.getAccount().getBalance() < 20000){
                           c1.getAccount().getLevel().changeLevel(c1.getAccount());
                           b = "Gold, $10 fee on all purchases.";
                           purchaseCost += 10;
                        }
                        if (c1.getAccount().getBalance() >= 20000){
                           c1.getAccount().getLevel().changeLevel(c1.getAccount());
                           b = "Platinum, NO fee on all purchases.";

                        }
                        currentLevelLabel.setText("Current Level: " + b);
                               }}

                               });           
                               }


                            }
                        ); 

                    } else {
                        titleLabel.setText("Wrong username or password");
                    }
                } catch (FileNotFoundException ex) {
                    titleLabel.setText("Wrong username or password");
                    USERNAME_TEXT_FIELD.clear();
                    PASSWORD_TEXT_FIELD.clear();
                }
            }
        });

        // Create a VBox to hold UI components
        VBox vbox = new VBox(2);
        vbox.setAlignment(Pos.CENTER); // Set alignment to center
        vbox.getChildren().addAll(titleLabel, signinLayout);

        // Create a scene and place it in the stage
        Scene scene = new Scene(vbox, 600, 300);
        primaryStage.setTitle("Bank Account Application Log In");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
