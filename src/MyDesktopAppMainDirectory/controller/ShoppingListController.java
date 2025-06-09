package MyDesktopAppMainDirectory.controller;
import MyDesktopAppMainDirectory.database.MongoDBService;
import MyDesktopAppMainDirectory.model.ShoppingList;
import MyDesktopAppMainDirectory.model.ToDoCalendarActivity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.javatuples.Quartet;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ShoppingListController implements Initializable {
    Quartet<StackPane, StackPane, StackPane, StackPane> chartRow;
    HashMap<String, Quartet<StackPane, StackPane, StackPane, StackPane>> fullRow = new HashMap<>();
    List<ShoppingList> shoppingListsToDB;
    ShoppingList shoppingList;
    MongoDBService mongoDBService;
    int amountOfTasks, rowHelper, rowHelperDeleting;
    Rectangle rectangleIndex, rectangleTask, rectangleFrame, rectangleAmount;
    boolean checkIfCreated = false;

    @FXML
    private Button returnButton, showProducts;
    @FXML
    private FlowPane shoppingListPane;
    private StackPane rectangleStackPane;


    @FXML
    private void returnButton(ActionEvent event) throws IOException {
        returnButton.setOnMouseClicked(e -> {
            try {
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource
                        ("/MyDesktopAppMainDirectory/view/MainView.fxml")));
                Stage stage = (Stage) returnButton.getScene().getWindow();
                stage.setScene(new Scene(root, 800, 640));
                stage.setTitle("Desktop App");
                stage.show();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        //System.out.println("Button clicked");
    }

    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        //Only for making calendar (while choosing date) in english
        Locale.setDefault(Locale.ENGLISH);
        this.mongoDBService = new MongoDBService();
        this.amountOfTasks = 0;
        this.rowHelper = 49;
        this.rowHelperDeleting = 49;
        this.rectangleFrame = new Rectangle();
        this.rectangleStackPane = new StackPane();
        this.rectangleAmount = new Rectangle();
        this.rectangleIndex = new Rectangle();
        this.rectangleTask = new Rectangle();

        shoppingListsToDB = new ArrayList<>();
        drawBox();
    }

    //priority jako color
    //(index) name amount status
    //Creating blank table and saving stackPanes inside a hashmap
    private void drawBox() {
        List<StackPane> helperList = new ArrayList<StackPane>();

        double strokeWidth = 2.0;
        double strokeWidthTaskFrame = 1.0;
        double rectangleWidthIndex = 45.0;
        double rectangleWidthTask = 450.0;
        double rectangleWidthAmount = 100.0;
        double rectangleHeight = 45.0;
        rectangleFrame.setWidth(rectangleWidthAmount);
        rectangleFrame.setHeight(rectangleHeight);
        rectangleFrame.setFill(Color.LIGHTBLUE);
        rectangleFrame.setStroke(Color.DARKBLUE);
        rectangleFrame.setStrokeWidth(strokeWidth);

        Text textIndex = new Text("Index");
        this.rectangleFrame.setWidth(rectangleWidthIndex);
        rectangleStackPane = createBoxCell(rectangleFrame.getWidth(), rectangleFrame.getHeight(), rectangleFrame.getStroke(), rectangleFrame.getFill(), textIndex);
        helperList.add(rectangleStackPane);
        shoppingListPane.getChildren().add(rectangleStackPane);
        Text textTask = new Text("Product");
        this.rectangleFrame.setWidth(rectangleWidthTask);
        rectangleStackPane = createBoxCell(rectangleFrame.getWidth(), rectangleFrame.getHeight(), rectangleFrame.getStroke(), rectangleFrame.getFill(), textTask);
        helperList.add(rectangleStackPane);
        shoppingListPane.getChildren().add(rectangleStackPane);
        Text textHowImportant = new Text("Amount");
        this.rectangleFrame.setWidth(100.0);
        rectangleStackPane = createBoxCell(rectangleFrame.getWidth(), rectangleFrame.getHeight(), rectangleFrame.getStroke(), rectangleFrame.getFill(), textHowImportant);
        helperList.add(rectangleStackPane);
        shoppingListPane.getChildren().add(rectangleStackPane);
        Text textStatus = new Text("Status");
        rectangleStackPane = createBoxCell(rectangleFrame.getWidth(), rectangleFrame.getHeight(), rectangleFrame.getStroke(), rectangleFrame.getFill(), textStatus);
        helperList.add(rectangleStackPane);
        shoppingListPane.getChildren().add(rectangleStackPane);

        Text blankFulfillment = new Text();
        Text indexAsText;
        this.rectangleFrame.setStroke(Color.BLACK);
        this.rectangleFrame.setFill(Color.TRANSPARENT);

        this.rectangleIndex.setStrokeWidth(strokeWidthTaskFrame);
        this.rectangleIndex.setHeight(rectangleHeight);
        this.rectangleIndex.setWidth(rectangleWidthIndex);

        this.rectangleTask.setStrokeWidth(strokeWidthTaskFrame);
        this.rectangleTask.setHeight(rectangleHeight);
        this.rectangleTask.setWidth(rectangleWidthTask);

        this.rectangleAmount.setStrokeWidth(strokeWidthTaskFrame);
        this.rectangleAmount.setHeight(rectangleHeight);
        this.rectangleAmount.setWidth(rectangleWidthAmount);

        chartRow = Quartet.fromCollection(helperList);
        helperList.clear();

        for (int i = 0; i < 10; i++) {
            indexAsText = new Text(String.valueOf(i + 1));
            rectangleStackPane = createBoxCell(rectangleIndex.getWidth(), rectangleIndex.getHeight(), rectangleFrame.getStroke(), rectangleFrame.getFill(), indexAsText);
            helperList.add(rectangleStackPane);
            shoppingListPane.getChildren().add(rectangleStackPane);
            //Task's name
            rectangleStackPane = createBoxCell(rectangleTask.getWidth(), rectangleTask.getHeight(), rectangleFrame.getStroke(), rectangleFrame.getFill(), blankFulfillment);
            helperList.add(rectangleStackPane);
            shoppingListPane.getChildren().add(rectangleStackPane);
            //Amount
            rectangleStackPane = createBoxCell(rectangleAmount.getWidth(), rectangleAmount.getHeight(), rectangleFrame.getStroke(), rectangleFrame.getFill(), blankFulfillment);
            helperList.add(rectangleStackPane);
            shoppingListPane.getChildren().add(rectangleStackPane);
            //Status
            rectangleStackPane = createBoxCell(rectangleAmount.getWidth(), rectangleAmount.getHeight(), rectangleFrame.getStroke(), rectangleFrame.getFill(), blankFulfillment);
            helperList.add(rectangleStackPane);
            shoppingListPane.getChildren().add(rectangleStackPane);
            chartRow =  Quartet.fromCollection(helperList);
            fullRow.put(String.valueOf(i + 1), chartRow);
            helperList.clear();
        }
    }

    private StackPane createBoxCell(double width, double height, Paint color, Paint background, Text text) {
        Rectangle rect = new Rectangle(width, height);
        rect.setStroke(color);
        rect.setFill(background);
        String name = text.getText();
        text = new Text(name);
        text.setStyle("-fx-font-weight: bold; -fx-font-size: 12;");
        StackPane stack = new StackPane(rect, text);
        return stack;
    }

    @FXML
    private void insertProduct(ActionEvent event) throws IOException {
        this.shoppingList = new ShoppingList();
        ShoppingList finalProduct = shoppingList.addProduct();
        shoppingListsToDB.add(finalProduct);
        this.amountOfTasks++;
        //Adding provided task details as values in hashMap
        finalProduct.quartetCreator(finalProduct.getHowImportant(), finalProduct.getName(), finalProduct.getAmountString(), finalProduct.getBought());
        HashMap<Integer, ShoppingList.Quartet> hashMapForAProduct;
        //Creating hashMap using created quartet
        hashMapForAProduct = shoppingList.addHashMapValue();
        ShoppingList.Quartet quartetFromHashMap;
        quartetFromHashMap = hashMapForAProduct.get(0);
        //Adding a product
        Text textInsideCell = new Text(quartetFromHashMap.name());
        shoppingListPane.getChildren().remove(shoppingListPane.getChildren().size() - (rowHelper - 10));
        rectangleStackPane = createBoxCell(rectangleTask.getWidth(), rectangleTask.getHeight(), rectangleFrame.getStroke(),
                rectangleFrame.getFill(),textInsideCell);
        shoppingListPane.getChildren().add(shoppingListPane.getChildren().size() - (rowHelper - 11),rectangleStackPane);
        //Adding importance
        textInsideCell = new Text(quartetFromHashMap.amount());
        shoppingListPane.getChildren().remove(shoppingListPane.getChildren().size() - (rowHelper - 11));
        rectangleStackPane = createBoxCell(rectangleAmount.getWidth(), rectangleAmount.getHeight(), rectangleFrame.getStroke(),
                rectangleFrame.getFill(),textInsideCell);
        shoppingListPane.getChildren().add(shoppingListPane.getChildren().size() - (rowHelper - 12),rectangleStackPane);
        //Adding status
        textInsideCell = new Text(quartetFromHashMap.bought());
        shoppingListPane.getChildren().remove(shoppingListPane.getChildren().size() - (rowHelper - 12));
        rectangleStackPane = createBoxCell(rectangleAmount.getWidth(), rectangleAmount.getHeight(), rectangleFrame.getStroke(),
                rectangleFrame.getFill(),textInsideCell);
        shoppingListPane.getChildren().add(shoppingListPane.getChildren().size() - (rowHelper - 13),rectangleStackPane);
        this.mongoDBService.insertShoppingList(shoppingListsToDB);

        this.rowHelperDeleting = rowHelperDeleting - 4;
        this.rowHelper = rowHelper - 4;
        if(!checkIfCreated) {
            this.checkIfCreated = true;
        }

        //for checking
        System.out.println(rowHelper);
        //System.out.println(tasks.getTasks());
        System.out.println(quartetFromHashMap);
    }

    @FXML
    private void deleteProduct(ActionEvent event) throws IOException {
        Text blankCell = new Text("");
        //Adding a product
        shoppingListPane.getChildren().remove(shoppingListPane.getChildren().size() - (rowHelper - 10));
        rectangleStackPane = createBoxCell(rectangleTask.getWidth(), rectangleTask.getHeight(), rectangleFrame.getStroke(),
                rectangleFrame.getFill(), blankCell);
        shoppingListPane.getChildren().add(shoppingListPane.getChildren().size() - (rowHelper - 11),rectangleStackPane);
        //Adding importance
        shoppingListPane.getChildren().remove(shoppingListPane.getChildren().size() - (rowHelper - 11));
        rectangleStackPane = createBoxCell(rectangleAmount.getWidth(), rectangleAmount.getHeight(), rectangleFrame.getStroke(),
                rectangleFrame.getFill(),blankCell);
        shoppingListPane.getChildren().add(shoppingListPane.getChildren().size() - (rowHelper - 12),rectangleStackPane);
        //Adding status
        shoppingListPane.getChildren().remove(shoppingListPane.getChildren().size() - (rowHelper - 12));
        rectangleStackPane = createBoxCell(rectangleAmount.getWidth(), rectangleAmount.getHeight(), rectangleFrame.getStroke(),
                rectangleFrame.getFill(),blankCell);
        shoppingListPane.getChildren().add(shoppingListPane.getChildren().size() - (rowHelper - 13),rectangleStackPane);

        this.amountOfTasks--;

        if(amountOfTasks > 0) {
            this.rowHelperDeleting = rowHelperDeleting + 4;
            this.rowHelper = rowHelper + 4;
        } else {
            //System.out.println("All tasks deleted!");
            this.rowHelperDeleting = 49;
            this.rowHelper = 49;
        }
    }

    @FXML  //najwyzej sprawdzic czy overloading ok
    private List<ShoppingList> showAllAddedProducts(ActionEvent event) {
        List<ShoppingList> products = mongoDBService.findAllActive(ShoppingList.class);
        if(products.isEmpty() && !checkIfCreated) {
            System.out.println("No added activities.");
            return null;
        }
        return products;
    }
}
