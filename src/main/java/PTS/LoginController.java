package PTS;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML private Button loginSubmit;
    @FXML private MenuItem loginReturnMainPage;
    @FXML private TextField loginUsername;
    @FXML private PasswordField loginPassword;
    @FXML private Text loginErrorText;

    private RockWallManagementApp rockWallManagementApp;

    public void setRockWallManagementApp(RockWallManagementApp app) {
        rockWallManagementApp = app;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loginErrorText.setVisible(false);
        loginSubmit.setOnAction(e -> {
            try {
                if (!(loginUsername.getText().isEmpty() || loginPassword.getText().isEmpty())) {
                    Account account = AccountTableDAO.getByUsername(loginUsername.getText());
                    if (account != null) {
                        if (Objects.equals(account.getPassword(), loginPassword.getText())) {
                            if (Objects.equals(account.getType(), "A")) {
                                rockWallManagementApp.setAccessLevel(RockWallManagementApp.Role.ADMINISTRATOR);
                            } else if (Objects.equals(account.getType(), "M")) {
                                rockWallManagementApp.setAccessLevel(RockWallManagementApp.Role.MANAGER);
                            }

                            rockWallManagementApp.showMainPage();
                        }
                    }
                }

                loginErrorText.setVisible(true);
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
