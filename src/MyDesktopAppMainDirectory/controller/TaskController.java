package MyDesktopAppMainDirectory.controller;
import MyDesktopAppMainDirectory.database.MongoDBService;
import MyDesktopAppMainDirectory.model.Task;
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

public class TaskController implements Initializable {
    Quartet<StackPane, StackPane, StackPane, StackPane> chartRow;
    HashMap<String, Quartet<StackPane, StackPane, StackPane, StackPane>> fullRow = new HashMap<>();
    Task task;
    MongoDBService mongoDBService;
    int amountOfTasks, rowHelper, rowHelperDeleting;
    Rectangle rectangleIndex, rectangleDate, rectangleTask, rectangleFrame;

    @FXML
    private Button returnButton;
    @FXML
    private FlowPane taskList;
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
        this.rectangleDate = new Rectangle();
        this.rectangleIndex = new Rectangle();
        this.rectangleTask = new Rectangle();
        drawBox();
    }

    //Creating blank table and saving stackPanes inside a hashmap
    private void drawBox() {
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
            this.rectangleFrame.setWidth(rectangleWidthIndex);
            rectangleStackPane = createBoxCell(rectangleFrame.getWidth(), rectangleFrame.getHeight(), rectangleFrame.getStroke(), rectangleFrame.getFill(), textIndex);
            taskList.getChildren().add(rectangleStackPane);
        Text textDate = new Text("Date");
            this.rectangleFrame.setWidth(rectangleWidthDate);
            rectangleStackPane = createBoxCell(rectangleFrame.getWidth(), rectangleFrame.getHeight(), rectangleFrame.getStroke(), rectangleFrame.getFill(), textDate);
            taskList.getChildren().add(rectangleStackPane);
            helperList.add(rectangleStackPane);
        Text textTask = new Text("Task");
            this.rectangleFrame.setWidth(rectangleWidthTask);
            rectangleStackPane = createBoxCell(rectangleFrame.getWidth(), rectangleFrame.getHeight(), rectangleFrame.getStroke(), rectangleFrame.getFill(), textTask);
            helperList.add(rectangleStackPane);
            taskList.getChildren().add(rectangleStackPane);
        Text textHowImportant = new Text("Importance");
            this.rectangleFrame.setWidth(rectangleWidthDate);
            rectangleStackPane = createBoxCell(rectangleFrame.getWidth(), rectangleFrame.getHeight(), rectangleFrame.getStroke(), rectangleFrame.getFill(), textHowImportant);
            helperList.add(rectangleStackPane);
            taskList.getChildren().add(rectangleStackPane);
        Text textStatus = new Text("Status");
            rectangleStackPane = createBoxCell(rectangleFrame.getWidth(), rectangleFrame.getHeight(), rectangleFrame.getStroke(), rectangleFrame.getFill(), textStatus);
            helperList.add(rectangleStackPane);
            taskList.getChildren().add(rectangleStackPane);

        Text blankFulfillment = new Text();
        Text indexAsText;
        this.rectangleFrame.setStroke(Color.BLACK);
        this.rectangleFrame.setFill(Color.TRANSPARENT);

        this.rectangleDate.setStrokeWidth(strokeWidthTaskFrame);
        this.rectangleDate.setHeight(rectangleHeight);
        this.rectangleDate.setWidth(rectangleWidthDate);

        this.rectangleIndex.setStrokeWidth(strokeWidthTaskFrame);
        this.rectangleIndex.setHeight(rectangleHeight);
        this.rectangleIndex.setWidth(rectangleWidthIndex);

        this.rectangleTask.setStrokeWidth(strokeWidthTaskFrame);
        this.rectangleTask.setHeight(rectangleHeight);
        this.rectangleTask.setWidth(rectangleWidthTask);

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
        this.task = new Task();
        Task tasks = task.createTask();
        this.amountOfTasks++;
        //Adding provided task details as values in hashMap
        tasks.quartetCreator(tasks.getNowAsAString(), tasks.getName(), tasks.getHowImportant(), tasks.getStatus());
        HashMap<String, Task.Quartet> hashMapForTask;
        //Creating hashMap using created quartet
        hashMapForTask = tasks.addHashMapValue();
        Task.Quartet quartetFromHashMap;
        quartetFromHashMap = hashMapForTask.get("0");
        Text textInsideCell = new Text(quartetFromHashMap.date());
        //Adding date
        taskList.getChildren().remove(taskList.getChildren().size() - (rowHelper));
        rectangleStackPane = createBoxCell(rectangleDate.getWidth(), rectangleDate.getHeight(), rectangleFrame.getStroke(),
                rectangleFrame.getFill(),textInsideCell);
        taskList.getChildren().add(taskList.getChildren().size() - (rowHelper - 1),rectangleStackPane);
        //Adding task
        textInsideCell = new Text(quartetFromHashMap.name());
        taskList.getChildren().remove(taskList.getChildren().size() - (rowHelper - 1));
        rectangleStackPane = createBoxCell(rectangleTask.getWidth(), rectangleTask.getHeight(), rectangleFrame.getStroke(),
                rectangleFrame.getFill(),textInsideCell);
        taskList.getChildren().add(taskList.getChildren().size() - (rowHelper - 2),rectangleStackPane);
        //Adding importance
        textInsideCell = new Text(quartetFromHashMap.howImportant());
        taskList.getChildren().remove(taskList.getChildren().size() - (rowHelper - 2));
        rectangleStackPane = createBoxCell(rectangleDate.getWidth(), rectangleDate.getHeight(), rectangleFrame.getStroke(),
                rectangleFrame.getFill(),textInsideCell);
        taskList.getChildren().add(taskList.getChildren().size() - (rowHelper - 3),rectangleStackPane);
        //Adding status
        textInsideCell = new Text(quartetFromHashMap.status());
        taskList.getChildren().remove(taskList.getChildren().size() - (rowHelper - 3));
        rectangleStackPane = createBoxCell(rectangleDate.getWidth(), rectangleDate.getHeight(), rectangleFrame.getStroke(),
                rectangleFrame.getFill(),textInsideCell);
        taskList.getChildren().add(taskList.getChildren().size() - (rowHelper - 4),rectangleStackPane);


        this.rowHelperDeleting = rowHelperDeleting - 5;
        this.rowHelper = rowHelper - 5;


        //for checking
        System.out.println(rowHelper);
        System.out.println(tasks.getTasks());
        System.out.println(quartetFromHashMap);
    }

    @FXML
    private void deleteTask(ActionEvent event) throws IOException {
        Text blankCell = new Text("");
        //Adding date
        taskList.getChildren().remove(taskList.getChildren().size() - (rowHelperDeleting));
        rectangleStackPane = createBoxCell(rectangleDate.getWidth(), rectangleDate.getHeight(), rectangleFrame.getStroke(),
                rectangleFrame.getFill(),blankCell);
        taskList.getChildren().add(taskList.getChildren().size() - (rowHelperDeleting - 1),rectangleStackPane);
        //Adding task
        taskList.getChildren().remove(taskList.getChildren().size() - (rowHelperDeleting - 1));
        rectangleStackPane = createBoxCell(rectangleTask.getWidth(), rectangleTask.getHeight(), rectangleFrame.getStroke(),
                rectangleFrame.getFill(), blankCell);
        taskList.getChildren().add(taskList.getChildren().size() - (rowHelperDeleting - 2),rectangleStackPane);
        //Adding importance
        taskList.getChildren().remove(taskList.getChildren().size() - (rowHelperDeleting - 2));
        rectangleStackPane = createBoxCell(rectangleDate.getWidth(), rectangleDate.getHeight(), rectangleFrame.getStroke(),
                rectangleFrame.getFill(), blankCell);
        taskList.getChildren().add(taskList.getChildren().size() - (rowHelperDeleting - 3),rectangleStackPane);
        //Adding status
        taskList.getChildren().remove(taskList.getChildren().size() - (rowHelperDeleting - 3));
        rectangleStackPane = createBoxCell(rectangleDate.getWidth(), rectangleDate.getHeight(), rectangleFrame.getStroke(),
                rectangleFrame.getFill(), blankCell);
        taskList.getChildren().add(taskList.getChildren().size() - (rowHelperDeleting - 4),rectangleStackPane);

        this.amountOfTasks--;

        if(amountOfTasks > 0) {
            this.rowHelperDeleting = rowHelperDeleting + 5;
            this.rowHelper = rowHelper + 5;
        } else {
            System.out.println("All tasks deleted!");
            this.rowHelperDeleting = 49;
            this.rowHelper = 49;
        }
    }
}