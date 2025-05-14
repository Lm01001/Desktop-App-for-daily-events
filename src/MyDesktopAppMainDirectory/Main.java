package MyDesktopAppMainDirectory;
import MyDesktopAppMainDirectory.model.*;
import MyDesktopAppMainDirectory.database.MongoDBService;

import java.time.DayOfWeek;

public class Main {
    public static void main(String[] args) {
        ToDoCalendarActivity todo = new ToDoCalendarActivity(1, "cos", "11-11-2022",DayOfWeek.MONDAY ,
            0, "yes", "a");
        todo.createAction();
    }
}