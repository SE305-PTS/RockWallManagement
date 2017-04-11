package PTS;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SessionTableDAO {
    private static final Logger log = LoggerFactory.getLogger(PatronTableDAO.class);

    public static void insert(Session item) {
        String query = "INSERT INTO Session(id,memberid,checkin,checkout) VALUES (?,?,?,?)";
        try {
            Connection conn = DriverManager.getConnection(DBInterface.getUrl());
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, item.getSessionID());
            stmt.setInt(2, item.getPatronID());
            stmt.setString(3, item.getCheckIn());
            stmt.setString(4, item.getCheckOut());
            stmt.executeUpdate();
            stmt.closeOnCompletion();
        } catch (SQLException e) {
            log.error("Could not insert Session into Database", e);
        }
    }

    public static void insert(Session array[]) {
        for (Session i : array) {
            insert(i);
        }
    }

    public static void delete(int id) {
        String query = "DELETE FROM Session WHERE id = ?";
        try {
            Connection conn = DriverManager.getConnection(DBInterface.getUrl());
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            stmt.executeUpdate();
            stmt.closeOnCompletion();
        } catch (SQLException e) {
            log.error("Could not delete Session from Database", e);
        }
    }

    public static void delete(int array[]) {
        for (int i : array) {
            delete(i);
        }
    }

    public static void update(Session item) {
        String query = "UPDATE Session Set memberid = ?, checkin = ?, checkout = ?";
        query = query + " WHERE id = ?";
        try {
            Connection conn = DriverManager.getConnection(DBInterface.getUrl());
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, item.getPatronID());
            stmt.setString(2, item.getCheckIn());
            stmt.setString(3, item.getCheckOut());
            stmt.setInt(4, item.getSessionID());
            stmt.executeUpdate();
            stmt.closeOnCompletion();
        } catch (SQLException e) {
            log.error("Could not update Session in Database", e);
        }
    }

    public static void update(Session array[]) {
        for (Session i : array) {
            update(i);
        }
    }

    public static ObservableList<Session> select() {
        String query = "SELECT id,memberid,checkin,checkout FROM Session";
        List<Session> list = new ArrayList<>();
        ObservableList<Session> observable;
        try {
            Connection conn = DriverManager.getConnection(DBInterface.getUrl());
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                Session current = new Session();
                current.setSessionID(rs.getInt("id"));
                current.setPatronID(rs.getInt("memberid"));
                current.setCheckIn(rs.getString("checkin"));
                current.setCheckOut(rs.getString("checkout"));
                list.add(current);
            }
            stmt.closeOnCompletion();
            rs.close();
        } catch (SQLException e) {
            log.error("Could not select from Session: ", e);
        }
        observable = FXCollections.observableArrayList(list);
        return observable;
    }

    public static ObservableList<Session> select(String id, String memberid, String checkin, String checkout) {
        String query = "SELECT iid,memberid,checkin,checkout FROM Session WHERE";
        if(id != null) query = query + " id=" + id + " and";
        if(memberid != null) query = query + " memberid=" + memberid + " and";
        if(checkin != null) query = query + " checkin=\"" + checkin + "\" and";
        if(checkout != null) query = query + " checkout=\"" + checkout + "\"";
        else query = query = query + " (checkout IS NULL or checkout IS NOT NULL)";
        List<Session> list = new ArrayList<>();
        ObservableList<Session> observable;
        try {
            Connection conn = DriverManager.getConnection(DBInterface.getUrl());
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                Session current = new Session();
                current.setSessionID(rs.getInt("id"));
                current.setPatronID(rs.getInt("memberid"));
                current.setCheckIn(rs.getString("checkin"));
                current.setCheckOut(rs.getString("checkout"));
                list.add(current);
            }
            stmt.closeOnCompletion();
            rs.close();
        } catch (SQLException e) {
            log.error("Could not select from Session: ", e);
        }
        observable = FXCollections.observableArrayList(list);
        return observable;
    }

    public static Session getByID(int id) {
        String query = "SELECT id,memberid,checkin,checkout FROM Session WHERE id = ?";
        Session current = new Session();
        try {
            Connection conn = DriverManager.getConnection(DBInterface.getUrl());
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                current.setSessionID(rs.getInt("id"));
                current.setPatronID(rs.getInt("memberid"));
                current.setCheckIn(rs.getString("checkin"));
                current.setCheckOut(rs.getString("checkout"));
            }
            stmt.closeOnCompletion();
            rs.close();
        } catch (SQLException e) {
            log.error("Could not find Session in Database: ", e);
        }
        return current;
    }
}
