package PTS;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewPatrons implements Initializable {
    @FXML public MenuItem viewPatronsExit;

    private RockWallManagementApp rockWallManagementApp;

    public void setRockWallManagementApp(RockWallManagementApp app) {
        rockWallManagementApp = app;
        viewPatronsExit.setOnAction(e -> {
            rockWallManagementApp.showMainPage();
        });
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

}
