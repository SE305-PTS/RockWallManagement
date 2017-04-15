package PTS;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.util.Objects;

public class RockWallManagementApp extends Application {

    private Stage primaryStage;

    public enum Role {
        EMPLOYEE, MANAGER, ADMINISTRATOR
    }

    private Role accessLevel = Role.EMPLOYEE;

    public Role getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(Role accessLevel) {
        this.accessLevel = accessLevel;
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Rock Wall Management");
        DBInterface.init();
        setup();
        showMainPage();
    }

    private void showPage(Parent root) {
        Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setScene(new Scene(root, visualBounds.getWidth(), visualBounds.getHeight()));
        primaryStage.show();
    }

    public void showCheckInPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SignWaiver.fxml"));
            Parent root = loader.load();
            SignWaiverController controller = loader.getController();
            controller.setRockWallManagementApp(this);
            showPage(root);
        } catch (Exception e) {e.printStackTrace();}
    }

    public void showCheckOutPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CheckInOut.fxml"));
            Parent root = loader.load();
            CheckInOutController controller = loader.getController();
            controller.setRockWallManagementApp(this);
            showPage(root);
        } catch (Exception e) {e.printStackTrace();}
    }

    public void showEditManagerPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/EditManager.fxml"));
            Parent root = loader.load();
            EditManagerController controller = loader.getController();
            controller.setRockWallManagementApp(this);
            showPage(root);
        } catch (Exception e) {e.printStackTrace();}
    }

    public void showGenerateReportPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/GenerateReports.fxml"));
            Parent root = loader.load();
            GenerateReportsController controller = loader.getController();
            controller.setRockWallManagementApp(this);
            showPage(root);
        } catch (Exception e) {e.printStackTrace();}
    }

    public void showLoginPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
            Parent root = loader.load();
            LoginController controller = loader.getController();
            controller.setRockWallManagementApp(this);
            showPage(root);
        } catch (Exception e) {e.printStackTrace();}
    }

    public void showMainPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainPage.fxml"));
            Parent root = loader.load();
            MainPageController controller = loader.getController();
            controller.setRockWallManagementApp(this);
            showPage(root);
        } catch (Exception e) {e.printStackTrace();}
    }

    public void showViewInventoryPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ViewInventory.fxml"));
            Parent root = loader.load();
            ViewInventoryController controller = loader.getController();
            controller.setRockWallManagementApp(this);
            showPage(root);
        } catch (Exception e) {e.printStackTrace();}
    }

    public void showViewPatronsPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ViewPatrons.fxml"));
            Parent root = loader.load();
            ViewPatronsController controller = loader.getController();
            controller.setRockWallManagementApp(this);
            showPage(root);
        } catch (Exception e) {e.printStackTrace();}
    }

    private void setup() {

        // if there are no admins in the database, logins in as admin to allowing creation of admin account
        boolean adminExists = false;
        for (Account a : AccountTableDAO.select()) {
            if (Objects.equals(a.getType(), "A")) {
                adminExists = true;
            }
        }
        if (!adminExists) {
            accessLevel = Role.ADMINISTRATOR;
        }

        // closes all sessions from previous day that were left open
        LocalDateTime time = LocalDateTime.now();
        String month;
        String day;
        if (time.getMonthValue() < 10) {
            month = "0" + time.getMonthValue();
        } else {
            month = "" + time.getMonthValue();
        }
        if (time.getDayOfMonth() < 10) {
            day = "0" + time.getDayOfMonth();
        } else {
            day = "" + time.getDayOfMonth();
        }
        String timeStamp = "" + time.getYear() + "-" + month + "-" + day;
        for (Session s : SessionTableDAO.select()) {
            if (s.getCheckOut() == null) {
                if (s.getCheckIn().substring(0,9) == timeStamp) {
                    s.setCheckOut(s.getCheckIn().substring(0, 10).concat("23:59"));
                }
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
