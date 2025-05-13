package MyDesktopAppMainDirectory.model;
import MyDesktopAppMainDirectory.model.Occurrence;
import MyDesktopAppMainDirectory.model.Event;

public class ToDoCalendarHelp extends Occurrence {
    public ToDoCalendarHelp(String date, int priority, String name) {
        super(date, priority, name);
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
