package PTS;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class GenerateReportsController implements Initializable {
    @FXML public MenuItem generateReportsExit;
    @FXML public Button reportGenerateButton;
    @FXML public RadioButton reportCheckIns;
    @FXML public RadioButton reportCertifications;
    @FXML public RadioButton reportSuspensions;
    @FXML public RadioButton reportBelayYes;
    @FXML public RadioButton reportBelayNo;
    @FXML public RadioButton reportLeadYes;
    @FXML public RadioButton reportLeadNo;
    @FXML public RadioButton reportSuspendedYes;
    @FXML public RadioButton reportSuspendedNo;
    @FXML public TextField reportStartDate;
    @FXML public TextField reportEndDate;
    @FXML public TextField reportName;
    @FXML public TextField reportID;

    private RockWallManagementApp rockWallManagementApp;

    public void setRockWallManagementApp(RockWallManagementApp app) {
        rockWallManagementApp = app;
        generateReportsExit.setOnAction(e -> {
            rockWallManagementApp.showMainPage();
        });
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        reportGenerateButton.setOnAction(e -> {
            if(reportCheckIns.isSelected()) generateCheckInReport();
            else if(reportCertifications.isSelected()) generateCertificationsReport();
            else if(reportSuspensions.isSelected()) generateSuspensionsReport();
        });
    }

    public void generateCheckInReport() {
        String startDate = reportStartDate.getText();
        String endDate = reportEndDate.getText();
        String name = reportName.getText();
        String id = reportID.getText();

    }

    public void generateCertificationsReport() {

    }

    public void generateSuspensionsReport() {

    }
}
