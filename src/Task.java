import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Task extends List implements Event {

    LocalTime now = LocalTime.now();
    LocalTime hourAndMinutes = LocalTime.of(now.getHour(), now.getMinute());
    String hour = now.toString();
    int index = 0;
    int whichOne = 0;
    HashMap<String, String>tasks = new HashMap<>();
    HashMap<Integer, String>entries = new HashMap<>();

    public Task(int priority, String name) {
        super(priority, name);
        System.out.println("Please set the time of the task you want to add.");
        System.out.println("Time should be provided in format HH:MM, otherwise " +
                "time will be set to current time.");
        hour = choice.nextLine();
        if(hour.isEmpty() || hour.length() != 5 || hour.charAt(2) != ':') {
            hour = hourAndMinutes.toString();
        }
        System.out.println("Now, choose the name of your task: ");
        this.name = choice.nextLine();
        if(this.name.isEmpty()) {
            this.name = List.name;
        }
        tasks.put(this.hour, this.name);
        entries.put(index++, this.hour);
    }

    public boolean ifCompleted() {      //Used during editing existing list, deleting/unchecking completed tasks
        System.out.println("Do you want to set task as completed?");
        System.out.println("Choose ");
        return false;
    }

    public void deleteTask()        //Deleting unwanted tasks, when creating or editing
    {
        System.out.println("Which task would you like to delete? Choose index of a task: ");
        int tasksIndexToDelete = Integer.parseInt(choice.nextLine());

        if(entries.containsKey(tasksIndexToDelete)) {
            String remove = entries.get(tasksIndexToDelete);
            entries.remove(tasksIndexToDelete);
            tasks.remove(remove);
            System.out.println("Task with index " + tasksIndexToDelete + " at "
                    + remove + " has been deleted successfully.");
        } else {
            System.out.println("Task with this index not found.");
        }
    }

    public void showExistingTasks() {
        int taskIndex = 0;
        if(tasks.isEmpty()) {
            System.out.println("There are no tasks in the database.");
            return;
        }
        System.out.println("Index   Time      Name");
        for(Map.Entry<String, String> entry : tasks.entrySet()) {
            System.out.println(taskIndex + "       " + entry.getKey() + "     " + entry.getValue());
        }
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

    @Override
    public String toString() {
        return "Task: [index] " + index + ". | [hour] " + hour +
                " | [name] " + name + " | [priority] " + priority;
    }
}
