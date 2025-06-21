package MyDesktopAppMainDirectory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

public class Main extends Application {


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

    public static void main(String[] args) {
        launch(args);
    }
}