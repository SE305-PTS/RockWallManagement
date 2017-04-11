package PTS;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.text.Text;
import javafx.util.converter.BooleanStringConverter;

import java.net.URL;
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
    @FXML public TableColumn<Patron, Integer> idColumn;
    @FXML public TableColumn<Patron, String> firstNameColumn;
    @FXML public TableColumn<Patron, String> lastNameColumn;
    @FXML public TableColumn<Patron, String> emailColumn;
    @FXML public TableColumn<Patron, Boolean> subscriberColumn;
    @FXML public TableColumn<Patron, Boolean> belayColumn;
    @FXML public TableColumn<Patron, Boolean> leadColumn;
    @FXML public TableColumn<Patron, String> suspendedColumn;
    @FXML public CheckBox patronBelayYes;
    @FXML public CheckBox patronBelayNo;
    @FXML public CheckBox patronLeadYes;
    @FXML public CheckBox patronLeadNo;
    @FXML public CheckBox patronSuspendedYes;
    @FXML public CheckBox patronSuspendedNo;
    @FXML public CheckBox patronSubscriberYes;
    @FXML public CheckBox patronSubscriberNo;
    @FXML public Button deletePatron;

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
        deletePatron.setOnAction(e -> {
            Patron temp = viewPatronsTable.getSelectionModel().getSelectedItem();
            patronObservableList.remove(temp);
            PatronTableDAO.delete(temp.getID());
        });
    }

    private void initTableView() {
        viewPatronsTable.setItems(patronObservableList);

        BooleanStringConverter subscriberConv = new BooleanStringConverter();
        BooleanStringConverter belayConv = new BooleanStringConverter();
        BooleanStringConverter leadConv = new BooleanStringConverter();

        idColumn.setCellValueFactory(new PropertyValueFactory("ID"));

        firstNameColumn.setCellValueFactory(new PropertyValueFactory("firstName"));
        firstNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        firstNameColumn.setOnEditCommit((TableColumn.CellEditEvent<Patron, String> event) -> {
            Patron temp = event.getTableView().getItems().get(event.getTablePosition().getRow());
            temp.setFirstName(event.getNewValue());
            event.getTableView().getItems().set(event.getTablePosition().getRow(), temp);
            PatronTableDAO.update(temp);
        });

        lastNameColumn.setCellValueFactory(new PropertyValueFactory("lastName"));
        lastNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        lastNameColumn.setOnEditCommit((TableColumn.CellEditEvent<Patron, String> event) -> {
            Patron temp = event.getTableView().getItems().get(event.getTablePosition().getRow());
            temp.setLastName(event.getNewValue());
            event.getTableView().getItems().set(event.getTablePosition().getRow(), temp);
            PatronTableDAO.update(temp);
        });

        emailColumn.setCellValueFactory(new PropertyValueFactory("emailAddress"));
        emailColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        emailColumn.setOnEditCommit((TableColumn.CellEditEvent<Patron, String> event) -> {
            Patron temp = event.getTableView().getItems().get(event.getTablePosition().getRow());
            temp.setEmailAddress(event.getNewValue());
            event.getTableView().getItems().set(event.getTablePosition().getRow(), temp);
            PatronTableDAO.update(temp);
        });

        subscriberColumn.setCellValueFactory(new PropertyValueFactory("emailOptIn"));
        subscriberColumn.setCellFactory(TextFieldTableCell.forTableColumn(subscriberConv));
        subscriberColumn.setOnEditCommit((TableColumn.CellEditEvent<Patron, Boolean> event) -> {
            Patron temp = event.getTableView().getItems().get(event.getTablePosition().getRow());
            temp.setEmailOptIn(event.getNewValue());
            event.getTableView().getItems().set(event.getTablePosition().getRow(), temp);
            PatronTableDAO.update(temp);
        });

        belayColumn.setCellValueFactory(new PropertyValueFactory("belayCertified"));
        belayColumn.setCellFactory(TextFieldTableCell.forTableColumn(belayConv));
        belayColumn.setOnEditCommit((TableColumn.CellEditEvent<Patron, Boolean> event) -> {
            Patron temp = event.getTableView().getItems().get(event.getTablePosition().getRow());
            temp.setBelayCertified(event.getNewValue());
            event.getTableView().getItems().set(event.getTablePosition().getRow(), temp);
            PatronTableDAO.update(temp);
        });

        leadColumn.setCellValueFactory(new PropertyValueFactory("leadCertified"));
        leadColumn.setCellFactory(TextFieldTableCell.forTableColumn(leadConv));
        leadColumn.setOnEditCommit((TableColumn.CellEditEvent<Patron, Boolean> event) -> {
            Patron temp = event.getTableView().getItems().get(event.getTablePosition().getRow());
            temp.setLeadCertified(event.getNewValue());
            event.getTableView().getItems().set(event.getTablePosition().getRow(), temp);
            PatronTableDAO.update(temp);
        });

        suspendedColumn.setCellValueFactory(new PropertyValueFactory("suspended"));
        suspendedColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        suspendedColumn.setOnEditCommit((TableColumn.CellEditEvent<Patron, String> event) -> {
            Patron temp = event.getTableView().getItems().get(event.getTablePosition().getRow());
            temp.setSuspended(event.getNewValue());
            event.getTableView().getItems().set(event.getTablePosition().getRow(), temp);
            PatronTableDAO.update(temp);
        });
    }
}
