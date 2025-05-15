package MyDesktopAppMainDirectory.database;
import MyDesktopAppMainDirectory.model.*;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import org.bson.Document;

public class MongoDBService {
    private final MongoClient mongoClient;
    private final MongoDatabase database;
    private final MongoCollection<Document> collection;

    public MongoDBService(String dbName, String collectionName) {
        this.mongoClient = MongoClients.create("mongodb://localhost:27017");
        this.database = mongoClient.getDatabase(dbName);
        this.collection = database.getCollection(collectionName);
    }

    public void insertShoppingList(ShoppingList shoppinglist) {
        Document newDocument = new Document("category: ", "Shopping List").append("index: ", shoppinglist.getIndex()).append("product: ",
                        shoppinglist.getName()).append("amount: ", shoppinglist.getAmount()).append("priority: ", shoppinglist.getPriority()).
                        append("status: ", shoppinglist.getStatus());
    }

    public void insertCalendarEvent(ToDoCalendarActivity calendar) {
        Document newDocument = new Document("category: ", "Calendar event").append("index", calendar.getIndex()).
                append("Date: ", calendar.getChosenDate()).append("Day: ", calendar.getDayOfTheWeek()).append("Task: ", calendar.getName()).
                append("Importance: ", calendar.getHowImportant()).append("Obligatory? ", calendar.getDutifully());
    }

    public void insertTask(Task task) {
        Document newDocument = new Document("category: ", "Task").append("Time: ", task.getChosenTime()). append("Index: ", task.getIndex()).
                append("Name: ", task.getName()).append("Importance: ", task.getHowImportant()).append("Status: ", task.getStatus());
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
