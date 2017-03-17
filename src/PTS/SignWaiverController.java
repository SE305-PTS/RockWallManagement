package PTS;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class CheckIn implements Initializable {
    @FXML public MenuItem checkInExit;
    @FXML public TextField signWaiverFirstName; //required
    @FXML public TextField signWaiverLastName; //required
    @FXML public TextField signWaiverID; //required
    @FXML public TextField signWaiverEmailAddress;
    @FXML public RadioButton signWaiverMale;
    @FXML public RadioButton signWaiverFemale;
    @FXML public CheckBox signWaiverEmailOptIn;
    @FXML public Button signWaiverSubmit;

    private RockWallManagementApp rockWallManagementApp;

    public void setRockWallManagementApp(RockWallManagementApp app) {
        rockWallManagementApp = app;
        checkInExit.setOnAction(e -> {
            rockWallManagementApp.showMainPage();
        });
        signWaiverSubmit.setOnAction(e -> {

            //grab and store data from fields here

            if (!(signWaiverFirstName.getText().isEmpty() && signWaiverLastName.getText().isEmpty() && signWaiverID.getText().isEmpty())) {
                rockWallManagementApp.showMainPage();
            } else {

            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

}
