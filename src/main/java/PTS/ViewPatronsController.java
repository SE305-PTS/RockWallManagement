package PTS;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

import static PTS.PatronTableDAO.selectAll;

public class ViewPatronsController implements Initializable {
    private ObservableList<Patron> patronObservableList = FXCollections.observableArrayList();

    @FXML public MenuItem viewPatronsExit;
    @FXML public Text viewPatronsTitle;
    @FXML public TextField patronIDField;
    @FXML public TextField patronFirstNameField;
    @FXML public TextField patronLastNameField;
    @FXML public TableView<Patron> viewPatronsTable;
    @FXML public TableColumn<Patron, String> firstNameColumn;
    @FXML public TableColumn<Patron, String> lastNameColumn;
    @FXML public TableColumn<Patron, Integer> idColumn;
    @FXML public TableColumn<Patron, Boolean> belayColumn;
    @FXML public TableColumn<Patron, Boolean> leadColumn;
    @FXML public TableColumn<Patron, String> suspendedColumn;
    @FXML public CheckBox patronBelayYes;
    @FXML public CheckBox patronBelayNo;
    @FXML public CheckBox patronLeadYes;
    @FXML public CheckBox patronLeadNo;
    @FXML public CheckBox patronSuspendedYes;
    @FXML public CheckBox patronSuspendedNo;

    private RockWallManagementApp rockWallManagementApp;

    public void setRockWallManagementApp(RockWallManagementApp app) {
        rockWallManagementApp = app;
        changeAccessView(rockWallManagementApp.getAccessLevel());
        viewPatronsExit.setOnAction(e -> {
            rockWallManagementApp.showMainPage();
        });
    }

    private void changeAccessView(RockWallManagementApp.Role accessLevel) {
        switch (accessLevel) {
            case EMPLOYEE:
                viewPatronsTitle.setText("View Patrons");
                break;
            case MANAGER:
                viewPatronsTitle.setText("View/Edit Patrons");
                break;
            case ADMINISTRATOR:
                viewPatronsTitle.setText("View/Edit Patrons");
                break;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        patronObservableList = selectAll();
        initTableView();
    }

    private void initTableView() {
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        firstNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        lastNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        idColumn.setCellValueFactory(new PropertyValueFactory("ID"));
//        idColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        belayColumn.setCellValueFactory(new PropertyValueFactory("belayCertified"));
//        belayColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        leadColumn.setCellValueFactory(new PropertyValueFactory("leadCertified"));
//        leadColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        suspendedColumn.setCellValueFactory(new PropertyValueFactory("suspended"));
        suspendedColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        viewPatronsTable.setItems(patronObservableList);
    }
}
