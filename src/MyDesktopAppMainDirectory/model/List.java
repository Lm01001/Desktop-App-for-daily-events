package MyDesktopAppMainDirectory.model;
import MyDesktopAppMainDirectory.model.Event;
import java.util.Scanner;

public class List implements Event {

    Scanner choice = new Scanner(System.in);
    public static String name = "Default name";
    public static int priority = 1;

    public List(int priority, String name) {     //List's constructor
        List.priority = priority;
        List.name = name;
    }

    @Override
    public void setPriority(int priority) {
        while(true){
            System.out.print("Please choose priority from range 1-3, ");
            System.out.print("where 1 means lower priority, 2 means normal - " +
                    "not something important, but to do ");
            System.out.println("and 3 means very high priority. ");
            try{
                List.priority = Integer.parseInt(choice.nextLine());
                if(List.priority >= 1 && List.priority <= 3){
                    break;
                }else{
                    System.out.print("Number out of range, please try again: ");
                }
            }catch(NumberFormatException e){
                System.out.println("Choice not recognized. Please enter a valid number.");
            }
        }
    }

    @Override
    public void setName(String name) {      //Setting action's name
        System.out.println("Please choose name of the product You want to add to the list: ");
        List.name = choice.nextLine();
    }

}
