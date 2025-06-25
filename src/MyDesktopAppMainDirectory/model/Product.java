package MyDesktopAppMainDirectory.model;
import MyDesktopAppMainDirectory.model.List;
import javax.swing.*;

public class Product extends List {
    private String amount;
    public String getAmount(){
        return amount;
    }

    public Product(int priority, String name, String amount) {
        super(priority, name);
        this.amount = amount;
    }

    /*Extra no-argument constructor as a helper for ShoppingList inside Db class
    public Product() {
        super(0, "Default name");
        this.amount = "0";
    } subclass default constructor used*/

    public void setAmount() {       //Choosing amount/number to buy for each item
        while(true){
            this.amount = JOptionPane.
                    showInputDialog("Please choose quantity of products you want to add (max up to 1000): ");
            if(!checkInput(amount)){
                JOptionPane.showMessageDialog(null, "Amount too big or invalid input! Enter correct value.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                break;
            }
        }
       // }
        /*while(true){
            System.out.println("Please choose quantity of products you want to add (max up to 1000): ");
            try{
                amount = Integer.parseInt(choice.nextLine());
                if(amount <= 1000 && amount > 0) {
                    this.amount = amount;
                    break;
                }else{
                    System.out.print("Number not recognized or out of range, please try again: ");
                }
            }catch(NumberFormatException e){
                System.out.println("Choice not recognized. Please enter a valid number.");
            }
        }*/
    }

    private boolean checkInput(String number){
        int length = number.length();
        if(number.length() > 3 || number.equals("0")){
            return false;
        }
        for(int i = 0; i < length; i++){
            if(!Character.isDigit(number.charAt(i))){
                return false;
            }
        }
        return true;
    }
}
