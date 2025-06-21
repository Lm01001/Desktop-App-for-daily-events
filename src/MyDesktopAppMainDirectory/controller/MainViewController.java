package MyDesktopAppMainDirectory.controller;
import MyDesktopAppMainDirectory.database.MongoDBService;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class MainViewController {
    MongoDBService mongoDBService = new MongoDBService();
    @FXML
    private Button exitButton, exportButton, shoppingListButton, calendarButton, tasksButton;

    @FXML
    private void initialize() {
        exitButton.setOnAction(e -> Platform.exit());

        /*exportButton.setOnAction(e -> {

        });*/

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
}
