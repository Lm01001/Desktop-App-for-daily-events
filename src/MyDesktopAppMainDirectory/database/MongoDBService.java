package MyDesktopAppMainDirectory.database;
import MyDesktopAppMainDirectory.model.*;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.mongodb.client.*;
import com.mongodb.client.model.*;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import com.google.gson.Gson;

public class MongoDBService {
    String CONNECTION_STRING = "mongodb://localhost:27017";
    String DATABASE_NAME = "DesktopAppForDailyEvents";

    private final MongoClient mongoClient;
    public MongoClient getMongoClient() {
        return mongoClient;
    }
    private final MongoDatabase database;
    public MongoDatabase getDatabase() {
        return database;
    }

    public MongoDBService() {
        this.mongoClient = MongoClients.create(CONNECTION_STRING);
        this.database = mongoClient.getDatabase(DATABASE_NAME);
    }

    MongoCollection<Document> collection;
    public MongoCollection<Document> getCollection() {
        return this.collection;
    }
    public void setCollection(String collectionName) {
        this.collection = database.getCollection(collectionName);
    }


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

    public void insertShoppingList() {
        Document mainDocWithAShoppingList = new Document("category", "shoppingList")
                .append("index", getShoppingListId());
        setCollection("shoppingList");
        List<Document> arrayForShoppingLists = new ArrayList<>();

        boolean addingInProgress = true;
        while(addingInProgress) {
            ShoppingList shoppinglist = new ShoppingList();
            shoppinglist.addProduct();
            Document newShoppingList = new Document("category", "shoppingList " + shoppinglist.getIndex()).append("product",
                        shoppinglist.getName()).append("amount", shoppinglist.getAmount()).append("priority", shoppinglist.getPriority()).
                        append("status", shoppinglist.getStatus());
            arrayForShoppingLists.add(newShoppingList);
            if(!shoppinglist.ifStillInProgress().equals("yes")) {
                addingInProgress = false;
            }
        }
        mainDocWithAShoppingList.append("shoppingList", arrayForShoppingLists);
        collection.insertOne(mainDocWithAShoppingList);
        setShoppingListId(getShoppingListId() + 1);
    }


    public void insertCalendarEvent(List<ToDoCalendarActivity> calendarActivities) {
        setCollection("calendarEvent");
        Document mainDocWithACalendarPage = new Document("category", "calendarEvents " + getCalendarId());
        List<Document> arrayForCalendarPages = new ArrayList<>();

        for(ToDoCalendarActivity calendar : calendarActivities) {
            Document newEventInCalendar = new Document("category", "Event " + calendar.getIndex()).
                    append("date", calendar.getChosenDate()).append("day", calendar.getDayOfTheWeek().toString().toLowerCase()).
                    append("task", calendar.getName()).append("importance", calendar.getHowImportant()).
                    append("obligatory", calendar.getDutifully());
            arrayForCalendarPages.add(newEventInCalendar);
            calendar.setIndex(calendar.getIndex() + 1);
        }
        mainDocWithACalendarPage.append("pages", arrayForCalendarPages);
        collection.insertOne(mainDocWithACalendarPage);
        setCalendarId(getCalendarId() + 1);
    }

    public void insertTask(List<Task> tasks) {
        Document mainDocWithATask = new Document("category", "task")
                .append("index", getTaskId());
        setCollection("task");
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
        mainDocWithATask.append("task", arrayForTasks);
        collection.insertOne(mainDocWithATask);
        setTaskId(getTaskId() + 1);
    }

    //Instance of Gson class from Gson library, used to convert between Java objects and JSON Strings
    Gson gson = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .disableInnerClassSerialization()
            .create();

    /*private <T> List<T> loadFromJsonResources(Class<T> entityType) {
        String fileName;
        if(entityType.equals(Task.class)) {
            fileName = "task.json";
        } else if(entityType.equals(ShoppingList.class)) {
            fileName = "shoppingList.json";
        } else if(entityType.equals(ToDoCalendarActivity.class)) {
            fileName = "calendarEvent.json";
        } else {
            throw new IllegalArgumentException("Unsupported entity type");
        }

        InputStream is = getClass().getClassLoader().getResourceAsStream(fileName);
        if (is == null) return new ArrayList<>();
        try (InputStreamReader reader = new InputStreamReader(is)) {
            Type listType = TypeToken.getParameterized(List.class, entityType).getType();
            return gson.fromJson(reader, listType);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }*/

    //Generic method it(<T>) can return T here based on passed entityType
    private <T> T convertToEntity(Document doc, Class<T> entityType) {
        String json = doc.toJson();
        return gson.fromJson(json, entityType);
    }

    public <T> List<T> findAll(Class<T> entityType) {
        List<T> results = new ArrayList<>();

        if(entityType.equals(Task.class)) {
            setCategory("task");
            setArrayAccessKey("task");
            setCollection("task");
        } else if(entityType.equals(ShoppingList.class)) {
            setCategory("shoppingList");
            setArrayAccessKey("shoppingList");
            setCollection("shoppingList");
        } else if(entityType.equals(ToDoCalendarActivity.class)) {
            setCategory("calendarEvent");
            setArrayAccessKey("calendarEvent");
            setCollection("calendarEvent");
        } else {
            throw new IllegalArgumentException("Unsupported entity type: " +
                    entityType.getName());
        }
        Document eventsToShow = getCollection().find().sort(Sorts.descending("_id")).first();
        //ObjectId id = eventsToShow.getObjectId("_id");
        FindIterable<Document> docToSearch = collection.find(Filters.eq("_id", eventsToShow.get("_id")));
        for (Document doc : docToSearch) {
            List<Document> task = (List<Document>) doc.get("pages");
            if (task != null) {
                for (Document eventDoc : task) {
                    String name = eventDoc.getString("task");
                    System.out.println("Name: " + name);
                }
            }
        }
        return results;
    }

    /*private void saveCalendarActivitiesToJson(List<ToDoCalendarActivity> calendarActivities) {
        Path path = Paths.get("src/MyDesktopAppMainDirectory/resources/calendarEvent.json");

        try (FileWriter writer = new FileWriter(path.toFile())) {
            gson.toJson(calendarActivities, writer);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to save calendar events to JSON file.");
        }
    }*/

    public <T extends Event>  T findById(Class<T> entityType, int id) {
        List<T> allResults = new ArrayList<>();
        if(entityType.equals(Task.class)) {
            setCategory("task");
            setArrayAccessKey("task");
            setCollection("task");
        } else if(entityType.equals(ShoppingList.class)) {
            setCategory("shoppingList");
            setArrayAccessKey("shoppingList");
            setCollection("shoppingList");
        } else if(entityType.equals(ToDoCalendarActivity.class)) {
            setCategory("calendar event");
            setArrayAccessKey("pages");
            setCollection("calendarEvent");
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
            setArrayAccessKey("task");
            setCollection("task");
        } else if(entityType.equals(ShoppingList.class)) {
            setCategory("shoppingList");
            setArrayAccessKey("shoppingList");
            setCollection("shoppingList");
        } else if(entityType.equals(ToDoCalendarActivity.class)) {
            setCategory("calendar event");
            setArrayAccessKey("pages");
            setCollection("calendarEvent");
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
                    if(docsInside.getInteger("index", getTaskId() - 1) == id) {
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

    }*/

    public void close() {
        mongoClient.close();
    }
}
