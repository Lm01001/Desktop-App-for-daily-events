package MyDesktopAppMainDirectory;

import MyDesktopAppMainDirectory.database.MongoDBService;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

import static javafx.application.Application.launch;


public class Preview extends Application  {
    @FXML
    Button exitButton, exportButton, shoppingListButton, calendarButton, tasksButton;

    @FXML
    Button insertCalendarEvent, insertTask, insertShoppingList, findAll, finById, deletePosition, editPosition;

    MongoDBService mongoDBService = new MongoDBService();
    /*@Override
    public void start(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource
                ("/MyDesktopAppMainDirectory/view/MainView.fxml")));
        button = new Button();
        button.setText("first button");
        button2 = new Button();
        button2.setText("second button");


        //w momencie nacisniecia operuje co sie dzieje
        //this oznacza ze wszystkie metody sa tu w klasie
        //      button.setOnAction(this);
        //inna opcja, usuwamy to implements eventhandler, inner class
        //implements EventHandler<ActionEvent>
        button.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent actionEvent) {
                 //bez potrzeby tego w () przy if bo przypisane do przycisku
                //Akcja widoczna tylko dla mnie w terminalu
                //moze przydatne przy testowaniu
                System.out.println("Button pressed. akcja ktora ma miejsca po nacisnieciu");
            }
        });



        //lambda expressions, e reprezentuje event
        button.setOnAction(e -> System.out.println("Button pressed."));
        //lub
        button.setOnAction(e -> {
            System.out.println("Button pressed.");
            System.out.println("Button pressed.");
        });

        //Utworzenie obiektu od StackPane ktory wysrodkowuje przycisk
        StackPane layout, layout2;
        layout = new StackPane();
        layout2 = new StackPane();
        layout.getChildren().add(button);
        layout2.getChildren().add(button2);

        button2.setOnAction(e -> {
            System.out.println("Button pressed. scene2");
            System.out.println("Button pressed. again");
        });
        //tytul okna
        stage.setTitle("Desktop App");
        Scene scene1, scene2;
        scene1 = new Scene(layout, 800, 600); // layout changed from root
        scene2 = new Scene(layout2, 800, 600);
        button.setOnAction(e -> stage.setScene(scene2));
        stage.setScene(scene1);
        stage.show();
    }
    @Override
    public void handle(ActionEvent actionEvent) {
        if(actionEvent.getSource()==button1) {
            //Akcja widoczna tylko dla mnie w terminalu
            //moze przydatne przy testowaniu
            System.out.println("Button pressed. akcja ktora ma miejsca po nacisnieciu");
        }
    }*/

    //nie zmienia sie na gorze z powrotem na desktop app
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource
                ("/MyDesktopAppMainDirectory/view/MainView.fxml")));
        Scene scene = new Scene(root, 800, 640);
        stage.setScene(scene);
        stage.setTitle("Desktop App");
        /*Pane layout = new Pane();
        stage.setTitle("creating label");
        Label name = new Label("Daily tasks");
        name.setLayoutX(310);
        name.setLayoutY(60);
        name.setMinWidth(100);
        name.setMinHeight(80);
        name.setStyle("-fx-font: 36 arial");
        //name.setStyle("-fx-font-weight: bold");
        layout.getChildren().add(name);*/

        exitButton = new Button();
        exitButton.setText("Exit");
        exitButton.setOnAction(e -> {
            mongoDBService.close();
            Platform.exit();
        });

        exportButton = new Button();
        exportButton.setText("Export data");
        exportButton.setOnAction(e -> {

        });


        shoppingListButton = new Button();
        shoppingListButton.setText("Shopping List");
        shoppingListButton.setOnAction(e -> {
            try {
                Parent root2 = FXMLLoader.load(Objects.requireNonNull(getClass().getResource
                        ("/MyDesktopAppMainDirectory/view/ShoppingListView.fxml")));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        });


        tasksButton = new Button();
        insertTask = new Button();
        tasksButton.setText("Tasks");
        insertTask.setText("+");
        tasksButton.setOnAction(e -> {
            try {
                Parent root2 = FXMLLoader.load(Objects.requireNonNull(getClass().getResource
                        ("/MyDesktopAppMainDirectory/view/TaskView.fxml")));
                URL fxmlUrl = getClass().getResource("/MyDesktopAppMainDirectory/view/TaskView.fxml");
                if(fxmlUrl == null)
                    throw new IllegalStateException("FXML file not found!");
                Stage stage2 = (Stage) tasksButton.getScene().getWindow();
                stage2.setScene(new Scene(root2,800, 640));
                stage2.setTitle("Task list");
                stage2.show();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });


        calendarButton = new Button();
        insertCalendarEvent = new Button();
        calendarButton.setText("Calendar");
        insertCalendarEvent.setText("+");
        calendarButton.setOnAction(e -> {
            try {
                Parent root2 = FXMLLoader.load(Objects.requireNonNull(getClass().getResource
                        ("/MyDesktopAppMainDirectory/view/CalendarView.fxml")));
                URL fxmlUrl = getClass().getResource("/MyDesktopAppMainDirectory/view/CalendarView.fxml");
                if(fxmlUrl == null)
                    throw new IllegalStateException("FXML file not found!");
                Stage stage2 = (Stage) calendarButton.getScene().getWindow();
                stage2.setScene(new Scene(root2, 800, 640));
                stage2.setTitle("Calendar");
                stage2.show();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        //insertCalendarEvent.setOnAction(e -> mongoDBService.insertCalendarEvent());

        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

