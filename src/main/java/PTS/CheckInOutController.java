package PTS;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class CheckInOutController implements Initializable {
    @FXML public MenuItem checkInOutExit;
    @FXML public TextField checkInOutID;
    @FXML public Button checkInOutSubmit;
    @FXML public Text checkInSuspendedText;
    @FXML public Text checkInSuccessText;

    private RockWallManagementApp rockWallManagementApp;

    public void setRockWallManagementApp(RockWallManagementApp app) {
        rockWallManagementApp = app;
        checkInOutExit.setOnAction(e -> {
            rockWallManagementApp.showMainPage();
        });
        checkInOutSubmit.setOnAction(e -> {
            checkInSuspendedText.setVisible(false);
            checkInSuccessText.setVisible(false);
            if (!checkInOutID.getText().isEmpty()) {
                Patron patron = PatronTableDAO.getByID(Integer.parseInt(checkInOutID.getText()));
                if (patron.getID() == Integer.parseInt(checkInOutID.getText())) {
                    LocalDateTime time = LocalDateTime.now();
                    String month;
                    String day;
                    String hour;
                    String minute;
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
                    if (time.getHour() < 10) {
                        hour = "0" + time.getHour();
                    } else {
                        hour = "" + time.getHour();
                    }
                    if (time.getMinute() < 10) {
                        minute = "0" + time.getMinute();
                    } else {
                        minute = "" + time.getMinute();
                    }
                    String timeStamp = "" + time.getYear() + "-" + month + "-" + day + " " + hour + ":" + minute;

                    Session session = SessionTableDAO.getLatestByMemberID(patron.getID());

                    String successText = "Patron with ID " + patron.getID() + " has been checked ";

                    if (session.getCheckIn() == null) {
                        if (patron.getSuspended().isEmpty()) {
                            session.setCheckIn(timeStamp);
                            session.setPatronID(Integer.parseInt(checkInOutID.getText()));
                            session.setSessionID(SessionTableDAO.getMaxID() + 1);
                            SessionTableDAO.insert(session);
                            checkInSuccessText.setText(successText + "in");
                            checkInSuccessText.setVisible(true);
                            checkInOutID.setText("");
                        } else {
                            checkInOutID.setText("");
                            checkInSuspendedText.setVisible(true);
                        }
                    } else {
                        session.setCheckOut(timeStamp);
                        SessionTableDAO.update(session);
                        checkInSuccessText.setText(successText + "out");
                        checkInSuccessText.setVisible(true);
                        checkInOutID.setText("");
                    }
                }
            }
        });
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        checkInSuspendedText.setVisible(false);
        checkInSuccessText.setVisible(false);
        checkInOutID.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                checkInOutID.setText(oldValue);
            }
        });
    }

}
