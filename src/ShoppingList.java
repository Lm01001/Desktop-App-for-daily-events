import java.util.Scanner;

public class ShoppingList extends List implements Event {

    int amount = 0;
    public ShoppingList(int priority, String name, int amount) {
        super(priority, name);
        System.out.println("Please choose quantity of products you want to add: ");
        this.amount = choice.nextInt();
    }
    public void deleteItem() {      //Deleting unwanted item

        System.out.println("Item deleted successfully.");
    }

    public boolean ifBought() {     //Checking item, if already bought (used if editing existing list)

        return false;
    }

    public void setAmount() {       //Choosing amount/number to buy for each item

        System.out.println("Amount set correctly.");
    }

    int priority = choice.nextInt();
    @Override
    public void setPriority(int priority) {
        super.setPriority(priority);
    }

    String name = choice.nextLine();
    @Override
    public void setName(String name) {
        super.setName(name);
    }
}
