package MyDesktopAppMainDirectory.controller;
import MyDesktopAppMainDirectory.database.MongoDBService;
import MyDesktopAppMainDirectory.model.Task;
import MyDesktopAppMainDirectory.model.ToDoCalendarActivity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.javatuples.Quartet;
import org.javatuples.Triplet;

import javax.management.StringValueExp;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class TaskController implements Initializable {
    Quartet<StackPane, StackPane, StackPane, StackPane> chartRow;
    HashMap<String, Quartet<StackPane, StackPane, StackPane, StackPane>> fullRow = new HashMap<>();
    Task task;
    MongoDBService mongoDBService;
    int amountOfTasks;

    @FXML
    private Button addButton, returnButton;
    @FXML
    private FlowPane taskList;


    @FXML
    private void onAddClicked() {
        System.out.println("Add button clicked!");
        // Logic to handle adding a shopping list item
    }

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
        drawBox();
    }

    //Creating blank table and saving stackPanes inside a hashmap
    private void drawBox() {
        StackPane rectangleStackPane = new StackPane();
        Rectangle rectangleFrame = new Rectangle();
        List<StackPane> helperList = new ArrayList<StackPane>();

        double strokeWidth = 2.0;
        double strokeWidthTaskFrame = 1.0;
        double rectangleWidthDate = 80.0;
        double rectangleWidthIndex = 45.0;
        double rectangleWidthTask = 400.0;
        double rectangleHeight = 45.0;
        rectangleFrame.setWidth(rectangleWidthDate);
        rectangleFrame.setHeight(rectangleHeight);
        rectangleFrame.setFill(Color.LIGHTBLUE);
        rectangleFrame.setStroke(Color.DARKBLUE);
        rectangleFrame.setStrokeWidth(strokeWidth);

        Text textIndex = new Text("Index");
            rectangleFrame.setWidth(rectangleWidthIndex);
            rectangleStackPane = createBoxCell(rectangleFrame.getWidth(), rectangleFrame.getHeight(), rectangleFrame.getStroke(), rectangleFrame.getFill(), textIndex);
            taskList.getChildren().add(rectangleStackPane);
        Text textDate = new Text("Date");
            rectangleFrame.setWidth(rectangleWidthDate);
            rectangleStackPane = createBoxCell(rectangleFrame.getWidth(), rectangleFrame.getHeight(), rectangleFrame.getStroke(), rectangleFrame.getFill(), textDate);
            taskList.getChildren().add(rectangleStackPane);
            helperList.add(rectangleStackPane);
        Text textTask = new Text("Task");
            rectangleFrame.setWidth(rectangleWidthTask);
            rectangleStackPane = createBoxCell(rectangleFrame.getWidth(), rectangleFrame.getHeight(), rectangleFrame.getStroke(), rectangleFrame.getFill(), textTask);
            helperList.add(rectangleStackPane);
            taskList.getChildren().add(rectangleStackPane);
        Text textHowImportant = new Text("Importance");
            rectangleFrame.setWidth(rectangleWidthDate);
            rectangleStackPane = createBoxCell(rectangleFrame.getWidth(), rectangleFrame.getHeight(), rectangleFrame.getStroke(), rectangleFrame.getFill(), textHowImportant);
            helperList.add(rectangleStackPane);
            taskList.getChildren().add(rectangleStackPane);
        Text textStatus = new Text("Status");
            rectangleStackPane = createBoxCell(rectangleFrame.getWidth(), rectangleFrame.getHeight(), rectangleFrame.getStroke(), rectangleFrame.getFill(), textStatus);
            helperList.add(rectangleStackPane);
            taskList.getChildren().add(rectangleStackPane);

        Text blankFulfillment = new Text();
        Text indexAsText;
        rectangleFrame.setStroke(Color.BLACK);
        rectangleFrame.setFill(Color.TRANSPARENT);
        Rectangle rectangleDate = new Rectangle();
        rectangleDate.setStrokeWidth(strokeWidthTaskFrame);
        rectangleDate.setHeight(rectangleHeight);
        rectangleDate.setWidth(rectangleWidthDate);

        Rectangle rectangleIndex = new Rectangle();
        rectangleIndex.setStrokeWidth(strokeWidthTaskFrame);
        rectangleIndex.setHeight(rectangleHeight);
        rectangleIndex.setWidth(rectangleWidthIndex);

        Rectangle rectangleTask = new Rectangle();
        rectangleTask.setStrokeWidth(strokeWidthTaskFrame);
        rectangleTask.setHeight(rectangleHeight);
        rectangleTask.setWidth(rectangleWidthTask);

        Rectangle rectangleIfCompleted = new Rectangle();
        rectangleIfCompleted.setStrokeWidth(strokeWidth);
        rectangleIfCompleted.setHeight(rectangleHeight);
        rectangleIfCompleted.setWidth(rectangleWidthDate);

        chartRow = Quartet.fromCollection(helperList);
        this.fullRow.put("Index", chartRow);
        helperList.clear();

        for (int i = 0; i < 10; i++) {
                indexAsText = new Text(String.valueOf(i + 1));
                rectangleStackPane = createBoxCell(rectangleIndex.getWidth(), rectangleIndex.getHeight(), rectangleFrame.getStroke(), rectangleFrame.getFill(), indexAsText);
                taskList.getChildren().add(rectangleStackPane);
                //Date
                rectangleStackPane = createBoxCell(rectangleDate.getWidth(), rectangleDate.getHeight(), rectangleFrame.getStroke(), rectangleFrame.getFill(), blankFulfillment);
                helperList.add(rectangleStackPane);
                taskList.getChildren().add(rectangleStackPane);
                //Task's name
                rectangleStackPane = createBoxCell(rectangleTask.getWidth(), rectangleTask.getHeight(), rectangleFrame.getStroke(), rectangleFrame.getFill(), blankFulfillment);
                helperList.add(rectangleStackPane);
                taskList.getChildren().add(rectangleStackPane);
                //How important, priority
                rectangleStackPane = createBoxCell(rectangleDate.getWidth(), rectangleDate.getHeight(), rectangleFrame.getStroke(), rectangleFrame.getFill(), blankFulfillment);
                helperList.add(rectangleStackPane);
                taskList.getChildren().add(rectangleStackPane);
                //Status
                rectangleStackPane = createBoxCell(rectangleDate.getWidth(), rectangleDate.getHeight(), rectangleFrame.getStroke(), rectangleFrame.getFill(), blankFulfillment);
                helperList.add(rectangleStackPane);
                taskList.getChildren().add(rectangleStackPane);
                chartRow =  Quartet.fromCollection(helperList);
                fullRow.put(String.valueOf(i + 1), chartRow);
                helperList.clear();
        }
    }

    private StackPane createBoxCell(double width, double height, Paint color, Paint background,Text text) {
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
    private void insertTask(ActionEvent event) throws IOException {
        List<Task> taskList1 = new ArrayList<>();
        this.task = new Task();
        Task tasks = task.createTask();
        taskList1.add(tasks);
        tasks.quartetCreator(tasks.getNowAsAString(), tasks.getName(), tasks.getHowImportant(), tasks.getStatus());
        tasks.addHashMapValue();


        System.out.println(tasks.getTasks());

    }
}