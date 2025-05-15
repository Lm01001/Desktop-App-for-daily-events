package MyDesktopAppMainDirectory.database;
import MyDesktopAppMainDirectory.model.*;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.javatuples.Quartet;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class MongoDBService {
    private final MongoClient mongoClient;
    private final MongoDatabase database;
    private final MongoCollection<Document> collection;
    private int shoppingListId = 1;
    public int getShoppingListId() {
        return shoppingListId;
    }
    public void setShoppingListId(int shoppingListId) {
        this.shoppingListId = shoppingListId;
    }

    private int taskId = 1;
    public int getTaskId() {
        return taskId;
    }
    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    private int calendarId = 1;
    public int getCalendarId() {
        return calendarId;
    }
    public void setCalendarId(int calendarId) {
        this.calendarId = calendarId;
    }

    public MongoDBService(String dbName, String collectionName) {
        this.mongoClient = MongoClients.create("mongodb://localhost:27017");
        this.database = mongoClient.getDatabase(dbName);
        this.collection = database.getCollection(collectionName);
    }

    public void insertShoppingList() {
        Document mainDocWithAShoppingList = new Document("category", "Shopping List")
                .append("index", getShoppingListId());
        List<Document> arrayForShoppingLists = new ArrayList<>();

        boolean addingInProgress = true;
        while(addingInProgress) {
            ShoppingList shoppinglist = new ShoppingList();
            shoppinglist.addProduct();
            Document newShoppingList = new Document("category: ", "Shopping List").append("index: ", shoppinglist.getIndex()).append("product: ",
                        shoppinglist.getName()).append("amount: ", shoppinglist.getAmount()).append("priority: ", shoppinglist.getPriority()).
                        append("status: ", shoppinglist.getStatus());
            arrayForShoppingLists.add(newShoppingList);
            if(!shoppinglist.ifStillInProgress().equals("yes")) {
                addingInProgress = false;
            }
        }
        mainDocWithAShoppingList.append("shoppingLists", arrayForShoppingLists);
        collection.insertOne(mainDocWithAShoppingList);
        setShoppingListId(getShoppingListId() + 1);
    }

    public void insertCalendarEvent(List<ToDoCalendarActivity> calendarActivities) {
        Document mainDocWithACalendarPage = new Document("category", "Calendar event")
                .append("index", getCalendarId());
        List<Document> arrayForCalendarPages = new ArrayList<>();

        for(ToDoCalendarActivity calendar : calendarActivities) {
            Document newEventInCalendar = new Document("category", "Calendar event").append("index", calendar.getIndex()).
                    append("date", calendar.getChosenDate()).append("day", calendar.getDayOfTheWeek()).append("task", calendar.getName()).
                    append("importance", calendar.getHowImportant()).append("obligatory", calendar.getDutifully());
            arrayForCalendarPages.add(newEventInCalendar);
        }
        mainDocWithACalendarPage.append("pages", arrayForCalendarPages);
        collection.insertOne(mainDocWithACalendarPage);
        setCalendarId(getCalendarId() + 1);
    }

    public void insertTask(List<Task> tasks) {
        Document mainDocWithATask = new Document("category", "Task")
                .append("index", getTaskId());
        List<Document> arrayForTasks = new ArrayList<>();
        for(Task task : tasks) {
            Document newTask = new Document("category", "Task").append("time", task.getChosenTime()).append("index", task.getIndex()).
                    append("name", task.getName()).append("importance", task.getHowImportant()).append("status", task.getStatus());
            arrayForTasks.add(newTask);
        }
        mainDocWithATask.append("tasks", arrayForTasks);
        collection.insertOne(mainDocWithATask);
        setTaskId(getTaskId() + 1);
    }

    /*public FindIterable<Document> findAllDocuments() {
        // implement find all logic
        return collection.find();
    }

    public Document findByField(String field, Object value) {
        // implement filter logic
        return collection.find(Filters.eq(field, value)).first();
    }

    public void updateDocument(Bson filter, Bson update) {
        // implement update logic
        //collection.updateOne(filter, update);
    }

    public void deleteDocument(Bson filter) {
        // implement delete logic
        collection.deleteOne(filter);
    }*/

    public void close() {
        mongoClient.close();
    }
}
