import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Task extends List implements Event {

    LocalTime now = LocalTime.now();
    String hour = now.toString();

    public Task(int priority, String name,  String hour) {
        super(priority, name);
        System.out.println("Please set the time of the task you want to add.");
        System.out.println("Time should be provided in format HH:MM, otherwise " +
                "time will be set to current time.");
        this.hour = choice.nextLine();
    }

    public boolean ifCompleted() {      //Used during editing existing list, deleting/unchecking completed tasks
        System.out.println("Do you want to set task as completed?");
        return false;
    }

    public void deleteTask()        //Deleting unwanted tasks, when creating or editing
    {
        System.out.println("Task deleted successfully.");
    }

    int priority = choice.nextInt();
    @Override
    public void setPriority(int priority) {
        super.setPriority(priority);
    }

    String name = choice.nextLine();
    @Override
    public void setName(String name) {
        super.setName(name);
    }
}
