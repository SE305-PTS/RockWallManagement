package PTS;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewPatronsController implements Initializable {
    @FXML public MenuItem viewPatronsExit;
    @FXML public Text viewPatronsTitle;
    @FXML public TextField patronIDField;
    @FXML public TextField patronNameField;
    @FXML public TextField patronNotesField;
    @FXML public TableView viewPatronsTable;
    @FXML public CheckBox patronBelayYes;
    @FXML public CheckBox patronBelayNo;
    @FXML public CheckBox patronLeadYes;
    @FXML public CheckBox patronLeadNo;
    @FXML public CheckBox patronSuspendedYes;
    @FXML public CheckBox patronSuspendedNo;

    private RockWallManagementApp rockWallManagementApp;

    public void setRockWallManagementApp(RockWallManagementApp app) {
        rockWallManagementApp = app;
        changeAccessView(rockWallManagementApp.getAccessLevel());
        viewPatronsExit.setOnAction(e -> {
            rockWallManagementApp.showMainPage();
        });
    }

    private void changeAccessView(RockWallManagementApp.Role accessLevel) {
        switch (accessLevel) {
            case EMPLOYEE:
                viewPatronsTitle.setText("View Patrons");
                break;
            case MANAGER:
                viewPatronsTitle.setText("View/Edit Patrons");
                break;
            case ADMINISTRATOR:
                viewPatronsTitle.setText("View/Edit Patrons");
                break;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

}
