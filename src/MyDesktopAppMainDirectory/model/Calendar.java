package MyDesktopAppMainDirectory.model;
import MyDesktopAppMainDirectory.model.Event;
import MyDesktopAppMainDirectory.model.Occurrence;

import java.time.LocalDate;
import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Calendar extends Occurrence {

    String date = choice.nextLine();
    LocalDate chosenDate;

    public Calendar(String date, int priority,
                    String name, LocalDate chosenDate) {        //Constructor for situation on certain day
        super(date, priority, name);
        try{
            this.chosenDate = LocalDate.parse(date,
                    DateTimeFormatter.ISO_LOCAL_DATE);
        }catch(DateTimeParseException e){
            this.chosenDate = null;
        }
    }

    public String whatDayOfTheWeek() {      //Getting day of the week based on provided date
        if(chosenDate != null) {
            DayOfWeek dayOfWeek = chosenDate.getDayOfWeek();
            return dayOfWeek.toString();
        }else {
            return "Date not chosen yet or format is incorrect.";
        }
    }

    int priority = choice.nextInt();
    @Override
    public void setPriority(int priority) {     //Setting priority of certain event
        super.setPriority(priority);
    }

    String name = choice.nextLine();
    @Override
    public void setName(String name) {      //Setting name of the event
        super.setName(name);
    }
}
