package PTS;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewInventoryController implements Initializable {
    @FXML public MenuItem viewInventoryExit;
    @FXML public Text viewInventoryTitle;
    @FXML public TableView viewInventoryTable;
    @FXML public TextField inventoryIDField;
    @FXML public TextField inventoryPurchaseField;
    @FXML public TextField inventoryRetireField;
    @FXML public TextField inventoryTypeField;

    private RockWallManagementApp rockWallManagementApp;

    public void setRockWallManagementApp(RockWallManagementApp app) {
        rockWallManagementApp = app;
        changeAccessView(rockWallManagementApp.getAccessLevel());
        viewInventoryExit.setOnAction(e -> {
            rockWallManagementApp.showMainPage();
        });
    }

    private void changeAccessView(RockWallManagementApp.Role accessLevel) {
        switch (accessLevel) {
            case EMPLOYEE:
                viewInventoryTitle.setText("View Inventory");
                break;
            case MANAGER:
                viewInventoryTitle.setText("View/Edit Inventory");
                break;
            case ADMINISTRATOR:
                viewInventoryTitle.setText("View/Edit Inventory");
                break;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

}
