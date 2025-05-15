package MyDesktopAppMainDirectory.model;
import java.time.LocalDate;
import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Calendar extends Occurrence {
    private String chosenDate = LocalDate.now().toString();
    public String getChosenDate() {
        return chosenDate;
    }

    private DayOfWeek dayOfTheWeek = LocalDate.now().getDayOfWeek();
    public DayOfWeek getDayOfTheWeek() {
        return dayOfTheWeek;
    }

    public DateTimeFormatter formatterForDateDots = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    public DateTimeFormatter formatterForDateDashes = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public Calendar(int priority, String name, String chosenDate, DayOfWeek dayOfTheWeek) {        //Constructor for situation on certain day
        super(priority, name);
        this.chosenDate = chosenDate;
        this.dayOfTheWeek = dayOfTheWeek;
    }

    //No-argument constructor to use in Db class to initialize default class object
    public Calendar() {
        super(0, "Default name");
        this.chosenDate = "";
        this.dayOfTheWeek = DayOfWeek.SATURDAY;
    }

    public void setDate(String chosenDate) {
        while(true) {
            System.out.print("Choose a date for the action You'd like to create. ");
            System.out.println("Date should have format: DD-MM-YYYY or DD.MM.YYYY");
            try {
                this.chosenDate = choice.nextLine();
                if (this.chosenDate.charAt(2) == '-' && this.chosenDate.charAt(5) == '-') {
                    LocalDate formattedDate = LocalDate.parse(this.chosenDate, formatterForDateDashes);
                    this.dayOfTheWeek = formattedDate.getDayOfWeek();
                    break;
                } else if (this.chosenDate.charAt(2) == '.' && this.chosenDate.charAt(5) == '.') {
                    LocalDate formattedDate = LocalDate.parse(this.chosenDate, formatterForDateDots);
                    this.dayOfTheWeek = formattedDate.getDayOfWeek();
                } else {
                    System.out.print("No date provided or the format is incorrect, please try again: ");
                }
            } catch(DateTimeParseException e) {
                this.chosenDate = null;
                this.dayOfTheWeek = null;
            }
        }
    }

    public String whatDayOfTheWeek() {      //Getting day of the week based on provided date
        if(this.chosenDate != null) {
            return dayOfTheWeek.toString();
        }else {
            return "Date not chosen yet or format is incorrect.";
        }
    }
}
