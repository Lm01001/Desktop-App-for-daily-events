package MyDesktopAppMainDirectory.database;
import MyDesktopAppMainDirectory.model.*;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.javatuples.Quartet;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import com.google.gson.Gson;
import org.bson.Document;

public class MongoDBService {
    private final MongoClient mongoClient;
    public MongoClient getMongoClient() {
        return mongoClient;
    }
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

    private String category = "";
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }

    private String arrayAccessKey = "";
    public String getArrayAccessKey() {
        return arrayAccessKey;
    }
    public void setArrayAccessKey(String arrayAccessKey) {
        this.arrayAccessKey = arrayAccessKey;
    }

    public MongoDBService(String dbName, String collectionName) {
        this.mongoClient = MongoClients.create("mongodb://localhost:27017");
        this.database = mongoClient.getDatabase(dbName);
        this.collection = database.getCollection(collectionName);
    }

    public void insertShoppingList() {
        Document mainDocWithAShoppingList = new Document("category", "shopping List")
                .append("index", getShoppingListId());
        List<Document> arrayForShoppingLists = new ArrayList<>();

        boolean addingInProgress = true;
        while(addingInProgress) {
            ShoppingList shoppinglist = new ShoppingList();
            shoppinglist.addProduct();
            Document newShoppingList = new Document("category", "shopping List").append("index", shoppinglist.getIndex()).append("product",
                        shoppinglist.getName()).append("amount: ", shoppinglist.getAmount()).append("priority", shoppinglist.getPriority()).
                        append("status", shoppinglist.getStatus());
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
        Document mainDocWithACalendarPage = new Document("category", "calendar event")
                .append("index", getCalendarId());
        List<Document> arrayForCalendarPages = new ArrayList<>();

        boolean addingInProgress = true;
        while(addingInProgress) {
            ToDoCalendarActivity calendar = new ToDoCalendarActivity();
            calendar.createAction();
            Document newEventInCalendar = new Document("category", "calendar event").append("index", calendar.getIndex()).
                    append("date", calendar.getChosenDate()).append("day", calendar.getDayOfTheWeek()).append("task", calendar.getName()).
                    append("importance", calendar.getHowImportant()).append("obligatory", calendar.getDutifully());
            arrayForCalendarPages.add(newEventInCalendar);
            if(!calendar.ifStillInProgress().equals("yes")) {
                addingInProgress = false;
            }
        }
        mainDocWithACalendarPage.append("pages", arrayForCalendarPages);
        collection.insertOne(mainDocWithACalendarPage);
        setCalendarId(getCalendarId() + 1);
    }

    public void insertTask(List<Task> tasks) {
        Document mainDocWithATask = new Document("category", "task")
                .append("index", getTaskId());
        List<Document> arrayForTasks = new ArrayList<>();

        boolean addingInProgress = true;
        while(addingInProgress) {
            Task task = new Task();
            task.createTask();
            Document newTask = new Document("category", "task").append("time", task.getChosenTime()).append("index", task.getIndex()).
                    append("name", task.getName()).append("importance", task.getHowImportant()).append("status", task.getStatus());
            arrayForTasks.add(newTask);
            if(!task.ifStillInProgress().equals("yes")) {
                addingInProgress = false;
            }
        }
        mainDocWithATask.append("tasks", arrayForTasks);
        collection.insertOne(mainDocWithATask);
        setTaskId(getTaskId() + 1);
    }

    //Instance of Gson class from Gson library, used to convert between Java objects and JSON Strings
    Gson gson = new Gson();

    //Generic method it(<T>) can return T here based on passed entityType
    private <T> T convertToEntity(Document doc, Class<T> entityType) {
        String json = doc.toJson();
        return gson.fromJson(json, entityType);
    }

    public <T> List<T> findAll(Class<T> entityType) {
        List<T> results = new ArrayList<>();

        if(entityType.equals(Task.class)) {
            setCategory("task");
            setArrayAccessKey("tasks");
        } else if(entityType.equals(ShoppingList.class)) {
            setCategory("shopping List");
            setArrayAccessKey("shoppingLists");
        } else if(entityType.equals(ToDoCalendarActivity.class)) {
            setCategory("calendar event");
            setArrayAccessKey("pages");
        } else {
            throw new IllegalArgumentException("Unsupported entity type: " +
                    entityType.getName());
        }

        FindIterable<Document> docs = collection.find(Filters.
                eq("category", getCategory()));

        for(Document document : docs) {
        //To hide a warning about the unchecked cast
            @SuppressWarnings("unchecked")
            List<Document> docsInsideAnArray = (List<Document>) document.get(getArrayAccessKey());
            if(docsInsideAnArray != null) {
                for(Document docsInside : docsInsideAnArray) {
                    T object = convertToEntity(docsInside, entityType);
                    results.add(object);
                }
            }
        }
        return results;
    }

    public <T extends Event>  T findById(Class<T> entityType, int id) {
        List<T> allResults = new ArrayList<>();
        if(entityType.equals(Task.class)) {
            setCategory("task");
            setArrayAccessKey("tasks");
        } else if(entityType.equals(ShoppingList.class)) {
            setCategory("shopping List");
            setArrayAccessKey("shoppingLists");
        } else if(entityType.equals(ToDoCalendarActivity.class)) {
            setCategory("calendar event");
            setArrayAccessKey("pages");
        } else {
            throw new IllegalArgumentException("Unsupported entity type: " +
                    entityType.getName());
        }
        FindIterable<Document> docs = collection.find(Filters.
                eq("category", getCategory()));

        for(Document document : docs) {
            //To hide a warning about the unchecked cast
            @SuppressWarnings("unchecked")
            List<Document> docsInsideAnArray = (List<Document>) document.get(getArrayAccessKey());
            if(docsInsideAnArray != null) {
                for(Document docsInside : docsInsideAnArray) {
                    T object = convertToEntity(docsInside, entityType);
                    allResults.add(object);
                }
            }
        }
        for(T object : allResults) {
            if(object.getIndex() == id) {
                return object;
            }
        }
        return null;
    }

    public <T extends Event> void deletePosition(Class<T> entityType, int id) {
        List<T> allResults = new ArrayList<>();
        if(entityType.equals(Task.class)) {
            setCategory("task");
            setArrayAccessKey("tasks");
        } else if(entityType.equals(ShoppingList.class)) {
            setCategory("shopping List");
            setArrayAccessKey("shoppingLists");
        } else if(entityType.equals(ToDoCalendarActivity.class)) {
            setCategory("calendar event");
            setArrayAccessKey("pages");
        } else {
            throw new IllegalArgumentException("Unsupported entity type: " +
                    entityType.getName());
        }
        FindIterable<Document> docs = collection.find(Filters.
                eq("category", getCategory()));

        for(Document document : docs) {
            //To hide a warning about the unchecked cast
            @SuppressWarnings("unchecked")
            List<Document> docsInsideAnArray = (List<Document>) document.get(getArrayAccessKey());
            if(docsInsideAnArray != null) {
                for(Document docsInside : docsInsideAnArray) {
                    if(docsInside.getInteger("index", -1) == id) {
                        Bson filter = Filters.eq("_id", document.getObjectId("_id"));
                        Bson update = Updates.pull(getArrayAccessKey(), Filters.eq("index", id));
                        collection.updateOne(filter, update);
                        return;
                    }
                }
            }
        }
    }

    /*public <T> void editPosition(Class<T> entityType, int id) {

    }

    public void close() {
        mongoClient.close();
    }*/
}
