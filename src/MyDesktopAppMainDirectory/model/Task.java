package MyDesktopAppMainDirectory.model;
import java.time.LocalTime;
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

    private String tasksTime = getNowAsAString();
    public String getTasksTime() {
        return tasksTime;
    }
    public void setTasksTime(String tasksTime) {
        this.tasksTime = tasksTime;
    }

    String chosenTime;
    public String getChosenTime() {
        return chosenTime;
    }

    private final LocalTime hourAndMinutes = LocalTime.of(now.getHour(), now.getMinute());
    /*public LocalTime getHourAndMinutes() {
        return hourAndMinutes;
    }*/
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
    private static record Quartet(int index, String name, String howImportant, String status) {};
    public Quartet quartetCreator(int index, String name, String howImportant, String status) {
        return new Quartet(this.index, name, this.howImportant, this.status);
    }

    //HashMap with time as a key, and quartet including task details as a value
    private final HashMap<String, Quartet>tasks = new HashMap<>();
    public void addHashMapValue() {
        Quartet task = quartetCreator(getIndex(), getName(), getHowImportant(), getStatus());
        tasks.put(getHourAndMinutesAsAString(), task);
        setIndex(getIndex() + 1);
    }

    public Task(int priority, String name, int index, String nowAsAString, String howImportant,
                String status, String hourAndMinutesAsAString) {
        super(priority, name);
        this.index = index;
        this.howImportant = howImportant;
        this.status = status;
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
        chosenTime = choice.nextLine();
        if(chosenTime.length() != 5 || chosenTime.charAt(2) != ':') {
            this.chosenTime = getTasksTime();
        } else {
            setTasksTime(chosenTime);
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
                if(tasks.getValue().index() == taskCompletion) {
                    setStatus("Completed");
                    break;
                }else{
                    System.out.println("Index not found. Please enter a valid number.");
                }
            }
        }
    }

    //Deleting unwanted tasks, when creating or editing
    public void deleteTask()
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
                    if (entry.getValue().index() == getDeleteTask()) {
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
    }

    //Showing already existing tasks before adding them to the db
    public void showActiveTasks() {
        int taskIndex = 0;
        if(tasks.isEmpty()) {
            System.out.println("There are no tasks in the database.");
            return;
        }
        System.out.println("Time     Tasks Information");
        for(Map.Entry<String, Quartet> tasks : tasks.entrySet()) {
            System.out.println(taskIndex + "       " + tasks.getKey() + "     " + tasks.getValue());
        }
    }

    public Task createTask() {
        super.setName(getName());
        super.setPriority(getPriority());
        setTasksTime();
        return new Task(getPriority(), getName(), getIndex(), getNowAsAString(), getHowImportant(),
                getStatus(), getHourAndMinutesAsAString());
    }
}
