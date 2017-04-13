package PTS;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class SignWaiverController implements Initializable {
    @FXML public MenuItem signWaiverExit;
    @FXML public TextField signWaiverFirstName; //required
    @FXML public TextField signWaiverLastName; //required
    @FXML public TextField signWaiverID; //required
    @FXML public TextField signWaiverEmailAddress;
    @FXML public RadioButton signWaiverMale;
    @FXML public RadioButton signWaiverFemale;
    @FXML public CheckBox signWaiverEmailOptIn;
    @FXML public Button signWaiverSubmit;

    private RockWallManagementApp rockWallManagementApp;

    public void setRockWallManagementApp(RockWallManagementApp app) {
        rockWallManagementApp = app;
        signWaiverExit.setOnAction(e -> rockWallManagementApp.showMainPage());
        signWaiverSubmit.setOnAction(e -> {
            boolean validID = false;

            if (!signWaiverID.getText().isEmpty()) {
                if (PatronTableDAO.getByID(Integer.parseInt(signWaiverID.getText())).getFirstName() == null) {
                    validID = true;
                }
            }

            if (!(signWaiverFirstName.getText().isEmpty() || signWaiverLastName.getText().isEmpty()) && validID) {
                Patron patron = new Patron(Integer.parseInt(signWaiverID.getText()), signWaiverFirstName.getText(), signWaiverLastName.getText());
                if (!signWaiverEmailAddress.getText().isEmpty()) {
                    patron.setEmailAddress(signWaiverEmailAddress.getText());
                    if (signWaiverEmailOptIn.isSelected()) {
                        patron.setEmailOptIn(true);
                    }
                }
                if (signWaiverMale.isSelected()) {
                    patron.setGender("M");
                } else if (signWaiverFemale.isSelected()) {
                    patron.setGender("F");
                }

                PatronTableDAO.insert(patron);
                rockWallManagementApp.showMainPage();
            } else {
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        signWaiverID.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                signWaiverID.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

}
