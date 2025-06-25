package MyDesktopAppMainDirectory.test;


/*import MyDesktopAppMainDirectory.database.MongoDBService;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.Node;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;*/

public class Preview2 /*extends Application*/ {
   /* MongoDBService mongoDBService = new MongoDBService();
    @FXML
    private Button exitButton, exportButton, shoppingListButton, calendarButton, tasksButton;

    @FXML
    private void initialize() {
        exitButton.setOnAction(e -> Platform.exit());

        exportButton.setOnAction(e -> {

        });

        shoppingListButton.setOnAction(e -> {
            try {
                URL fxmlUrl = getClass().getResource("/MyDesktopAppMainDirectory/view/ShoppingListView.fxml");
                if(fxmlUrl == null)
                    throw new IllegalStateException("FXML file not found!");
                Parent root = FXMLLoader.load(fxmlUrl);
                Stage stage = (Stage) calendarButton.getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setTitle("Shopping List");
                String css = Objects.requireNonNull(getClass().getResource("/MyDesktopAppMainDirectory/style/style.css")).toExternalForm();
                scene.getStylesheets().add(css);
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        });

        tasksButton.setOnAction(e -> {
            try {
                URL fxmlUrl = getClass().getResource("/MyDesktopAppMainDirectory/view/TaskView.fxml");
                if(fxmlUrl == null)
                    throw new IllegalStateException("FXML file not found!");
                Parent root = FXMLLoader.load(fxmlUrl);
                Stage stage = (Stage) tasksButton.getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setTitle("Tasks");
                String css = Objects.requireNonNull(getClass().getResource("/MyDesktopAppMainDirectory/style/style.css")).toExternalForm();
                scene.getStylesheets().add(css);
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        calendarButton.setOnAction(e -> {
            try {
                URL fxmlUrl = getClass().getResource("/MyDesktopAppMainDirectory/view/CalendarView.fxml");
                if(fxmlUrl == null)
                    throw new IllegalStateException("FXML file not found!");
                Parent root = FXMLLoader.load(fxmlUrl);
                Stage stage = (Stage) calendarButton.getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setTitle("Calendar");
                String css = Objects.requireNonNull(getClass().getResource("/MyDesktopAppMainDirectory/style/style.css")).toExternalForm();
                scene.getStylesheets().add(css);
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/MyDesktopAppMainDirectory/view/MainView.fxml")));
        stage.setTitle("Desktop App");
        //root.scaleXProperty().bind(stage.widthProperty().divide(800));
        //root.scaleYProperty().bind(stage.widthProperty().divide(640));
        Scene scene = new Scene(root, 1100, 650);
        String css = Objects.requireNonNull(getClass().getResource("/MyDesktopAppMainDirectory/style/style.css")).toExternalForm();
        System.out.println("Root classes: " + root.getStyleClass());

        scene.getStylesheets().add(css);
        stage.setScene(scene);

        stage.show();
    }

    private void switchScene(ActionEvent event, String fxmlPath) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlPath)));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1100, 650);
            stage.setScene(scene);

            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }*/
}