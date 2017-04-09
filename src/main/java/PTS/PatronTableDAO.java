package PTS;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatronTableDAO {
    private static final Logger log = LoggerFactory.getLogger(PatronTableDAO.class);

    public static void insert(Patron item) {
        String query = "INSERT INTO Patron(id,firstname,lastname,gender,email,subscriber,belaycert,leadcert,suspension) VALUES (?,?,?,?,?,?,?,?,?)";
        try {
            Connection conn = DriverManager.getConnection(DBInterface.getUrl());
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, item.getID());
            stmt.setString(2, item.getFirstName());
            stmt.setString(3, item.getLastName());
            stmt.setString(4, item.getGender());
            stmt.setString(5, item.getEmailAddress());
            stmt.setBoolean(6, item.getEmailOptIn());
            stmt.setBoolean(7, item.getBelayCertified());
            stmt.setBoolean(8, item.getLeadCertified());
            stmt.setString(9, item.getSuspended());
            stmt.executeUpdate();
            stmt.closeOnCompletion();
        } catch (SQLException e) {
            log.error("Could not insert Patron into Database", e);
        }
    }

    public static void insert(Patron array[]) {
        for (Patron i : array) {
            insert(i);
        }
    }

    public static void delete(int id) {
        String query = "DELETE FROM Patron WHERE id = ?";
        try {
            Connection conn = DriverManager.getConnection(DBInterface.getUrl());
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            stmt.executeUpdate();
            stmt.closeOnCompletion();
        } catch (SQLException e) {
            log.error("Could not delete Patron from Database", e);
        }
    }

    public static void delete(int array[]) {
        for (int i : array) {
            delete(i);
        }
    }

    public static void update(Patron item) {
        String query = "UPDATE Patron Set firstname = ?, lastname = ?, gender = ?, email = ?, subscriber = ?, belaycert = ?, leadcert = ?, suspension = ?";
        query = query + " WHERE id = ?";
        try {
            Connection conn = DriverManager.getConnection(DBInterface.getUrl());
            conn.setAutoCommit(true);
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, item.getFirstName());
            stmt.setString(2, item.getLastName());
            stmt.setString(3, item.getGender());
            stmt.setString(4, item.getEmailAddress());
            stmt.setBoolean(5, item.getEmailOptIn());
            stmt.setBoolean(6, item.getBelayCertified());
            stmt.setBoolean(7, item.getLeadCertified());
            stmt.setString(8, item.getSuspended());
            stmt.setInt(9, item.getID());
            stmt.executeUpdate();
            stmt.closeOnCompletion();
        } catch (SQLException e) {
            log.error("Could not update Patron in Database", e);
        }
    }

    public static void update(Patron array[]) {
        for (Patron i : array) {
            update(i);
        }
    }

    public static ObservableList<Patron> select() {
        String query = "SELECT id,firstname,lastname,gender,email,subscriber,belaycert,leadcert,suspension FROM Patron";
        List<Patron> list = new ArrayList<>();
        ObservableList<Patron> observable;
        try {
            Connection conn = DriverManager.getConnection(DBInterface.getUrl());
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                Patron current = new Patron();
                current.setID(rs.getInt("id"));
                current.setFirstName(rs.getString("firstname"));
                current.setLastName(rs.getString("lastname"));
                current.setGender(rs.getString("gender"));
                current.setEmailAddress(rs.getString("email"));
                current.setEmailOptIn(rs.getBoolean("subscriber"));
                current.setBelayCertified(rs.getBoolean("belaycert"));
                current.setLeadCertified(rs.getBoolean("leadcert"));
                current.setSuspended(rs.getString("suspension"));
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

    public static ObservableList<Patron> select(String id, String firstname, String lastname, String belaycert, String leadcert, String suspended) {
        String query = "SELECT id,firstname,lastname,gender,email,subscriber,belaycert,leadcert,suspension FROM Patron WHERE";
        if(id != null) query = query + " id=" + id + " and";
        if(firstname != null) query = query + " firstname=\"" + firstname + "\" and";
        if(lastname != null) query = query + " lastname=\"" + lastname + "\" and";
        if(belaycert.equals("Y")) query = query + " belaycert=1 and";
        else if(belaycert.equals("N")) query = query + " belaycert=0 and";
        if(leadcert.equals("Y")) query = query + " leadcert=1 and";
        else if(leadcert.equals("N")) query = query + " leadcert=0 and";
        if(suspended.equals("Y")) query = query + " suspension>\"now\"";
        else if(suspended.equals("N")) query = query + " (suspension<\"now\" or suspension IS NULL)";
        else query = query = query + " (suspension IS NULL or suspension IS NOT NULL)";
        List<Patron> list = new ArrayList<>();
        ObservableList<Patron> observable;
        try {
            Connection conn = DriverManager.getConnection(DBInterface.getUrl());
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                Patron current = new Patron();
                current.setID(rs.getInt("id"));
                current.setFirstName(rs.getString("firstname"));
                current.setLastName(rs.getString("lastname"));
                current.setGender(rs.getString("gender"));
                current.setEmailAddress(rs.getString("email"));
                current.setEmailOptIn(rs.getBoolean("subscriber"));
                current.setBelayCertified(rs.getBoolean("belaycert"));
                current.setLeadCertified(rs.getBoolean("leadcert"));
                current.setSuspended(rs.getString("suspension"));
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

    public static Patron getByID(int id) {
        String query = "SELECT id,firstname,lastname,gender,email,subscriber,belaycert,leadcert,suspension FROM Patron";
        query = query + " WHERE id = ?";
        Patron pat = new Patron();
        try {
            Connection conn = DriverManager.getConnection(DBInterface.getUrl());
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                pat.setID(rs.getInt("id"));
                pat.setFirstName(rs.getString("firstname"));
                pat.setLastName(rs.getString("lastname"));
                pat.setGender(rs.getString("gender"));
                pat.setEmailAddress(rs.getString("email"));
                pat.setEmailOptIn(rs.getBoolean("subscriber"));
                pat.setBelayCertified(rs.getBoolean("belaycert"));
                pat.setLeadCertified(rs.getBoolean("leadcert"));
                pat.setSuspended(rs.getString("suspension"));
            }
            stmt.closeOnCompletion();
            rs.close();
        } catch (SQLException e) {
            log.error("Could not update Patron in Database", e);
        }
        return pat;
    }
}
