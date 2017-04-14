package PTS;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.converter.DoubleStringConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.PatternSyntaxException;

import static PTS.RockWallManagementApp.Role.ADMINISTRATOR;
import static PTS.RockWallManagementApp.Role.MANAGER;

public class ViewInventoryController implements Initializable {
    private ObservableList<Item> inventoryObservableList = FXCollections.observableArrayList();
    private FilteredList<Item> filteredInventoryList = new FilteredList<Item>(InventoryTableDAO.select(), p -> true);

    @FXML public MenuItem viewInventoryExit;
    @FXML public Text viewInventoryTitle;
    @FXML public TableView<Item> viewInventoryTable;
    @FXML public TextField inventoryIDField;
    @FXML public TextField inventoryPriceField;
    @FXML public TextField inventoryRetireField;
    @FXML public TextField inventoryTypeField;
    @FXML public TableColumn<Item, Integer> inventoryIDColumn;
    @FXML public TableColumn<Item, String> inventoryTypeColumn;
    @FXML public TableColumn<Item, String> inventoryRetireColumn;
//    @FXML public TableColumn<Item, Double> inventoryPriceColumn;
    @FXML public Button deleteItem;
    @FXML public Button inventoryExport;
    @FXML public Button inventoryAddButton;
    @FXML public VBox inventoryAddItemVBox;
    @FXML public TextField inventoryAddID;
    @FXML public TextField inventoryAddType;
    @FXML public TextField inventoryAddRetire;
    @FXML public TextField inventoryAddPrice;

    private RockWallManagementApp rockWallManagementApp;

    public void setRockWallManagementApp(RockWallManagementApp app) {
        rockWallManagementApp = app;
        changeAccessView(rockWallManagementApp.getAccessLevel());
        viewInventoryExit.setOnAction(e -> {
            rockWallManagementApp.showMainPage();
        });
        inventoryAddButton.setOnAction(e -> {
            Item item = new Item();
            if (!(inventoryAddID.getText().isEmpty() || inventoryAddType.getText().isEmpty() || inventoryAddRetire.getText().isEmpty() || inventoryAddPrice.getText().isEmpty())) {
                if (InventoryTableDAO.getByID(Integer.parseInt(inventoryAddID.getText())).getType() == null) {
                    if (inventoryAddRetire.getText().matches("\\d{4}[-]{1}\\d{2}[-]{1}\\d{2}\\s{1}[0-2]{1}\\d{1}[:]{1}[0-5]{2}")) {
                        if (Integer.valueOf(inventoryAddRetire.getText().substring(11, 12)) < 25) {
                            item.setID(Integer.valueOf(inventoryAddID.getText()));
                            item.setType(inventoryAddType.getText());
                            item.setRetireDate(inventoryAddRetire.getText());
                            item.setPrice(Double.valueOf(inventoryAddPrice.getText()));
                            inventoryObservableList.add(item);
                            filteredInventoryList.add(item);
                            InventoryTableDAO.insert(item);
                        }
                    }
                }
            }
        });
        deleteItem.setOnAction(e -> {
            Item temp = viewInventoryTable.getSelectionModel().getSelectedItem();
            filteredInventoryList.remove(temp);
            inventoryObservableList.remove(temp);
            InventoryTableDAO.delete(temp.getID());
        });
        initTableView();
    }

