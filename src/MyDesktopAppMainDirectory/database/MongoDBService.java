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
        Document newDocument = new Document("index: ", shoppinglist.getIndex()).append("product: ",
                        shoppinglist.getName()).append("amount: ", shoppinglist.getAmount()).append("priority: ", shoppinglist.getPriority()).
                        append("status: ", shoppinglist.getStatus());
    }

    public void insertCalendarEvent(Calendar calendar) {

    }

    public void insertTask(Task task) {

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
