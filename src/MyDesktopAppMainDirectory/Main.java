package MyDesktopAppMainDirectory;
import MyDesktopAppMainDirectory.model.*;

public class Main {
    public static void main(String[] args) {
        //List list = new List(List.priority, List.name);
        /*Task task = new Task(List.priority, List.name);
        task.setPriority(List.priority);
        task.showExistingTasks();
        System.out.println();
        System.out.println(task);
        System.out.println();
        task.deleteTask();
        System.out.println();
        task.showExistingTasks();*/
        ShoppingList s = new ShoppingList(List.priority, List.name, Product.amount, ShoppingList.bought);
        s.addProduct();
        s.ifBought();
        System.out.println();
        //System.out.println(ShoppingList.product);

        for(Integer key : ShoppingList.finalProduct.keySet()){
            System.out.println(ShoppingList.finalProduct.get(key));
        }

        System.out.println();
        s.deleteItem();
        for(Integer key : ShoppingList.finalProduct.keySet()){
            System.out.println(ShoppingList.finalProduct.get(key));
        }
    }
}