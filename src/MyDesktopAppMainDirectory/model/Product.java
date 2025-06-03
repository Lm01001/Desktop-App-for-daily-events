package MyDesktopAppMainDirectory.model;
import MyDesktopAppMainDirectory.model.List;

import java.util.Scanner;

public class Product extends List {
    private int amount = 0;
    public int getAmount(){
        return amount;
    }
    private String amountAsString = String.valueOf(getAmount());
    public String getAmountString(){
        return amountAsString;
    }

    public Product(int priority, String name, String amount) {
        super(priority, name);
        this.amountAsString = amount;
    }

    //Extra no-argument constructor as a helper for ShoppingList inside Db class
    public Product() {
        super(0, "Default name");
        this.amount = 0;
    }

    public void setAmount(int amount) {       //Choosing amount/number to buy for each item
        while(true){
            System.out.println("Please choose quantity of products you want to add (max up to 1000): ");
            try{
                this.amount = Integer.parseInt(choice.nextLine());
                if(this.amount <= 1000 && this.amount > 0) {
                    break;
                }else{
                    System.out.print("Number not recognized or out of range, please try again: ");
                }
            }catch(NumberFormatException e){
                System.out.println("Choice not recognized. Please enter a valid number.");
            }
        }
    }
}
