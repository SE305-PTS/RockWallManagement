package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminMainPage implements Initializable {
    @FXML private MenuBar adminMenuBar;
    @FXML private MenuItem adminMenuLogout;
    @FXML private MenuItem adminMenuExit;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        adminMenuLogout.setOnAction(e -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("MainPage.fxml"));
                Stage stage = (Stage) adminMenuBar.getScene().getWindow();
                Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
                stage.setScene(new Scene(root, visualBounds.getWidth(), visualBounds.getHeight()));
                stage.show();
            } catch (Exception ex) {
            System.out.println("Admin logout attempted: " + ex);
            ex.printStackTrace();
            }
        });
        adminMenuExit.setOnAction(e -> System.exit(0));
    }
}
