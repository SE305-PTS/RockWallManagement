package PTS;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;

import java.net.URL;
import java.util.ResourceBundle;

public class GenerateReportsController implements Initializable {
    @FXML public MenuItem generateReportsExit;
    @FXML public Button reportExportButton;
    @FXML public Button reportGenerateButton;
    @FXML public RadioButton reportCheckIns;
    @FXML public RadioButton reportCertifications;
    @FXML public RadioButton reportSuspensions;
    @FXML public RadioButton reportIncidents;
    @FXML public RadioButton reportBelayYes;
    @FXML public RadioButton reportBelayNo;
    @FXML public RadioButton reportLeadYes;
    @FXML public RadioButton reportLeadNo;
    @FXML public RadioButton reportSuspendedYes;
    @FXML public RadioButton reportSuspendedNo;

    private RockWallManagementApp rockWallManagementApp;

    public void setRockWallManagementApp(RockWallManagementApp app) {
        rockWallManagementApp = app;
        generateReportsExit.setOnAction(e -> {
            rockWallManagementApp.showMainPage();
        });
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

}
