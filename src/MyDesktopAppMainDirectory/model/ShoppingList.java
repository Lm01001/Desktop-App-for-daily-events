package MyDesktopAppMainDirectory.model;
import MyDesktopAppMainDirectory.model.Product;
import MyDesktopAppMainDirectory.model.Event;
import java.util.*;
import org.javatuples.Quartet;
import java.util.HashMap;

public class ShoppingList extends Product {
    private int index = 0;
    public int getIndex() {
        return index;
    }
    public void setIndex(int index) {
        this.index = index;
    }

    private int productsIndex = 0;
    public int getProductsIndex() {
        return productsIndex++;
    }
    public void setProductsIndex(int productsIndex) {
        this.productsIndex = productsIndex;
    }

    private boolean bought = false;
    public boolean getBought() {
        return bought;
    }
    public void setBought(boolean bought) {
            this.bought = bought;
    }

    private String status = "To buy";
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    private static record Quartet(Integer priority, String name, Integer amount, boolean bought) {};
    private Quartet quartetCreator(Integer priority, String name, Integer amount, boolean bought) {
        return new Quartet(priority , name, amount, this.bought);
    }

    private final HashMap<Integer, Quartet> finalProduct = new HashMap<>();
    public void addHashMapValue() {
        Quartet product = quartetCreator(getPriority(), getName(), getAmount(), bought);
        finalProduct.put(getProductsIndex(), product);
    }

    public ShoppingList(int priority, String name, int amount, String status, int index) {
        super(priority, name, amount);
        this.status = status;
        this.index = index;
    }

    public void addProduct() {
        Scanner scanner = new Scanner(System.in);
        super.setPriority(getPriority());
        super.setName(getName());
        super.setAmount(getAmount());
        if (getBought()) {
            setStatus("Bought");
            addHashMapValue();
            ShoppingList sl = new ShoppingList(getPriority(), getName(), getAmount(), status, index);
            setBought(false);
            setStatus("To buy");
        } else {
            addHashMapValue();
            ShoppingList sl = new ShoppingList(getPriority(), getName(), getAmount(), status, index);
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
                    setBought(true);
                    setStatus("Bought");
                    finalProduct.put(productsIndex, quartetCreator(getPriority(), getName(), getAmount(), bought));
                    ShoppingList sl = new ShoppingList(getPriority(), getName(), getAmount(), status, productsIndex);
                    setBought(false);
                    setStatus("To buy");
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
