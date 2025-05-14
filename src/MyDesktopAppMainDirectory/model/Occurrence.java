package MyDesktopAppMainDirectory.model;
import java.util.Scanner;

public class Occurrence implements Event {

    Scanner choice = new Scanner(System.in);
    private String date = null;
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    pint priority = 1;

    public Occurrence(String date, int priority, String name) {
        this.date = choice.nextLine();
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
                    System.out.println("Priority has been set to " + this.priority + ". ");
                    break;
                }else{
                    System.out.print("Number out of range, please try again: ");
                }
            }catch(NumberFormatException e){
                System.out.println("Choice not recognized. Please enter a valid number.");
            }
        }
    }

    String name = "Default name";
    @Override
    public void setName(String name) {
        System.out.println("Please choose name for your event: ");
        this.name = choice.nextLine();
    }

}
