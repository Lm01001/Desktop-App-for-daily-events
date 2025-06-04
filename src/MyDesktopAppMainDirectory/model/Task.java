package MyDesktopAppMainDirectory.model;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import org.javatuples.Quartet;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Task extends List {
    private final LocalTime now = LocalTime.now();

    //If an app is used in a full-screen mode, time can appear in the corner
    public LocalTime getNow() {
        return now;
    }
    private final String nowAsAString = now.toString();
    public String getNowAsAString() {
        return nowAsAString;
    }

    private String tasksTime = "";
    public String getTasksTime() {
        return tasksTime;
    }

    private final LocalTime hourAndMinutes = LocalTime.of(now.getHour(), now.getMinute());
    /*public LocalTime getHourAndMinutes() {
        return hourAndMinutes;
    }*/
    private String hourAndMinutesAsAString = hourAndMinutes.toString();
    public String getHourAndMinutesAsAString() {
        return hourAndMinutesAsAString;
    }
    public void setHourAndMinutesAsString(String hourAndMinutesAsAString) {
        this.hourAndMinutesAsAString = hourAndMinutesAsAString;
    }

    private int index = 0;
    @Override
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

    int taskCompletion = 0;
    public int getTaskCompletion() {
        return taskCompletion;
    }
    public void setTaskCompletion(int taskCompletion) {
        this.taskCompletion = taskCompletion;
    }

    private int deleteTask = 0;
    public int getDeleteTask() {
        return deleteTask;
    }
    public void setDeleteTask(int deleteTask) {
        this.deleteTask = deleteTask;
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

    //Quartet used for creating a task excluding time, only name, status, importance (index -> automatic/incrementing after each task)
    /*private*/public record Quartet(String date, String name, String howImportant, String status) {};
    public Quartet quartetForTask;

    public Quartet quartetCreator(String date, String name, String howImportant, String status) {
        date = this.getTasksTime();
        howImportant = this.getHowImportant();
        status = this.getStatus();
        this.quartetForTask = new Quartet(date, name, howImportant, status);
        return quartetForTask;
    }
    public Quartet getQuartet(){
        return this.quartetForTask;
    }

    //HashMap with time as a key, and quartet including task details as a value
    /*private final*/public HashMap<String, Quartet> tasks = new HashMap<>();
    public HashMap<String, Quartet> getTasks() {
        return tasks;
    }
    public HashMap<String, Quartet> addHashMapValue() {
        Quartet task = quartetCreator(getHourAndMinutesAsAString(), getName(), getHowImportant(), getStatus());
        tasks.put(String.valueOf(getIndex()), task);
        setIndex(getIndex() + 1);
        return tasks;
    }

    public Task(int priority, String name, int index, String howImportant,
                String status, String time) {
        super(priority, name);
        this.index = index;
        this.howImportant = howImportant;
        this.status = status;
        this.tasksTime = time;
    }

    public Task() {
        super(0, "Default name");
        this.index = 0;
        this.howImportant = "Default";
        this.status = "Default";
    }

    //Choosing task's time, default time is equal to current time
    public void setTasksTime() {
        System.out.println("Please set the time of the task you want to add.");
        System.out.println("Time should be provided in format HH:MM, otherwise " +
                "time will be set to current time.");
        String taskTime = choice.nextLine();
        if(taskTime.length() != 5 || taskTime.charAt(2) != ':') {
            this.tasksTime = getHourAndMinutesAsAString();
        } else {
            this.tasksTime = taskTime;
        }
    }

    //Used during editing existing list, deleting/unchecking completed tasks
    public void ifCompleted() {
        System.out.print("Do you want to set task as completed?");
        System.out.println(" Choose 1 to complete the task.");
        while(true){
            try{
                this.taskCompletion = Integer.parseInt(choice.nextLine());
                if(this.taskCompletion != 1){
                    return;
                } else {
                    break;
                }
            }catch(NumberFormatException e){
                System.out.println("Value not recognized. Please enter a valid number.");
            }
        }
        System.out.println("Choose index of a task You want to mark as completed.");
        while(true){
            try{
                this.taskCompletion = Integer.parseInt(choice.nextLine());
            }catch(NumberFormatException e){
                System.out.println("Value not recognized. Please enter a valid number.");
            }
            for(Map.Entry<String, Quartet> tasks : tasks.entrySet()) {
                if(tasks.getKey().equals(String.valueOf(taskCompletion))) {
                    setStatus("Completed");
                    break;
                }else{
                    System.out.println("Index not found. Please enter a valid number.");
                }
            }
        }
    }

    //Deleting unwanted tasks, when creating or editing
    /*public void deleteTask()
    {
        while(true) {
            try {
                System.out.println("Which task would you like to delete? Choose an index of a task: ");
                int tasksIndexToDelete = Integer.parseInt(choice.nextLine());
                setDeleteTask(tasksIndexToDelete);
                boolean foundIndex = false;
                Iterator<Map.Entry<String, Quartet>> iterator = tasks.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<String, Quartet> entry = iterator.next();
                    if (entry.getKey() == getDeleteTask()) {
                        iterator.remove();
                        foundIndex = true;
                        break;
                    }
                }
                if (!foundIndex) {
                    System.out.println("Task with this index not found.");
                }
                break;
            } catch(NumberFormatException e) {
                System.out.println("Value not recognized. Please enter a valid number.");
            }
        }
    }*/

    //Showing already existing tasks before adding them to the db
    public void showActiveTasks() {
        int taskIndex = 0;
        if(tasks.isEmpty()) {
            System.out.println("There are no tasks in the database.");
            return;
        }
        System.out.println("Index     Time  Tasks Information");
        for(Map.Entry<String, Quartet> tasks : tasks.entrySet()) {
            System.out.println(taskIndex + "       " + tasks.getValue());
        }
    }

    public Task createTask() {
        super.setName(1);
        super.setPriority(getPriority());
        setTasksTime();
        return new Task(getPriority(), getName(), getIndex(), getHowImportant(),
                getStatus(), getTasksTime());
    }
}
