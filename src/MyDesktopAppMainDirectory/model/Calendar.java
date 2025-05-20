package MyDesktopAppMainDirectory.model;
import java.time.LocalDate;
import java.time.DayOfWeek;
import java.time.ZonedDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Calendar extends Occurrence {
    private String chosenDate = LocalDate.now().toString();
    public String getChosenDate() {
        return chosenDate;
    }
    private ZonedDateTime chosenDateTime;
    private ZonedDateTime parseChosenDateTime(String dateString) {
        if(dateString == null || dateString.isEmpty())
            return null;
        try {
            if(dateString.charAt(2) == '-' && dateString.charAt(5) == '-') {
                LocalDate ld = LocalDate.parse(dateString, formatterForDateDashes);
                return ld.atStartOfDay(ZoneId.systemDefault());
            } else if(dateString.charAt(2) == '.' && dateString.charAt(5) == '.') {
                LocalDate ld = LocalDate.parse(dateString, formatterForDateDots);
                return ld.atStartOfDay(ZoneId.systemDefault());
            }
        } catch(DateTimeParseException e) {
            return null;
        }
        return null;
    }

    private DayOfWeek dayOfTheWeek = LocalDate.now().getDayOfWeek();
    public DayOfWeek getDayOfTheWeek() {
        return dayOfTheWeek;
    }
    private String dayOfWeekAsAString =getDayOfTheWeek().toString().substring(0,2);
    public String getDayOfTheWeekAsString() {
        return dayOfWeekAsAString;
    }

    public DateTimeFormatter formatterForDateDots = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    public DateTimeFormatter formatterForDateDashes = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public Calendar(int priority, String name, String chosenDate, String DayOfTheWeekAsString) {        //Constructor for situation on certain day
        super(priority, name);
        this.chosenDate = chosenDate;
        this.dayOfWeekAsAString = DayOfTheWeekAsString;
        this.chosenDateTime = parseChosenDateTime(chosenDate);
    }

    //No-argument constructor to use in Db class to initialize default class object
    public Calendar() {
        super(0, "Default name");
        this.chosenDate = "";
        this.dayOfTheWeek = DayOfWeek.SATURDAY;
    }

    public void setDate(String chosenDate) {
        while (true) {
            System.out.print("Choose a date for the action You'd like to create. ");
            System.out.println("Date should have format: DD-MM-YYYY or DD.MM.YYYY");
            String input = choice.nextLine();
            this.chosenDate = input;
            this.chosenDateTime = parseChosenDateTime(input);
            if(this.chosenDateTime != null) {
                this.dayOfTheWeek = this.chosenDateTime.getDayOfWeek();
                this.dayOfWeekAsAString = this.dayOfTheWeek.toString().substring(0, 2);
                break;
            } else {
                System.out.print("No date provided or the format is incorrect, please try again: ");
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
