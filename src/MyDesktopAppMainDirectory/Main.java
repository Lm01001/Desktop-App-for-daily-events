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
    }
}