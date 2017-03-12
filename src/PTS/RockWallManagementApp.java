package PTS;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

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

        showMainPage();
    }

    private void showPage(Parent root) {
        Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setScene(new Scene(root, visualBounds.getWidth(), visualBounds.getHeight()));
        primaryStage.show();
    }

    public void showCheckInPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CheckIn.fxml"));
            Parent root = loader.load();
            CheckIn controller = loader.getController();
            controller.setRockWallManagementApp(this);
            showPage(root);
        } catch (Exception e) {e.printStackTrace();}
    }

    public void showCheckOutPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CheckOut.fxml"));
            Parent root = loader.load();
            CheckOut controller = loader.getController();
            controller.setRockWallManagementApp(this);
            showPage(root);
        } catch (Exception e) {e.printStackTrace();}
    }

    public void showEditManagerPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EditManager.fxml"));
            Parent root = loader.load();
            EditManager controller = loader.getController();
            controller.setRockWallManagementApp(this);
            showPage(root);
        } catch (Exception e) {e.printStackTrace();}
    }

    public void showGenerateReportPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("GenerateReports.fxml"));
            Parent root = loader.load();
            GenerateReports controller = loader.getController();
            controller.setRockWallManagementApp(this);
            showPage(root);
        } catch (Exception e) {e.printStackTrace();}
    }

    public void showLoginPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Parent root = loader.load();
            Login controller = loader.getController();
            controller.setRockWallManagementApp(this);
            showPage(root);
        } catch (Exception e) {e.printStackTrace();}
    }

    public void showMainPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainPage.fxml"));
            Parent root = loader.load();
            MainPage controller = loader.getController();
            controller.setRockWallManagementApp(this);
            showPage(root);
        } catch (Exception e) {e.printStackTrace();}
    }

    public void showViewInventoryPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewInventory.fxml"));
            Parent root = loader.load();
            ViewInventory controller = loader.getController();
            controller.setRockWallManagementApp(this);
            showPage(root);
        } catch (Exception e) {e.printStackTrace();}
    }

    public void showViewPatronsPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewPatrons.fxml"));
            Parent root = loader.load();
            ViewPatrons controller = loader.getController();
            controller.setRockWallManagementApp(this);
            showPage(root);
        } catch (Exception e) {e.printStackTrace();}
    }

    public static void main(String[] args) {
        launch(args);
    }
}
