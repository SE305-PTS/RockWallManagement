package PTS;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountTableDAO {
    private static final Logger log = LoggerFactory.getLogger(AccountTableDAO.class);

    public static void insert(Account item) {
        String query = "INSERT INTO Account(username,password,firstname,lastname,type) VALUES (?,?,?,?,?)";
        try {
            Connection conn = DriverManager.getConnection(DBInterface.getUrl());
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, item.getUsername());
            stmt.setString(2, item.getPassword());
            stmt.setString(3, item.getFirstName());
            stmt.setString(4, item.getLastName());
            stmt.setString(5, item.getType());
            stmt.executeUpdate();
            stmt.closeOnCompletion();
        } catch (SQLException e) {
            log.error("Could not insert Account into Database", e);
        }
    }

    public static void insert(Account array[]) {
        for (Account i : array) {
            insert(i);
        }
    }

    public static void delete(String username) {
        String query = "DELETE FROM Account WHERE username = ?";
        try {
            Connection conn = DriverManager.getConnection(DBInterface.getUrl());
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.executeUpdate();
            stmt.closeOnCompletion();
        } catch (SQLException e) {
            log.error("Could not delete Account from Database", e);
        }
    }

    public static void delete(String array[]) {
        for (String i : array) {
            delete(i);
        }
    }

    public static void update(Account item) {
        String query = "UPDATE Account Set password = ?, firstname = ?, lastname = ?, type = ? WHERE username = ?";
        try {
            Connection conn = DriverManager.getConnection(DBInterface.getUrl());
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, item.getPassword());
            stmt.setString(2, item.getFirstName());
            stmt.setString(3, item.getLastName());
            stmt.setString(4, item.getType());
            stmt.setString(5, item.getUsername());
            stmt.executeUpdate();
            stmt.closeOnCompletion();
        } catch (SQLException e) {
            log.error("Could not update Account in Database", e);
        }
    }

    public static void update(Account array[]) {
        for (Account i : array) {
            update(i);
        }
    }

    public static ObservableList<Account> selectAll() {
        String query = "SELECT username, password,firstname,lastname,type FROM Account";
        List<Account> list = new ArrayList<>();
        ObservableList<Account> observable;
        try {
            Connection conn = DriverManager.getConnection(DBInterface.getUrl());
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                Account current = new Account();
                current.setUsername(rs.getString("username"));
                current.setPassword(rs.getString("password"));
                current.setFirstName(rs.getString("firstname"));
                current.setLastName(rs.getString("lastname"));
                current.setType(rs.getString("type"));
                list.add(current);
            }
            stmt.closeOnCompletion();
            rs.close();
        } catch (SQLException e) {
            log.error("Could not select from Patron: ", e);
        }
        observable = FXCollections.observableArrayList(list);
        return observable;
    }

    public static Account getByUsername(String username) {
        String query = "SELECT username, password,firstname,lastname,type FROM Account WHERE username = ?";
        Account item = new Account();
        try {
            Connection conn = DriverManager.getConnection(DBInterface.getUrl());
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                item.setUsername(rs.getString("username"));
                item.setPassword(rs.getString("password"));
                item.setFirstName(rs.getString("firstname"));
                item.setLastName(rs.getString("lastname"));
                item.setType(rs.getString("type"));
            }
            stmt.closeOnCompletion();
            rs.close();
        } catch (SQLException e) {
            log.error("Could not update Patron in Database", e);
        }
        return item;
    }
}
