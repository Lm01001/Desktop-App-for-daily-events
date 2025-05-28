package MyDesktopAppMainDirectory.controller;

import MyDesktopAppMainDirectory.database.MongoDBService;
import MyDesktopAppMainDirectory.model.ToDoCalendarActivity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class CalendarController implements Initializable {
    ZonedDateTime dateFocus;
    ZonedDateTime today;
    Map<Integer, StackPane> dayPaneMap = new HashMap<>();
    String chosenDate;
    List<ToDoCalendarActivity> calendarActivities;
    LocalDate localDate;
    MongoDBService mongoDBService;
    ToDoCalendarActivity toDoCalendarActivity;
    boolean checkIfCreated = false;
    Popup popup;

    @FXML
    private Text year, month, day;
    @FXML
    private FlowPane calendar;
    @FXML
    private Button returnButton, insertCalendarEvent, showTasks;
    @FXML
    private Label datesLabel, warningLabel, popupForCalendar;
    @FXML
    private DatePicker datePick;


    //przy rejestracji do mngodb atlas przy najezdzaniu na przycisk krawedzie sie zaokraglaja w polowe okrege zamiast prostokata
    //ustawic zeby do wyjscia zatrzymywalo te dane wprowadzone juz, czyli cos z bd
    @FXML
    private String insertingOnPlusClicked(ActionEvent event) {
        LocalDate localDate = datePick.getValue();
        String formattedUsersDate = localDate.format(DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH));
        datesLabel.setText("Chosen date: " + formattedUsersDate);
        //formattedUsersDate = localDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
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
        localDate = LocalDate.now();
        this.chosenDate = null;
        this.datePick.setValue(null);
        calendarActivities = new ArrayList<>();
        this.mongoDBService = new MongoDBService();
        //popup = new Popup();
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
        Rectangle calendarActivityRectangleClip = new Rectangle(rectangleWidth, rectangleHeight);
        stackPane.setClip(calendarActivityRectangleClip);
        StringBuilder tooltipEventsInfo = new StringBuilder();
        for (int k = 0; k < calendarActivities.size(); k++) {
            ToDoCalendarActivity activity = calendarActivities.get(k);
            tooltipEventsInfo.append((k+1)).append(". ").append(datePick.getValue().getDayOfWeek().toString().toLowerCase()).append(", ")
                    .append(activity.getChosenDate()).append("\nName: ").append(activity.getName()).append("\n")
                    .append(activity.getHowImportant()).append("\nMandatory: ").append(activity.getDutifully())
                    .append("\n");
            if (k >= 2) { //zmienic na do 5 wlacznie jak sie ogarnie + do bazy danych tez sa wrzucane
                Text moreActivities = new Text("...");
                moreActivities.setFont(Font.font("System", FontWeight.BOLD, 12));
                calendarActivityBox.getChildren().add(moreActivities);
                moreActivities.setOnMouseClicked(mouseEvent -> {
                    System.out.println(calendarActivities);
                });
                break;
            }
            Text text = new Text((k + 1) + ". ☆\n");
            text.setWrappingWidth(rectangleWidth * 0.75);
            text.setFont(Font.font("System", FontWeight.BOLD, 12));
            if(activity.getPriority() == 1) {
                text.setFill(Color.BLUE);
            } else if (activity.getPriority() == 2) {
                text.setFill(Color.GREEN);
            } else { text.setFill(Color.DARKRED); }
            text.setOnMouseClicked(mouseEvent -> {
                System.out.println(text.getText());
            });
            calendarActivityBox.getChildren().add(text);
        }
        calendarActivityBox.setStyle("-fx-background-color:LIGHTGRAY");
        /*calendarActivityBox.setPrefWidth(rectangleWidth * 0.8);
        calendarActivityBox.setPrefHeight(rectangleHeight * 0.65);*/
        //calendarActivityBox.setTranslateY(rectangleHeight * 0.25);
        stackPane.getChildren().removeIf(node -> node instanceof VBox);
        stackPane.getChildren().addFirst(calendarActivityBox);
        /*if(!this.toDoCalendarActivity.ifStillInProgress().equals("yes")) {
            mongoDBService.close();
        }*/
         //zmienic to pozniej i jakos ogarnac czy ktos chce zapisac wszystkie zmiany
        if(!calendarActivities.isEmpty()){
            Tooltip tooltip = new Tooltip(tooltipEventsInfo.toString());
            Tooltip.install(stackPane, tooltip);
        }
        datePick.getEditor().clear();
    }

    @FXML
    private void insertCalendarEvent(ActionEvent event) throws IOException {
        if(this.chosenDate == null) {
            warningLabel.setVisible(true);
            insertCalendarEvent.setDisable(true);
            return;
        }

        String dateString = this.chosenDate;
        this.toDoCalendarActivity = new ToDoCalendarActivity();
        ToDoCalendarActivity eventsDetails = toDoCalendarActivity.createAction();
        datesLabel.setVisible(false);

        eventsDetails.setChosenDate(dateString);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
        calendarActivities.add(eventsDetails);
        LocalDate dateTime = LocalDate.parse(dateString, formatter);
        List<ToDoCalendarActivity> newActivities = calendarActivities.stream()
                .filter(a -> {
                    LocalDate date = LocalDate.parse(a.getChosenDate(), formatter);
                    return date.equals(dateTime);
                }).collect(Collectors.toList());
        int day = dateTime.getDayOfMonth();
        this.mongoDBService.insertCalendarEvent(calendarActivities);
        StackPane dayStackPane = dayPaneMap.get(day);
        double rectangleWidth = calendar.getWidth() / 7;
        double rectangleHeight = calendar.getHeight() / 6;
        createCalendarActivity(newActivities, rectangleHeight, rectangleWidth, dayStackPane);
        datePick.getEditor().clear();
        datePick.setPromptText(datePick.getPromptText());
        if(!checkIfCreated) {
            this.checkIfCreated = true;
        }
        /*if(!popup.isShowing()) {
            //popup.getContent().add(popupForCalendar);
            popupForCalendar.setVisible(true);
            popup.show(insertCalendarEvent,
                    insertCalendarEvent.localToScreen(insertCalendarEvent.getBoundsInLocal()).getMinX(),
                    insertCalendarEvent.localToScreen(insertCalendarEvent.getBoundsInLocal()).getMaxY());
        } else {
            popup.hide();
        }*/
    }

    private Map<Integer, List<ToDoCalendarActivity>> createCalendarMap(List<ToDoCalendarActivity> calendarActivities) {
        Map<Integer, List<ToDoCalendarActivity>> calendarActivityMap = new HashMap<>();

        for(ToDoCalendarActivity activity : calendarActivities) {
            ZonedDateTime activityDate = activity.getDate();
            if(activityDate.getYear() == dateFocus.getYear() && activityDate.getMonth() == dateFocus.getMonth()) {
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
        return createCalendarMap(calendarActivities);
    }

   @FXML  //najwyzej sprawdzic czy overloading ok
   private List<ToDoCalendarActivity> showCalendarActivitiesMonth(ActionEvent event) {
       List<ToDoCalendarActivity> activities = mongoDBService.findAllActive(ToDoCalendarActivity.class);
       if(activities.isEmpty() && !checkIfCreated) {
           System.out.println("No added activities.");
           return null;
       }
       return activities;
   }
}