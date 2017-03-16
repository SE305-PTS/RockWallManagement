package PTS;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class EditManagerController implements Initializable {
    @FXML public MenuItem editManagerExit;
    @FXML public TextField editManagerFirstName;
    @FXML public TextField editManagerLastName;
    @FXML public TextField editManagerUsername;
    @FXML public TextField editManagerPassword;
    @FXML public TableView editManagerTable;

    private RockWallManagementApp rockWallManagementApp;

    public void setRockWallManagementApp(RockWallManagementApp app) {
        rockWallManagementApp = app;
        editManagerExit.setOnAction(e -> {
            rockWallManagementApp.showMainPage();
        });
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

}
