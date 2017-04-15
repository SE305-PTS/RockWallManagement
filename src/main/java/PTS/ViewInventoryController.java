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

import java.net.URL;
import java.util.ResourceBundle;

public class ViewInventoryController implements Initializable {
    private ObservableList<Item> inventoryObservableList = FXCollections.observableArrayList();
    private ObservableList<Item> filteredListSource = InventoryTableDAO.select();
    private FilteredList<Item> filteredInventoryList = new FilteredList<>(filteredListSource, p -> true);

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
    @FXML public TableColumn<Item, Double> inventoryPriceColumn;
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
        viewInventoryExit.setOnAction(e -> rockWallManagementApp.showMainPage());
        inventoryExport.setOnAction(e -> Reports.inventoryReport(inventoryObservableList));
        inventoryAddButton.setOnAction(e -> {
            Item item = new Item();
            if (!(inventoryAddID.getText().isEmpty() || inventoryAddType.getText().isEmpty() || inventoryAddRetire.getText().isEmpty() || inventoryAddPrice.getText().isEmpty())) {
                if (InventoryTableDAO.getByID(Integer.parseInt(inventoryAddID.getText())).getType() == null) {
                    if (inventoryAddRetire.getText().matches("\\d{4}[-]{1}\\d{2}[-]{1}\\d{2}")) {
                        item.setID(Integer.valueOf(inventoryAddID.getText()));
                        item.setType(inventoryAddType.getText());
                        item.setRetireDate(inventoryAddRetire.getText());
                        item.setPrice(Double.valueOf(inventoryAddPrice.getText()));
                        inventoryObservableList.add(item);
                        InventoryTableDAO.insert(item);
                        filteredListSource.add(item);
                        inventoryIDField.setText("");
                        inventoryPriceField.setText("");
                        inventoryRetireField.setText("");
                        inventoryTypeField.setText("");
                    }
                }
            }
        });
        deleteItem.setOnAction(e -> {
            Item temp = viewInventoryTable.getSelectionModel().getSelectedItem();
            filteredListSource.remove(temp);
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
        inventoryExport.setOnAction(e -> Reports.inventoryReport(inventoryObservableList));
        inventoryIDField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                inventoryIDField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        inventoryAddID.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                inventoryAddID.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
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

        DoubleStringConverter priceConverter = new DoubleStringConverter();

        inventoryIDColumn.setCellValueFactory(new PropertyValueFactory("ID"));
        inventoryTypeColumn.setCellValueFactory(new PropertyValueFactory("type"));
        inventoryRetireColumn.setCellValueFactory(new PropertyValueFactory("retireDate"));
        inventoryPriceColumn.setCellValueFactory(new PropertyValueFactory("price"));

        if (rockWallManagementApp.getAccessLevel() == RockWallManagementApp.Role.MANAGER || rockWallManagementApp.getAccessLevel() == RockWallManagementApp.Role.ADMINISTRATOR) {
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

            inventoryPriceColumn.setCellFactory(TextFieldTableCell.forTableColumn(priceConverter));
            inventoryPriceColumn.setOnEditCommit((TableColumn.CellEditEvent<Item, Double> event) -> {
                Item temp = event.getTableView().getItems().get(event.getTablePosition().getRow());
                temp.setPrice(event.getNewValue());
                event.getTableView().getItems().set(event.getTablePosition().getRow(), temp);
                InventoryTableDAO.update(temp);
            });
        }

        viewInventoryTable.setItems(inventoryObservableList);
    }

    private boolean filter(Item item) {
        boolean id;
        boolean type;
        boolean retireDate;
        boolean price;

        id = inventoryIDField.getText().isEmpty() || Integer.toString(item.getID()).contains(inventoryIDField.getText());
        type = inventoryTypeField.getText().isEmpty() || item.getType().toLowerCase().contains(inventoryTypeField.getText().toLowerCase());
        retireDate = inventoryRetireField.getText().isEmpty() || item.getRetireDate().toLowerCase().contains(inventoryRetireField.getText().toLowerCase());
        price = inventoryPriceField.getText().isEmpty() || item.getPrice().toString().contains(inventoryPriceField.getText());

        return id && type && retireDate && price;
    }

}
