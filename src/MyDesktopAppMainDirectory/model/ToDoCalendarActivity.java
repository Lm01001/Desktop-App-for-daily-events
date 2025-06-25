package MyDesktopAppMainDirectory.model;

import MyDesktopAppMainDirectory.database.MongoDBService;
import com.google.gson.annotations.Expose;
import org.javatuples.Quartet;

import javax.swing.*;
import java.util.HashMap;
import java.time.ZonedDateTime;

public class ToDoCalendarActivity extends Calendar {


    private String clientName;
    private ZonedDateTime date;
    private Integer serviceNo;
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
        this.dutifully = ifMandatory ? "Yes" : "No";
    }

    private int index = 0;
    @Override
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

    public ToDoCalendarActivity(int priority, String name, String chosenDate, String dayOfTheWeek,
                                int index, String dutifully, String howImportant) {
        super(priority, name, chosenDate, dayOfTheWeek);
        this.index = index;
        this.dutifully = dutifully;
        this.howImportant = howImportant;
    }

    /*public ToDoCalendarActivity(ZonedDateTime date, String clientName, Integer serviceNo) {
        this.date = date;
        if (mongoDBService != null && clientName != null) {
            System.out.println(mongoDBService.getMongoClient().toString());
        } else {
            System.out.println("MongoDB service or client is not initialized.");
        }
        this.clientName = clientName;
        this.serviceNo = serviceNo;
    }*/

    //No-argument constructor to initialize an object in Db class avoiding NullPointerException
    public ToDoCalendarActivity() {
        super(0, "Default name", "", "");
        this.index = 0;
        this.dutifully = "Default";
        this.howImportant = "Default";
    }

    public int usersChoice = 0;
    public void isMandatory() {
        this.usersChoice = JOptionPane.showConfirmDialog(null,"Please choose if action is mandatory:",
                "Mandatory", JOptionPane.YES_NO_OPTION);
        if(usersChoice == JOptionPane.YES_OPTION) {
            setIfMandatory(true);
        }
            //System.out.print("Please choose if action is mandatory, ");
            //System.out.println("where 1 means yes, 2 means no.");
            /*try {
                usersChoice = Integer.parseInt(choice.nextLine());
                if(usersChoice == 1) {
                    setIfMandatory(true);
                    return true;
                }else if(usersChoice == 2) {
                    setIfMandatory(false);
                    return false;
                } else {
                    System.out.print("Number out of range, please try again: ");
                }
            }catch(NumberFormatException e){
                System.out.println("Choice not recognized. Please enter a valid number.");
            }*/
    }

    public ToDoCalendarActivity createAction() {
        super.setName(2);
        super.setPriority(getPriority());
        isMandatory();
        setDutifully(getIfMandatory());
        addHashMapValue();
        return new ToDoCalendarActivity(getPriority(), getName(), getChosenDate(),
                getDayOfTheWeekAsString(), getIndex(), getDutifully(), getHowImportant());
    }

    public ZonedDateTime getDate() {
        return date;
    }

   /* public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Integer getServiceNo() {
        return serviceNo;
    }

    public void setServiceNo(Integer serviceNo) {
        this.serviceNo = serviceNo;
    }*/

    @Override
    public String toString() {
        return "CalenderActivity{" +
                "date=" + date +
                ", clientName='" + clientName + '\'' +
                ", serviceNo=" + serviceNo +
                '}';
    }
}
