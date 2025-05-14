package MyDesktopAppMainDirectory.model;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class Task extends List {
    private final LocalTime now = LocalTime.now();
    public LocalTime getNow() {
        return now;
    }
    private final String nowAsAString = now.toString();
    public String getNowAsAString() {
        return nowAsAString;
    }

    private final LocalTime hourAndMinutes = LocalTime.of(now.getHour(), now.getMinute());
    public LocalTime getHourAndMinutes() {
        return hourAndMinutes;
    }
    private final String hourAndMinutesAsAString = hourAndMinutes.toString();
    public String getHourAndMinutesAsAString() {
        return hourAndMinutesAsAString;
    }

    private int index = 0;
    public int getIndex() {
        return index;
    }
    public void setIndex(int index) {
        this.index = index;
    }

    private String status = "Not Completed";
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    private String howImportant = "Optional";
    public String getHowImportant() {
        if(getPriority() == 1) {
            this.howImportant = "Low priority";
        } else if(getPriority() == 2) {
            this.howImportant = "Optional, to do";
        } else {
            this.howImportant = "Very important";
        }
        return howImportant;
    }

    //int whichOne = 0;
    private static record Quartet(int index, String name, String howImportant, String status) {};
    public Quartet quartetCreator(int index, String name, String howImportant, String status) {
        return new Quartet(this.index, name, this.howImportant, this.status);
    }

    private final HashMap<Integer, Quartet>tasks = new HashMap<>();
    public void addHashMapValue() {
        Quartet task = quartetCreator(getIndex(), getName(), getHowImportant(), getStatus());
        tasks.put(getIndex(), task);
        setIndex(getIndex() + 1);
    }

    public Task(int priority, String name, int index, String nowAsAString, String howImportant,
                String status, String hourAndMinutesAsAString) {
        super(priority, name);
        this.index = index;
        this.howImportant = howImportant;
        this.status = status;
    }

    public void setTasksTime() {
        /*System.out.println("Please set the time of the task you want to add.");
        System.out.println("Time should be provided in format HH:MM, otherwise " +
                "time will be set to current time.");
        hour = choice.nextLine();
        if(hour.isEmpty() || hour.length() != 5 || hour.charAt(2) != ':') {
            hour = hourAndMinutes.toString();
        }
        System.out.println("Now, choose the name of your task: ");
        this.name = choice.nextLine();
        if(this.name.isEmpty()) {
            this.name = this.name;
        }
        tasks.put(this.hour, this.name);
        entries.put(index++, this.hour);*/
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

    @Override
    public String toString() {
        return "Task: [index] " + index + ". | [hour] " + hour +
                " | [name] " + name + " | [priority] " + priority;
    }
}
