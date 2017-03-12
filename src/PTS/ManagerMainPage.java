package PTS;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ManagerMainPage implements Initializable {
    @FXML private MenuBar managerMenuBar;
    @FXML private MenuItem managerMenuLogout;
    @FXML private MenuItem managerMenuExit;
    @FXML private Button managerSignWaiver;
    @FXML private Button managerCheckIn;
    @FXML private Button managerPatrons;
    @FXML private Button managerInventory;
    @FXML private Button managerGenerateReports;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        managerMenuLogout.setOnAction(e -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("MainPage.fxml"));
                Stage stage = (Stage) managerMenuBar.getScene().getWindow();
                Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
                stage.setScene(new Scene(root, visualBounds.getWidth(), visualBounds.getHeight()));
                stage.show();
            } catch (Exception ex) {
                System.out.println("Manager logout attempted: " + ex);
                ex.printStackTrace();
            }
        });
        managerMenuExit.setOnAction(e -> System.exit(0));
    }
}
