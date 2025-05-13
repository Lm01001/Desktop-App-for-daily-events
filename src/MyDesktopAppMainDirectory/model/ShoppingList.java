package MyDesktopAppMainDirectory.model;
import MyDesktopAppMainDirectory.model.Product;
import MyDesktopAppMainDirectory.model.Event;
import java.util.*;
import org.javatuples.Quartet;
import java.util.HashMap;
import java.util.Map;

public class ShoppingList extends Product {
    int index = 0;
    int decision = 1;
    int productsIndex = 0;
    public static boolean bought = false;
    public static Quartet<Integer, String, Integer, String> product =
            Quartet.with(List.priority, List.name, Product.amount, "To buy");
    public static HashMap<Integer, Quartet<Integer, String, Integer, String>>finalProduct = new HashMap<>();

    public ShoppingList(int priority, String name, int amount, boolean bought) {
        super(priority, name, amount);
        ShoppingList.bought = bought;
    }

    public void addProduct() {
    for(int i = 0; i <3 ; i++) {
        Scanner scanner = new Scanner(System.in);
        super.setPriority(priority);
        product = product.setAt0(priority);
        super.setName(name);
        product = product.setAt1(name);
        super.setAmount(amount);
        product = product.setAt2(amount);
        ShoppingList newProduct = new ShoppingList(priority, name, amount, bought);
        if (bought) {
            product = product.setAt3("Bought");
        }
        finalProduct.put(index++, product);
        //System.out.println(product);
    }
    }

    public void deleteItem() {      //Deleting unwanted item
        System.out.println("Select index of a product You'd like to delete: ");
        while(true){
            try{
                this.productsIndex = Integer.parseInt(choice.nextLine());
                if(finalProduct.containsKey(productsIndex)){
                    finalProduct.remove(productsIndex);
                    break;
                }else{
                    System.out.println("Index not found. Please enter a valid number.");
                }
            }catch(NumberFormatException e){
                System.out.println("Value not recognized. Please enter a valid number.");
            }
        }
        System.out.println("Item deleted successfully.");
    }

    public void ifBought() {     //Checking item, if already bought (used if editing existing list)
        System.out.println("Select index of a product You've already bought: ");
        while(true){
            try{
                this.productsIndex = Integer.parseInt(choice.nextLine());
                if(finalProduct.containsKey(productsIndex)){
                    product = finalProduct.get(productsIndex);
                    product = product.setAt3("Bought");
                    finalProduct.put(productsIndex, product);
                    break;
                }else{
                    System.out.println("Index not found. Please enter a valid number.");
                }
            }catch(NumberFormatException e){
                    System.out.println("Value not recognized. Please enter a valid number.");
            }
        }
    }
}
