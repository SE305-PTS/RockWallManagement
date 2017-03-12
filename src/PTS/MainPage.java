package PTS;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

import static PTS.RockWallManagementApp.Role;

public class MainPage implements Initializable {
    @FXML public MenuBar mainMenuBar;
    @FXML public MenuItem menuLogin;
    @FXML public MenuItem menuLogout;
    @FXML private MenuItem mainMenuExit;
    @FXML private Button mainSignWaiver;
    @FXML private Button mainCheckIn;
    @FXML private Button mainViewPatrons;
    @FXML private Button mainViewInventory;
    @FXML private Button mainGenerateReports;
    @FXML private Button mainEditManagers;
    @FXML private Text mainAccessText;

    private RockWallManagementApp rockWallManagementApp;

    public void setRockWallManagementApp(RockWallManagementApp app) {
        rockWallManagementApp = app;
        changeAccessView(rockWallManagementApp.getAccessLevel());
        menuLogin.setOnAction(e -> {
            rockWallManagementApp.showLoginPage();
        });
        menuLogout.setOnAction(e -> {
            rockWallManagementApp.setAccessLevel(Role.EMPLOYEE);
            changeAccessView(rockWallManagementApp.getAccessLevel());
        });
        mainSignWaiver.setOnAction(e -> {
            rockWallManagementApp.showCheckInPage();
        });
        mainCheckIn.setOnAction(e -> {
            rockWallManagementApp.showCheckOutPage();
        });
        mainViewPatrons.setOnAction(e -> {
            rockWallManagementApp.showViewPatronsPage();
        });
        mainViewInventory.setOnAction(e -> {
            rockWallManagementApp.showViewInventoryPage();
        });
        mainGenerateReports.setOnAction(e -> {
            rockWallManagementApp.showGenerateReportPage();
        });
        mainEditManagers.setOnAction(e -> {
            rockWallManagementApp.showEditManagerPage();
        });
    }

    public void changeAccessView(Role accessLevel) {
        switch (accessLevel) {
            case EMPLOYEE:
                mainEditManagers.setVisible(false);
                mainAccessText.setVisible(false);
                menuLogin.setVisible(true);
                menuLogout.setVisible(false);
                break;
            case MANAGER:
                mainEditManagers.setVisible(false);
                mainAccessText.setText("Logged in as Manager");
                mainAccessText.setFill(Color.BLUE);
                mainAccessText.setVisible(true);
                menuLogin.setVisible(false);
                menuLogout.setVisible(true);
                break;
            case ADMINISTRATOR:
                mainEditManagers.setVisible(true);
                mainAccessText.setText("Logged in as Administrator");
                mainAccessText.setFill(Color.RED);
                mainAccessText.setVisible(true);
                menuLogin.setVisible(false);
                menuLogout.setVisible(true);
                break;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mainMenuExit.setOnAction(e -> System.exit(0));
    }

}
