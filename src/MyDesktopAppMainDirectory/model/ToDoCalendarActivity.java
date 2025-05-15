package MyDesktopAppMainDirectory.model;
import org.javatuples.Quartet;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;

public class ToDoCalendarActivity extends Calendar {
    private boolean ifMandatory = false;
    public boolean getIfMandatory() {
        return ifMandatory;
    }
    public void setIfMandatory(boolean ifMandatory) {
        this.ifMandatory = ifMandatory;
    }

    private String dutifully = "No";
    public String getDutifully() {
        return dutifully;
    }
    public void setDutifully(boolean ifMandatory) {
        if(ifMandatory) {
            this.dutifully = "Yes";
        } else {
            this.dutifully = "No";
        }
    }

    private int index = 0;
    public int getIndex() {
        return index;
    }
    public void setIndex(int index) {
        this.index = index;
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

    private static record Quarter(String date, String dayOfWeek, String name, boolean ifMandatory) {};
    public Quartet createQuartet(String date, String dayOfTheWeek, String name, boolean ifMandatory) {
        return new Quartet(date, dayOfTheWeek, name, this.ifMandatory);
    }

    private final HashMap<Integer, Quartet> activity = new HashMap<>();
    public void addHashMapValue() {
        Quartet activitySet = createQuartet(getChosenDate(), whatDayOfTheWeek(), getName(), this.ifMandatory);
        activity.put(getIndex(), activitySet);
        setIndex(getIndex() + 1);
    }

    public ToDoCalendarActivity(int priority, String name, String chosenDate, DayOfWeek dayOfTheWeek,
                                int index, String dutifully, String howImportant) {
        super(priority, name, chosenDate, dayOfTheWeek);
        this.index = index;
        this.dutifully = dutifully;
        this.howImportant = howImportant;
    }
    public int usersChoice = 0;
    public void isMandatory() {
        while(true) {
            System.out.print("Please choose if action is mandatory, ");
            System.out.println("where 1 means yes, 2 means no.");
            try {

                usersChoice = Integer.parseInt(choice.nextLine());
                if(usersChoice == 1) {
                    this.ifMandatory = true;
                    break;
                }else if(usersChoice == 0) {
                    this.ifMandatory = false;
                    break;
                } else {
                    System.out.print("Number out of range, please try again: ");
                }
            }catch(NumberFormatException e){
                System.out.println("Choice not recognized. Please enter a valid number.");
            }
        }
    }

    public ToDoCalendarActivity createAction() {
        super.setName(getName());
        super.setPriority(getPriority());
        super.setDate(getChosenDate());
        isMandatory();
        addHashMapValue();
        return new ToDoCalendarActivity(getPriority(), getName(), getChosenDate(),
                getDayOfTheWeek(), getIndex(), getDutifully(), getHowImportant());
        /*System.out.println(activity);
        System.out.println(todo.getPriority() + " " + todo.getName()+ " " + todo.getChosenDate()+ " " +
                todo.getDayOfTheWeek()+ " " +todo.getIndex()+ " " +todo.getDutifully()+ " " + todo.getHowImportant());*/
    }
}
