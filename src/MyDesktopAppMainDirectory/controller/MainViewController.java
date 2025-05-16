package MyDesktopAppMainDirectory.controller;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleGroup;

public class MainViewController {
    @FXML
    private Button addButton;

    @FXML
    private ToggleGroup group;

    @FXML
    private void onAddClicked() {
        System.out.println("Add button clicked! Running code user doesn't see");
        // Logic to handle adding a shopping list item
        addButton.setText("Add button clicked");
    }
}
