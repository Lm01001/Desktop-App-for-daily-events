package MyDesktopAppMainDirectory.controller;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ShoppingListController {
    @FXML
    private Button addButton;

    @FXML
    private void onAddClicked() {
        System.out.println("Add button clicked!");
        // Logic to handle adding a shopping list item
    }
}
