package PTS;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class Login implements Initializable {
    @FXML private Button loginSubmit;
    @FXML private MenuBar loginMenuBar;
    @FXML private MenuItem loginReturnMainPage;
    @FXML private TextField loginUsername;
    @FXML private PasswordField loginPassword;

    private RockWallManagementApp rockWallManagementApp;

    public void setRockWallManagementApp(RockWallManagementApp app) {
        rockWallManagementApp = app;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loginSubmit.setOnAction(e -> {
            try {
                Parent root;
                if (Objects.equals(loginUsername.getText(), "Admin")) {
                    rockWallManagementApp.setAccessLevel(RockWallManagementApp.Role.ADMINISTRATOR);
                } else if (Objects.equals(loginUsername.getText(), "Manager")) {
                    rockWallManagementApp.setAccessLevel(RockWallManagementApp.Role.MANAGER);
                }

                rockWallManagementApp.showMainPage();
            } catch (Exception ex) {
                System.out.println("Submit button pressed: " + ex);
                ex.printStackTrace();
            }
        });
        loginReturnMainPage.setOnAction(e -> {
            try {
                rockWallManagementApp.showMainPage();
            } catch (Exception ex) {
                System.out.println("Return to main page: " + ex);
                ex.printStackTrace();
            }
        });
    }
}
