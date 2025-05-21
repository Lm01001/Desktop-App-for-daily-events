package MyDesktopAppMainDirectory.controller;
import MyDesktopAppMainDirectory.model.CalendarActivity;
import MyDesktopAppMainDirectory.model.ToDoCalendarActivity;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CalendarController implements Initializable {
    ZonedDateTime dateFocus;
    ZonedDateTime today;
    Map<Integer, StackPane> dayPaneMap = new HashMap<>();
    String chosenDate;

    @FXML
    private Text year, month, day;
    @FXML
    private FlowPane calendar;
    @FXML
    private Button returnButton;
    @FXML
    private Label datesLabel, warningLabel;
    @FXML
    private Button insertCalendarEvent;
    @FXML
    private DatePicker datePick;

    @FXML
    private String insertingOnPlusClicked(ActionEvent event) {
        LocalDate localDate = datePick.getValue();
        datePick.setOnAction(e -> {
                System.out.println(datePick.getValue());
        });
        String formattedUsersDate = localDate.format(DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH));
        datesLabel.setText("Chosen date: " + formattedUsersDate);
        formattedUsersDate = localDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        this.chosenDate = formattedUsersDate;
        if(insertCalendarEvent.isDisable()) {
            insertCalendarEvent.setDisable(false);
        }
        if(warningLabel.isVisible()) {
            warningLabel.setVisible(false);
        }
        return this.chosenDate;
    }

    @FXML
    private void returnButton(ActionEvent event) throws IOException {
        returnButton.setOnMouseClicked(e -> {
            try {
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource
                        ("/MyDesktopAppMainDirectory/view/MainView.fxml")));
                Stage stage = (Stage) returnButton.getScene().getWindow();
                stage.setScene(new Scene(root));
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
        double calendarWidth = calendar.getPrefWidth();
        double calendarHeight = calendar.getPrefHeight();
        double strokeWidth = 1;
        double spacingH = calendar.getHgap();
        double spacingV = calendar.getVgap();

        //List of activities for a given month
        Map<Integer, List<ToDoCalendarActivity>> calendarActivityMap = getCalendarActivitiesMonth(dateFocus);

        int monthMaxDate = dateFocus.getMonth().maxLength();
        //Check for leap year
        if(dateFocus.getYear() % 4 != 0 && monthMaxDate == 29){
            monthMaxDate = 28;
        }
        int dateOffset = ZonedDateTime.of(dateFocus.getYear(), dateFocus.getMonthValue(), 1,0,0,0,0,dateFocus.getZone()).getDayOfWeek().getValue();

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                StackPane stackPane = new StackPane();

                Rectangle rectangle = new Rectangle();
                rectangle.setFill(Color.TRANSPARENT);
                rectangle.setStroke(Color.BLACK);
                rectangle.setStrokeWidth(strokeWidth);
                double rectangleWidth =(calendarWidth/7) - strokeWidth - spacingH;
                rectangle.setWidth(rectangleWidth);
                double rectangleHeight = (calendarHeight/6) - strokeWidth - spacingV;
                rectangle.setHeight(rectangleHeight);
                stackPane.getChildren().add(rectangle);

                int calculatedDate = (j+2)+(7*i);
                if(calculatedDate > dateOffset){
                    int currentDate = calculatedDate - dateOffset;
                    if(currentDate <= monthMaxDate){
                        Text date = new Text(String.valueOf(currentDate));
                        double textTranslationY = - (rectangleHeight / 2) * 0.75;
                        date.setTranslateY(textTranslationY);
                        stackPane.getChildren().add(date);
                        dayPaneMap.put(currentDate, stackPane);

                        List<ToDoCalendarActivity> calendarActivities = calendarActivityMap.get(currentDate);
                        if(calendarActivities != null){
                            createCalendarActivity(calendarActivities, rectangleHeight, rectangleWidth, stackPane);
                        }
                    }
                    if(today.getYear() == dateFocus.getYear() && today.getMonth() == dateFocus.getMonth() && today.getDayOfMonth() == currentDate){
                        rectangle.setStroke(Color.BLUE);
                    }
                }
                calendar.getChildren().add(stackPane);
            }
        }
    }

    private void createCalendarActivity(List<ToDoCalendarActivity> calendarActivities, double rectangleHeight, double rectangleWidth, StackPane stackPane) {
        VBox calendarActivityBox = new VBox();

        for (int k = 0; k < calendarActivities.size(); k++) {
            if (k >= 2) {
                Text moreActivities = new Text("...");
                calendarActivityBox.getChildren().add(moreActivities);
                moreActivities.setOnMouseClicked(mouseEvent -> {
                    System.out.println(calendarActivities);
                });
                break;
            }
            ToDoCalendarActivity activity = calendarActivities.get(k);
            Text text = new Text(activity.getName() + " (" + activity.getHowImportant() + ", " + activity.getDutifully() + ")");
            text.setWrappingWidth(rectangleWidth * 0.75);
            if(activity.getPriority() == 1) {
                calendarActivityBox.setStyle("-fx-background-color:LIGHTBLUE");
            } else if (activity.getPriority() == 2) {
                calendarActivityBox.setStyle("-fx-background-color:LIGHTGREEN");
            } else {
                calendarActivityBox.setStyle("-fx-background-color:RED");
            }
            text.setOnMouseClicked(mouseEvent -> {
                System.out.println(text.getText());
            });
            calendarActivityBox.getChildren().add(text);
        }
        //calendarActivityBox.setTranslateY((rectangleHeight / 2) * 0.20);
        calendarActivityBox.setMaxWidth(rectangleWidth * 0.8);
        calendarActivityBox.setMaxHeight(rectangleHeight * 0.65);
        stackPane.getChildren().addFirst(calendarActivityBox);
    }

    @FXML
    private void insertCalendarEvent(ActionEvent event) throws IOException {
        if(this.chosenDate == null) {
            warningLabel.setVisible(true);
            insertCalendarEvent.setDisable(true);
            return;
        }

        String dateString = this.chosenDate;
        ToDoCalendarActivity toDoCalendarActivity = new ToDoCalendarActivity();
        ToDoCalendarActivity eventsDetails = toDoCalendarActivity.createAction();

        eventsDetails.setChosenDate(dateString);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        List<ToDoCalendarActivity> calendarActivities = new ArrayList<>();
        calendarActivities.add(eventsDetails);
        LocalDate dateTime = LocalDate.parse(dateString, formatter);
        int day = dateTime.getDayOfMonth();

        StackPane dayStackPane = dayPaneMap.get(day);
        double rectangleWidth = calendar.getWidth() / 7;
        double rectangleHeight = calendar.getHeight() / 6;
        createCalendarActivity(calendarActivities, rectangleHeight, rectangleWidth, dayStackPane);
        this.chosenDate = null;
        datePick.getEditor().clear();
        datePick.setPromptText(datePick.getPromptText());
    }

    private Map<Integer, List<ToDoCalendarActivity>> createCalendarMap(List<ToDoCalendarActivity> calendarActivities) {
        Map<Integer, List<ToDoCalendarActivity>> calendarActivityMap = new HashMap<>();

        for(ToDoCalendarActivity activity : calendarActivities) {
            ZonedDateTime activityDate = activity.getDate();

            if(activityDate.getYear() == dateFocus.getYear() && activityDate.getMonth() == dateFocus.getMonth()) {
                //calendarActivityMap.put(activityDate, List.of(activity));
                //List<ToDoCalendarActivity> OldListByDate = calendarActivityMap.get(activityDate);
                int activityDay = activityDate.getDayOfMonth();
                List<ToDoCalendarActivity> newList = calendarActivityMap.getOrDefault(activityDay, new ArrayList<>());
                newList.add(activity);
                calendarActivityMap.put(activityDay, newList);
            }
        }
        return  calendarActivityMap;
    }

   private Map<Integer, List<ToDoCalendarActivity>> getCalendarActivitiesMonth(ZonedDateTime dateFocus) {
        List<ToDoCalendarActivity> calendarActivities = new ArrayList<>();
        //MongoDBService mongoDBService = new MongoDBService("MyDatabase", "MyCollection");
        int year = dateFocus.getYear();
        int month = dateFocus.getMonth().getValue();
        //mongoDBService.insertCalendarEvent(calendarActivities);
        //calendarActivities.add(new ToDoCalendarActivity();
        return createCalendarMap(calendarActivities);
    }
}