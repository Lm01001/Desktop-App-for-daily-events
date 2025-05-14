package MyDesktopAppMainDirectory.model;
import java.util.Scanner;

public class Occurrence implements Event {
    Scanner choice = new Scanner(System.in);

    private int priority = 1;
    public int getPriority() {
        return priority;
    }

    String name = "Default name";
    public String getName() {
        return name;
    }

    public Occurrence(int priority, String name) {
        this.priority = priority;
        this.name = name;
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

    @Override
    public void setName(String name) {
        System.out.println("Please choose name for your event: ");
        this.name = choice.nextLine();
    }

}
