package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class Login implements Initializable {
    @FXML private Button loginSubmit;
    @FXML private MenuBar loginMenuBar;
    @FXML private MenuItem loginReturnMainPage;
    @FXML private TextField loginUsername;
    @FXML private PasswordField loginPassword;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loginSubmit.setOnAction(e -> {
            try {
                Parent root;
                if (Objects.equals(loginUsername.getText(), "Admin")) {
                    root = FXMLLoader.load(getClass().getResource("AdminMainPage.fxml"));
                    Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
                    Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
                    stage.setScene(new Scene(root, visualBounds.getWidth(), visualBounds.getHeight()));
                    stage.show();
                } else if (Objects.equals(loginUsername.getText(), "Manager")) {
                    root = FXMLLoader.load(getClass().getResource("ManagerMainPage.fxml"));
                    Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
                    Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
                    stage.setScene(new Scene(root, visualBounds.getWidth(), visualBounds.getHeight()));
                    stage.show();
                }
            } catch (Exception ex) {
                System.out.println("Submit button pressed: " + ex);
                ex.printStackTrace();
            }
        });
        loginReturnMainPage.setOnAction(e -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("MainPage.fxml"));
                Stage stage = (Stage) loginMenuBar.getScene().getWindow();
                Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
                stage.setScene(new Scene(root, visualBounds.getWidth(), visualBounds.getHeight()));
                stage.show();
            } catch (Exception ex) {
                System.out.println("Return to main page: " + ex);
                ex.printStackTrace();
            }
        });
    }
}
