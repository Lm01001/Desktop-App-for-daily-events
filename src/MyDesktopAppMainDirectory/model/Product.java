package MyDesktopAppMainDirectory.model;
import MyDesktopAppMainDirectory.model.List;

import java.util.Scanner;

public class Product extends List {
    public static int amount = 0;
    Scanner scanner = new Scanner(System.in);

    public Product(int priority, String name, int amount) {
        super(priority, name);
        Product.amount = amount;
    }

    public void setAmount(int amount) {       //Choosing amount/number to buy for each item
        while(true){
            System.out.println("Please choose quantity of products you want to add (max up to 1000): ");
            try{
                Product.amount = Integer.parseInt(scanner.nextLine());
                if(Product.amount <= 1000 && Product.amount > 0) {
                    break;
                }else{
                    System.out.print("Number not recognized or out of range, please try again: ");
                }
            }catch(NumberFormatException e){
                System.out.println("Choice not recognized. Please enter a valid number.");
            }
        }
    }

    @Override
    public void setPriority(int priority) {
        super.setPriority(priority);
    }

    @Override
    public void setName(String name){
        super.setName(name);
    }
}
