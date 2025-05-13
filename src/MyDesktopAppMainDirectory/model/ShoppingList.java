package MyDesktopAppMainDirectory.model;
import MyDesktopAppMainDirectory.model.Product;
import MyDesktopAppMainDirectory.model.Event;
import java.util.*;
import org.javatuples.Quartet;

public class ShoppingList extends Product {
    public static boolean bought = false;
    String action = "To buy";

    public ShoppingList(int priority, String name, int amount, boolean bought) {
        super(priority, name, amount);
        ShoppingList.bought = bought;
    }

    public void addProduct() {
        Scanner scanner = new Scanner(System.in);
        super.setPriority(priority);
        super.setName(name);
        super.setAmount(amount);
        ShoppingList newProduct = new ShoppingList(priority, name, amount, bought);
        if(bought){
            action = "Bought";
        }
        Quartet<Integer, String, Integer, String> product =
                Quartet.with(priority, name, amount, action);
        System.out.println(product);
    }

    public void deleteItem() {      //Deleting unwanted item

        System.out.println("Item deleted successfully.");
    }

    public boolean ifBought() {     //Checking item, if already bought (used if editing existing list)

        return false;
    }

    @Override
    public void setPriority(int priority) {
        super.setPriority(priority);
    }

    @Override
    public void setName(String name) {
        super.setName(name);
    }

    @Override
    public void setAmount(int amount) {
        super.setAmount(amount);
    }
}
