import java.util.Scanner;

public class Occurrence implements Event {

    Scanner choice = new Scanner(System.in);
    String date = null;
    int priority = 1;

    public Occurrence(String date, int priority, String name) {
        this.date = choice.nextLine();
        this.priority = priority;
        this.name = name;
    }

    @Override
    public void setPriority(int priority) {
        System.out.print("Please choose priority from range 1-3, ");
        System.out.print("where 1 means lower priority, 2 means normal - " +
                "not something important, but to do ");
        System.out.println("and 3 means very high priority. ");
        this.priority = choice.nextInt();;
    }

    String name = "Default name";
    @Override
    public void setName(String name) {
        System.out.println("Please choose name for your event: ");
        this.name = choice.nextLine();
    }

}
