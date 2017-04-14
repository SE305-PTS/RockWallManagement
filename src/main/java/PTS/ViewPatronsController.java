package PTS;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.text.Text;
import javafx.util.converter.BooleanStringConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.ResourceBundle;

import static PTS.PatronTableDAO.select;
import static PTS.RockWallManagementApp.Role.ADMINISTRATOR;
import static PTS.RockWallManagementApp.Role.MANAGER;

public class ViewPatronsController implements Initializable {
    private ObservableList<Patron> patronObservableList = FXCollections.observableArrayList();
    private ObservableList<Patron> filteredListSource = select();
    private FilteredList<Patron> filteredPatronList = new FilteredList<Patron>(filteredListSource, p -> true);


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
    @FXML public TableColumn<Patron, String> genderColumn;
    @FXML public CheckBox patronBelayYes;
    @FXML public CheckBox patronBelayNo;
    @FXML public CheckBox patronLeadYes;
    @FXML public CheckBox patronLeadNo;
    @FXML public CheckBox patronSuspendedYes;
    @FXML public CheckBox patronSuspendedNo;
    @FXML public CheckBox patronMale;
    @FXML public CheckBox patronFemale;
    @FXML public CheckBox patronSubscriberYes;
    @FXML public CheckBox patronSubscriberNo;
    @FXML public Button deletePatron;
    @FXML public Button patronExport;

    private RockWallManagementApp rockWallManagementApp;

    public void setRockWallManagementApp(RockWallManagementApp app) {
        rockWallManagementApp = app;
        changeAccessView(rockWallManagementApp.getAccessLevel());
        initTableView();
        deletePatron.setOnAction(e -> {
            Patron temp = viewPatronsTable.getSelectionModel().getSelectedItem();
            filteredListSource.remove(temp);
            patronObservableList.remove(temp);
            PatronTableDAO.delete(temp.getID());
        });
    }

