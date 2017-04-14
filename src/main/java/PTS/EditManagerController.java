package PTS;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class EditManagerController implements Initializable {
    private ObservableList<Account> managerObservableList = FXCollections.observableArrayList();
    private ObservableList<Account> filteredListSource = AccountTableDAO.select();
    private FilteredList<Account> filteredAccountList = new FilteredList<>(filteredListSource, p -> true);

    @FXML public MenuItem editManagerExit;
    @FXML public TextField editManagerFirstName;
    @FXML public TextField editManagerLastName;
    @FXML public TextField editManagerUsername;
    @FXML public TextField editManagerPassword;
    @FXML public TableView<Account> editManagerTable;
    @FXML public Button editManagerSubmit;
    @FXML public Button editManagerDelete;
    @FXML public ToggleButton editManagerTypeManager;
    @FXML public ToggleButton editManagerTypeAdministrator;
    @FXML public TableColumn<Account, String> managerFirstNameColumn;
    @FXML public TableColumn<Account, String> managerLastNameColumn;
    @FXML public TableColumn<Account, String> managerUsernameColumn;
    @FXML public TableColumn<Account, String> managerTypeColumn;

    private RockWallManagementApp rockWallManagementApp;

    public void setRockWallManagementApp(RockWallManagementApp app) {
        rockWallManagementApp = app;
        initTableView();
        editManagerExit.setOnAction(e -> rockWallManagementApp.showMainPage());
        editManagerSubmit.setOnAction(e -> {
            Account account = new Account();
            if (!(editManagerFirstName.getText().isEmpty() || editManagerLastName.getText().isEmpty() || editManagerUsername.getText().isEmpty() || editManagerPassword.getText().isEmpty())) {
                if (AccountTableDAO.getByUsername(editManagerUsername.getText()).getType() == null) {
                    account.setFirstName(editManagerFirstName.getText());
                    account.setLastName(editManagerLastName.getText());
                    account.setPassword(editManagerPassword.getText());
                    account.setUsername(editManagerUsername.getText());
                    if (editManagerTypeAdministrator.isSelected()) {
                        account.setType("A");
                    } else {
                        account.setType("M");
                    }
                    managerObservableList.add(account);
                    AccountTableDAO.insert(account);
                    filteredListSource.add(account);
                    editManagerFirstName.setText("");
                    editManagerLastName.setText("");
                    editManagerUsername.setText("");
                    editManagerPassword.setText("");
                    editManagerTypeManager.setSelected(true);
                }
            }
        });
        editManagerDelete.setOnAction(e -> {
            Account temp = editManagerTable.getSelectionModel().getSelectedItem();
            filteredListSource.remove(temp);
            managerObservableList.remove(temp);
            AccountTableDAO.delete(temp.getUsername());
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    private void initTableView() {
        managerObservableList.setAll(filteredAccountList);

        managerFirstNameColumn.setCellValueFactory(new PropertyValueFactory("firstName"));
        managerLastNameColumn.setCellValueFactory(new PropertyValueFactory("lastName"));
        managerUsernameColumn.setCellValueFactory(new PropertyValueFactory("username"));
        managerTypeColumn.setCellValueFactory(new PropertyValueFactory("type"));

        managerFirstNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        managerFirstNameColumn.setOnEditCommit((TableColumn.CellEditEvent<Account, String> event) -> {
            Account temp = event.getTableView().getItems().get(event.getTablePosition().getRow());
            temp.setFirstName(event.getNewValue());
            event.getTableView().getItems().set(event.getTablePosition().getRow(), temp);
            AccountTableDAO.update(temp);
        });

        managerLastNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        managerLastNameColumn.setOnEditCommit((TableColumn.CellEditEvent<Account, String> event) -> {
            Account temp = event.getTableView().getItems().get(event.getTablePosition().getRow());
            temp.setLastName(event.getNewValue());
            event.getTableView().getItems().set(event.getTablePosition().getRow(), temp);
            AccountTableDAO.update(temp);
        });

        managerUsernameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        managerUsernameColumn.setOnEditCommit((TableColumn.CellEditEvent<Account, String> event) -> {
            Account temp = event.getTableView().getItems().get(event.getTablePosition().getRow());
            temp.setUsername(event.getNewValue());
            event.getTableView().getItems().set(event.getTablePosition().getRow(), temp);
            AccountTableDAO.update(temp);
        });

        managerTypeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        managerTypeColumn.setOnEditCommit((TableColumn.CellEditEvent<Account, String> event) -> {
            Account temp = event.getTableView().getItems().get(event.getTablePosition().getRow());
            if (Objects.equals(event.getNewValue().toLowerCase(), "m") || Objects.equals(event.getNewValue().toLowerCase(), "a")) {
                temp.setType(event.getNewValue().toUpperCase());
            }
            event.getTableView().getItems().set(event.getTablePosition().getRow(), temp);
            AccountTableDAO.update(temp);
        });

        editManagerTable.setItems(managerObservableList);
    }

}
