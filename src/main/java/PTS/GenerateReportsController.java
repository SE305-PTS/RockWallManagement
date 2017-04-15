package PTS;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class GenerateReportsController implements Initializable {
    private static final Logger log = LoggerFactory.getLogger(PatronTableDAO.class);
    @FXML public MenuItem generateReportsExit;
    @FXML public Button reportGenerateButton;
    @FXML public TextField reportStartDate;
    @FXML public TextField reportEndDate;

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
            if(reportStartDate.getText().matches("\\d{4}[-]{1}\\d{2}[-]{1}\\d{2}") && reportEndDate.getText().matches("\\d{4}[-]{1}\\d{2}[-]{1}\\d{2}")) {
                Reports.sessionReport(reportStartDate.getText(), reportEndDate.getText());
            }
            else {
                log.error("Generate Report Failure: Dates didn't match regex.");
            }
        });
    }
}
