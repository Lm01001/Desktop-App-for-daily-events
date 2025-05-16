package MyDesktopAppMainDirectory;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.Objects;


public class Preview extends Application  {
    Button button, button2;
    @Override
    public void start(Stage stage) throws Exception{
        /*Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource
                ("/MyDesktopAppMainDirectory/view/MainView.fxml")));
*/

        button = new Button();
        button.setText("first button");
        button2 = new Button();
        button2.setText("second button");


        //w momencie nacisniecia operuje co sie dzieje
        //this oznacza ze wszystkie metody sa tu w klasie
        //      button.setOnAction(this);
        //inna opcja, usuwamy to implements eventhandler, inner class
        //implements EventHandler<ActionEvent>
        /*button.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent actionEvent) {
                 //bez potrzeby tego w () przy if bo przypisane do przycisku
                //Akcja widoczna tylko dla mnie w terminalu
                //moze przydatne przy testowaniu
                System.out.println("Button pressed. akcja ktora ma miejsca po nacisnieciu");
            }
        });
         */


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

    /*@Override
    public void handle(ActionEvent actionEvent) {
        if(actionEvent.getSource()==button1) {
            //Akcja widoczna tylko dla mnie w terminalu
            //moze przydatne przy testowaniu
            System.out.println("Button pressed. akcja ktora ma miejsca po nacisnieciu");
        }
    }*/

    public static void main(String[] args) {
        launch(args);
    }
}