    private void changeAccessView(RockWallManagementApp.Role accessLevel) {
        switch (accessLevel) {
            case EMPLOYEE:
                viewInventoryTitle.setText("View Inventory");
                inventoryAddItemVBox.setVisible(false);
                deleteItem.setVisible(false);
                inventoryExport.setVisible(false);
                break;
            case MANAGER:
                viewInventoryTitle.setText("View/Edit Inventory");
                inventoryAddItemVBox.setVisible(true);
                deleteItem.setVisible(true);
                inventoryExport.setVisible(true);
                break;
            case ADMINISTRATOR:
                viewInventoryTitle.setText("View/Edit Inventory");
                inventoryAddItemVBox.setVisible(true);
                deleteItem.setVisible(true);
                inventoryExport.setVisible(true);
                break;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    private void initTableView() {

        inventoryObservableList.setAll(filteredInventoryList);

        inventoryIDField.textProperty().addListener(((observable, oldValue, newValue) -> {
            filteredInventoryList.setPredicate(this::filter);
            inventoryObservableList.setAll(filteredInventoryList);
        }));

        inventoryTypeField.textProperty().addListener(((observable, oldValue, newValue) -> {
            filteredInventoryList.setPredicate(this::filter);
            inventoryObservableList.setAll(filteredInventoryList);
        }));

        inventoryRetireField.textProperty().addListener(((observable, oldValue, newValue) -> {
            filteredInventoryList.setPredicate(this::filter);
            inventoryObservableList.setAll(filteredInventoryList);
        }));

        inventoryPriceField.textProperty().addListener(((observable, oldValue, newValue) -> {
            filteredInventoryList.setPredicate(this::filter);
            inventoryObservableList.setAll(filteredInventoryList);
        }));

        DoubleStringConverter priceConv = new DoubleStringConverter();

        inventoryIDColumn.setCellFactory(new PropertyValueFactory("ID"));
        inventoryTypeColumn.setCellFactory(new PropertyValueFactory("type"));
        inventoryRetireColumn.setCellFactory(new PropertyValueFactory("retireDate"));
//        inventoryPriceColumn.setCellFactory(new PropertyValueFactory("price"));

        if (rockWallManagementApp.getAccessLevel() == MANAGER || rockWallManagementApp.getAccessLevel() == ADMINISTRATOR) {
            inventoryTypeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
            inventoryTypeColumn.setOnEditCommit((TableColumn.CellEditEvent<Item, String> event) -> {
                Item temp = event.getTableView().getItems().get(event.getTablePosition().getRow());
                temp.setType(event.getNewValue());
                event.getTableView().getItems().set(event.getTablePosition().getRow(), temp);
                InventoryTableDAO.update(temp);
            });

            inventoryRetireColumn.setCellFactory(TextFieldTableCell.forTableColumn());
            inventoryRetireColumn.setOnEditCommit((TableColumn.CellEditEvent<Item, String> event) -> {
                Item temp = event.getTableView().getItems().get(event.getTablePosition().getRow());
                temp.setRetireDate(event.getNewValue());
                event.getTableView().getItems().set(event.getTablePosition().getRow(), temp);
                InventoryTableDAO.update(temp);
            });

//            inventoryPriceColumn.setCellFactory(TextFieldTableCell.forTableColumn(priceConv));
//            inventoryPriceColumn.setOnEditCommit((TableColumn.CellEditEvent<Item, Double> event) -> {
//                Item temp = event.getTableView().getItems().get(event.getTablePosition().getRow());
//                temp.setPrice(event.getNewValue());
//                event.getTableView().getItems().set(event.getTablePosition().getRow(), temp);
//                InventoryTableDAO.update(temp);
//            });
        }

        viewInventoryTable.setItems(inventoryObservableList);
    }

    private boolean filter(Item item) {
        boolean id;
        boolean type;
        boolean retireDate;
//        boolean price;

        id = inventoryIDField.getText().isEmpty() || Integer.toString(item.getID()).contains(inventoryIDField.getText());
        type = inventoryTypeField.getText().isEmpty() || item.getType().toLowerCase().contains(inventoryTypeField.getText().toLowerCase());
        retireDate = inventoryRetireField.getText().isEmpty() || item.getRetireDate().toLowerCase().contains(inventoryRetireField.getText().toLowerCase());
//        price = inventoryPriceField.getText().isEmpty() || item.getPrice().toString().contains(inventoryPriceField.getText());

        return id && type && retireDate;
    }

}
