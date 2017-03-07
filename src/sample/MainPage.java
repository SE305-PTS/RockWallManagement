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

public class MainPage implements Initializable {
    @FXML private MenuBar mainMenuBar;
    @FXML private MenuItem menuLogin;
    @FXML private MenuItem mainMenuExit;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        menuLogin.setOnAction(e -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
                Stage stage = (Stage) mainMenuBar.getScene().getWindow();
                Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
                stage.setScene(new Scene(root, visualBounds.getWidth(), visualBounds.getHeight()));
                stage.show();
            } catch (Exception ex) {
                System.out.println("Login attempted: " + ex);
                ex.printStackTrace();
            }
        });
        mainMenuExit.setOnAction(e -> System.exit(0));
    }
}
