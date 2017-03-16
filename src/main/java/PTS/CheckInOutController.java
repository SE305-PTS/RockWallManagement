package PTS;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class CheckInOutController implements Initializable {
    @FXML public MenuItem checkInOutExit;
    @FXML public TextField checkInOutID;
    @FXML public Button checkInOutSubmit;

    private RockWallManagementApp rockWallManagementApp;

    public void setRockWallManagementApp(RockWallManagementApp app) {
        rockWallManagementApp = app;
        checkInOutExit.setOnAction(e -> {
            rockWallManagementApp.showMainPage();
        });
        checkInOutSubmit.setOnAction(e -> {
            if (!checkInOutID.getText().isEmpty()) {
                rockWallManagementApp.showMainPage();
            } else {

            }
        });
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

}
