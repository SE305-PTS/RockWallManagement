package PTS;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InventoryTableDAO {
    private static final Logger log = LoggerFactory.getLogger(InventoryTableDAO.class);

    public static void insert(Item item) {
        String query = "INSERT INTO Inventory(id,type,purchasedate,retiredate,comments) VALUES (?,?,?,?,?)";
        try {
            Connection conn = DriverManager.getConnection(DBInterface.getUrl());
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, item.getID());
            stmt.setString(2, item.getType());
            stmt.setString(3, item.getPurchaseDate());
            stmt.setString(4, item.getRetireDate());
            stmt.setString(5, item.getNotes());
            stmt.executeUpdate();
            stmt.closeOnCompletion();
        } catch (SQLException e) {
            log.error("Could not insert Inventory Item into Database", e);
        }
    }

    public static void insert(Item array[]) {
        for (Item i : array) {
            insert(i);
        }
    }

    public static void delete(int id) {
        String query = "DELETE FROM Item WHERE id = ?";
        try {
            Connection conn = DriverManager.getConnection(DBInterface.getUrl());
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            stmt.executeUpdate();
            stmt.closeOnCompletion();
        } catch (SQLException e) {
            log.error("Could not delete Inventory Item from Database", e);
        }
    }

    public static void delete(int array[]) {
        for (int i : array) {
            delete(i);
        }
    }

    public static void update(Item item) {
        String query = "UPDATE Inventory Set type = ?, purchasedate = ?, retiredate = ?, comments = ? WHERE id = ?";
        try {
            Connection conn = DriverManager.getConnection(DBInterface.getUrl());
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, item.getType());
            stmt.setString(2, item.getPurchaseDate());
            stmt.setString(3, item.getRetireDate());
            stmt.setString(4, item.getNotes());
            stmt.setInt(5, item.getID());
            stmt.executeUpdate();
            stmt.closeOnCompletion();
        } catch (SQLException e) {
            log.error("Could not update Inventory Item in Database", e);
        }
    }

    public static void update(Item array[]) {
        for (Item i : array) {
            update(i);
        }
    }

    public static ObservableList<Item> select() {
        String query = "SELECT id,type,purchasedate,retiredate,comments FROM Item";
        List<Item> list = new ArrayList<>();
        ObservableList<Item> observable;
        try {
            Connection conn = DriverManager.getConnection(DBInterface.getUrl());
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                Item current = new Item();
                current.setID(rs.getInt("id"));
                current.setType(rs.getString("type"));
                current.setPurchaseDate(rs.getString("purchasedate"));
                current.setRetireDate(rs.getString("retiredate"));
                current.setNotes(rs.getString("comments"));
                list.add(current);
            }
            stmt.closeOnCompletion();
            rs.close();
        } catch (SQLException e) {
            log.error("Could not select from Inventory: ", e);
        }
        observable = FXCollections.observableArrayList(list);
        return observable;
    }

    public static ObservableList<Item> select(String id, String type, String purchasedate, String retiredate, String comments) {
        String query = "SELECT id,type,purchasedate,retiredate,comments FROM Item WHERE";
        if(id != null) query = query + " id=" + id + " and";
        if(type != null) query = query + " type=\"" + type + "\" and";
        if(purchasedate != null) query = query + " purchasedate=\"" + purchasedate + "\" and";
        if(retiredate != null) query = query + " retiredate=\"" + retiredate + "\" and";
        if(comments != null) query = query + " comments=\"" + comments + "\" and";
        else query = query = query + " (comments IS NULL or comments IS NOT NULL)";
        List<Item> list = new ArrayList<>();
        ObservableList<Item> observable;
        try {
            Connection conn = DriverManager.getConnection(DBInterface.getUrl());
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                Item current = new Item();
                current.setID(rs.getInt("id"));
                current.setType(rs.getString("type"));
                current.setPurchaseDate(rs.getString("purchasedate"));
                current.setRetireDate(rs.getString("retiredate"));
                current.setNotes(rs.getString("comments"));
                list.add(current);
            }
            stmt.closeOnCompletion();
            rs.close();
        } catch (SQLException e) {
            log.error("Could not select from Inventory: ", e);
        }
        observable = FXCollections.observableArrayList(list);
        return observable;
    }
}
