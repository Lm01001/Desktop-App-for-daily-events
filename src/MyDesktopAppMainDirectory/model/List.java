package MyDesktopAppMainDirectory.model;
import MyDesktopAppMainDirectory.model.Event;
import java.util.Scanner;

public class List implements Event {
    Scanner choice = new Scanner(System.in);
    private String name = "Default name";
    public String getName() {
        return name;
    }

    private int priority = 1;
    public int getPriority() {
        return priority;
    }

    public List(int priority, String name) {     //List's constructor
        this.priority = priority;
        this.name = name;
    }

    //Extra no-argument constructor as a helper for ShoppingList inside Db class
    public List() {
        this.priority = 0;
        this.name = "Default name";
    }

    @Override
    public void setName(String name) {      //Setting action's name
        System.out.println("Please choose name of the product You want to add to the list: ");
        this.name = choice.nextLine();
    }

    @Override
    public void setPriority(int priority) {
        while(true){
            System.out.print("Please choose priority from range 1-3, ");
            System.out.print("where 1 means lower priority, 2 means normal - " +
                    "not something important, but to do ");
            System.out.println("and 3 means very high priority. ");
            try{
                this.priority = Integer.parseInt(choice.nextLine());
                if(this.priority >= 1 && this.priority <= 3){
                    break;
                }else{
                    System.out.print("Number out of range, please try again: ");
                }
            }catch(NumberFormatException e){
                System.out.println("Choice not recognized. Please enter a valid number.");
            }
        }
    }
}
