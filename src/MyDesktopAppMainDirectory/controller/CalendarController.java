package MyDesktopAppMainDirectory.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import javax.print.DocFlavor;
import java.time.ZonedDateTime;
import java.util.ResourceBundle;

public class CalendarController {
    ZonedDateTime dateFocus;
    ZonedDateTime today;

    @FXML
    private Text year;
    @FXML
    private Text month;
    @FXML
    private Text day;
    @FXML
    private FlowPane calendar;


    @FXML
    private void onAddClicked() {
        System.out.println("Add button clicked! Running code user doesn't see");
        // Logic to handle adding a shopping list item
    }

    @FXML
    public void initialize(DocFlavor.URL url, ResourceBundle rb) {
        dateFocus = ZonedDateTime.now();
        today = ZonedDateTime.now();
        drawCalendar();
    }

    @FXML
    void backOneMonth(ActionEvent event) {
        dateFocus = dateFocus.minusMonths(1);
        calendar.getChildren().clear();
        drawCalendar();
    }

    @FXML
    void forwardOneMonth(ActionEvent event) {
        dateFocus = dateFocus.plusMonths(1);
        calendar.getChildren().clear();
        drawCalendar();
    }

    private void drawCalendar() {
        year.setText(String.valueOf(dateFocus.getYear()));
        month.setText(String.valueOf(dateFocus.getMonth()));


    }
}