    private void changeAccessView(RockWallManagementApp.Role accessLevel) {
        switch (accessLevel) {
            case EMPLOYEE:
                viewPatronsTitle.setText("View Patrons");
                deletePatron.setVisible(false);
                break;
            case MANAGER:
                viewPatronsTitle.setText("View/Edit Patrons");
                deletePatron.setVisible(true);
                break;
            case ADMINISTRATOR:
                viewPatronsTitle.setText("View/Edit Patrons");
                deletePatron.setVisible(true);
                break;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        viewPatronsExit.setOnAction(e -> {
            rockWallManagementApp.showMainPage();
        });
        patronExport.setOnAction(e -> {
            Reports.patronReport(patronObservableList);
        });
    }

    private void initTableView() {

        patronObservableList.setAll(filteredPatronList);

        patronIDField.textProperty().addListener(((observable, oldValue, newValue) -> {
            filteredPatronList.setPredicate(this::filter);
            patronObservableList.setAll(filteredPatronList);
        }));

        patronFirstNameField.textProperty().addListener(((observable, oldValue, newValue) -> {
            filteredPatronList.setPredicate(this::filter);
            patronObservableList.setAll(filteredPatronList);
        }));

        patronLastNameField.textProperty().addListener(((observable, oldValue, newValue) -> {
            filteredPatronList.setPredicate(this::filter);
            patronObservableList.setAll(filteredPatronList);
        }));

        patronSubscriberYes.setOnAction(e -> {
            filteredPatronList.setPredicate(this::filter);
            patronObservableList.setAll(filteredPatronList);
        });

        patronSubscriberNo.setOnAction(e -> {
            filteredPatronList.setPredicate(this::filter);
            patronObservableList.setAll(filteredPatronList);
        });

        patronBelayYes.setOnAction(e -> {
            filteredPatronList.setPredicate(this::filter);
            patronObservableList.setAll(filteredPatronList);
        });

        patronBelayNo.setOnAction(e -> {
            filteredPatronList.setPredicate(this::filter);
            patronObservableList.setAll(filteredPatronList);
        });

        patronLeadYes.setOnAction(e -> {
            filteredPatronList.setPredicate(this::filter);
            patronObservableList.setAll(filteredPatronList);
        });

        patronLeadNo.setOnAction(e -> {
            filteredPatronList.setPredicate(this::filter);
            patronObservableList.setAll(filteredPatronList);
        });

        patronSuspendedYes.setOnAction(e -> {
            filteredPatronList.setPredicate(this::filter);
            patronObservableList.setAll(filteredPatronList);
        });

        patronSuspendedNo.setOnAction(e -> {
            filteredPatronList.setPredicate(this::filter);
            patronObservableList.setAll(filteredPatronList);
        });

        patronMale.setOnAction(e -> {
            filteredPatronList.setPredicate(this::filter);
            patronObservableList.setAll(filteredPatronList);
        });

        patronFemale.setOnAction(e -> {
            filteredPatronList.setPredicate(this::filter);
            patronObservableList.setAll(filteredPatronList);
        });

        BooleanStringConverter subscriberConv = new BooleanStringConverter();
        BooleanStringConverter belayConv = new BooleanStringConverter();
        BooleanStringConverter leadConv = new BooleanStringConverter();

        idColumn.setCellValueFactory(new PropertyValueFactory("ID"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory("lastName"));
        emailColumn.setCellValueFactory(new PropertyValueFactory("emailAddress"));
        subscriberColumn.setCellValueFactory(new PropertyValueFactory("emailOptIn"));
        belayColumn.setCellValueFactory(new PropertyValueFactory("belayCertified"));
        leadColumn.setCellValueFactory(new PropertyValueFactory("leadCertified"));
        suspendedColumn.setCellValueFactory(new PropertyValueFactory("suspended"));
        genderColumn.setCellValueFactory(new PropertyValueFactory("gender"));

        if (rockWallManagementApp.getAccessLevel() == MANAGER || rockWallManagementApp.getAccessLevel() == ADMINISTRATOR) {
            firstNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
            firstNameColumn.setOnEditCommit((TableColumn.CellEditEvent<Patron, String> event) -> {
                Patron temp = event.getTableView().getItems().get(event.getTablePosition().getRow());
                temp.setFirstName(event.getNewValue());
                event.getTableView().getItems().set(event.getTablePosition().getRow(), temp);
                PatronTableDAO.update(temp);
            });

            lastNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
            lastNameColumn.setOnEditCommit((TableColumn.CellEditEvent<Patron, String> event) -> {
                Patron temp = event.getTableView().getItems().get(event.getTablePosition().getRow());
                temp.setLastName(event.getNewValue());
                event.getTableView().getItems().set(event.getTablePosition().getRow(), temp);
                PatronTableDAO.update(temp);
            });

            emailColumn.setCellFactory(TextFieldTableCell.forTableColumn());
            emailColumn.setOnEditCommit((TableColumn.CellEditEvent<Patron, String> event) -> {
                Patron temp = event.getTableView().getItems().get(event.getTablePosition().getRow());
                temp.setEmailAddress(event.getNewValue());
                event.getTableView().getItems().set(event.getTablePosition().getRow(), temp);
                PatronTableDAO.update(temp);
            });

            subscriberColumn.setCellFactory(TextFieldTableCell.forTableColumn(subscriberConv));
            subscriberColumn.setOnEditCommit((TableColumn.CellEditEvent<Patron, Boolean> event) -> {
                Patron temp = event.getTableView().getItems().get(event.getTablePosition().getRow());
                temp.setEmailOptIn(event.getNewValue());
                event.getTableView().getItems().set(event.getTablePosition().getRow(), temp);
                PatronTableDAO.update(temp);
            });

            belayColumn.setCellFactory(TextFieldTableCell.forTableColumn(belayConv));
            belayColumn.setOnEditCommit((TableColumn.CellEditEvent<Patron, Boolean> event) -> {
                Patron temp = event.getTableView().getItems().get(event.getTablePosition().getRow());
                temp.setBelayCertified(event.getNewValue());
                event.getTableView().getItems().set(event.getTablePosition().getRow(), temp);
                PatronTableDAO.update(temp);
            });

            leadColumn.setCellFactory(TextFieldTableCell.forTableColumn(leadConv));
            leadColumn.setOnEditCommit((TableColumn.CellEditEvent<Patron, Boolean> event) -> {
                Patron temp = event.getTableView().getItems().get(event.getTablePosition().getRow());
                temp.setLeadCertified(event.getNewValue());
                event.getTableView().getItems().set(event.getTablePosition().getRow(), temp);
                PatronTableDAO.update(temp);
            });

            suspendedColumn.setCellFactory(TextFieldTableCell.forTableColumn());
            suspendedColumn.setOnEditCommit((TableColumn.CellEditEvent<Patron, String> event) -> {
                Patron temp = event.getTableView().getItems().get(event.getTablePosition().getRow());
                if (Objects.equals(event.getNewValue(), "")) {
                    temp.setSuspended("");
                } else {
                    LocalDateTime time = LocalDateTime.now();
                    String year;

                    if (time.getMonthValue() > 5) {
                        year = Integer.toString(time.getYear() + 1);
                    } else {
                        year = Integer.toString(time.getYear());
                    }

                    String timeStamp = year + "-06-01 00:00";
                    temp.setSuspended(timeStamp);
                }
                event.getTableView().getItems().set(event.getTablePosition().getRow(), temp);
                PatronTableDAO.update(temp);
            });

            genderColumn.setCellFactory(TextFieldTableCell.forTableColumn());
            genderColumn.setOnEditCommit((TableColumn.CellEditEvent<Patron, String> event) -> {
                Patron temp = event.getTableView().getItems().get(event.getTablePosition().getRow());
                if (Objects.equals(event.getNewValue(), "")) {
                    temp.setGender("");
                } else {
                    if (event.getNewValue().toLowerCase().contains("f")) {
                        temp.setGender("F");
                    } else if (event.getNewValue().toLowerCase().contains("m")) {
                        temp.setGender("M");
                    }
                }
                event.getTableView().getItems().set(event.getTablePosition().getRow(), temp);
                PatronTableDAO.update(temp);
            });
        }

        viewPatronsTable.setItems(patronObservableList);
    }

    private boolean filter(Patron patron) {
        boolean id;
        boolean firstName;
        boolean lastName;
        boolean subscriber;
        boolean belay;
        boolean lead;
        boolean suspended;
        boolean gender;

        id = patronIDField.getText().isEmpty() || Integer.toString(patron.getID()).contains(patronIDField.getText());

        firstName = patronFirstNameField.getText().isEmpty() || patron.getFirstName().toLowerCase().contains(patronFirstNameField.getText().toLowerCase());

        lastName = patronLastNameField.getText().isEmpty() || patron.getLastName().toLowerCase().contains(patronLastNameField.getText().toLowerCase());

        if (patronSubscriberYes.isSelected() && patronSubscriberNo.isSelected()) {
            subscriber = true;
        } else if (!(patronSubscriberYes.isSelected() || patronSubscriberNo.isSelected())) {
            subscriber = true;
        } else if (patronSubscriberYes.isSelected()) {
            subscriber = patron.getEmailOptIn();
        } else {
            subscriber = !patron.getEmailOptIn();
        }

        if (patronBelayYes.isSelected() && patronBelayNo.isSelected()) {
            belay = true;
        } else if (!(patronBelayYes.isSelected() || patronBelayNo.isSelected())) {
            belay = true;
        } else if (patronBelayYes.isSelected()) {
            belay = patron.getBelayCertified();
        } else {
            belay = !patron.getBelayCertified();
        }

        if (patronLeadYes.isSelected() && patronLeadNo.isSelected()) {
            lead = true;
        } else if (!(patronLeadYes.isSelected() || patronLeadNo.isSelected())) {
            lead = true;
        } else if (patronLeadYes.isSelected()) {
            lead = patron.getLeadCertified();
        } else {
            lead = !patron.getLeadCertified();
        }

        if (patronSuspendedYes.isSelected() && patronSuspendedNo.isSelected()) {
            suspended = true;
        } else if (!(patronSuspendedYes.isSelected() || patronSuspendedNo.isSelected())) {
            suspended = true;
        } else if (patronSuspendedYes.isSelected()) {
            suspended = !patron.getSuspended().isEmpty();
        } else {
            suspended = patron.getSuspended().isEmpty();
        }

        if (patronMale.isSelected() && patronFemale.isSelected()) {
            gender = true;
        } else if (!(patronMale.isSelected() || patronFemale.isSelected())) {
            gender = true;
        } else if (patronMale.isSelected()) {
            gender = Objects.equals(patron.getGender(), "M");
        } else {
            gender = Objects.equals(patron.getGender(), "F");
        }

        return id && firstName && lastName && subscriber && belay && lead && suspended && gender;
    }
}
