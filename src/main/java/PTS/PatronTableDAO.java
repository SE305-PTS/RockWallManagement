package PTS;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.transform.Result;
import java.sql.*;

public class PatronTableDAO {
    private static final Logger log = LoggerFactory.getLogger(PatronTableDAO.class);

    public static void insert(Patron pat) {
        String query = "INSERT INTO Patron(id,firstname,lastname,gender,email,subscriber,belaycert,leadcert,suspension) VALUES (?,?,?,?,?,?,?,?,?)";
        try {
            Connection conn = DriverManager.getConnection(DBInterface.getUrl());
            conn.setAutoCommit(true);
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, pat.getID());
            stmt.setString(2, pat.getFirstName());
            stmt.setString(3, pat.getLastName());
            stmt.setString(4, pat.getGender());
            stmt.setString(5, pat.getEmailAddress());
            stmt.setBoolean(6, pat.getEmailOptIn());
            stmt.setBoolean(7, pat.getBelayCertified());
            stmt.setBoolean(8, pat.getLeadCertified());
            stmt.setString(9, pat.getSuspended());
            stmt.executeUpdate();
            stmt.closeOnCompletion();
        } catch (SQLException e) {
            log.error("Could not insert Patron into Database", e);
        }
    }

    public static void insert(Patron patrons[]) {
        for (Patron i : patrons) {
            insert(i);
        }
    }

    public static void delete(int id) {
        String query = "DELETE FROM Patron WHERE id = ?";
        try {
            Connection conn = DriverManager.getConnection(DBInterface.getUrl());
            conn.setAutoCommit(true);
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            stmt.executeUpdate();
            stmt.closeOnCompletion();
        } catch (SQLException e) {
            log.error("Could not delete Patron from Database", e);
        }
    }

    public static void delete(int id[]) {
        for (int i : id) {
            delete(i);
        }
    }

    public static void update(Patron pat) {
        String query = "UPDATE Patron Set firstname = ?, lastname = ?, gender = ?, email = ?, subscriber = ?, belaycert = ?, leadcert = ?, suspension = ?";
        query = query + " WHERE id = ?";
        try {
            Connection conn = DriverManager.getConnection(DBInterface.getUrl());
            conn.setAutoCommit(true);
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, pat.getFirstName());
            stmt.setString(2, pat.getLastName());
            stmt.setString(3, pat.getGender());
            stmt.setString(4, pat.getEmailAddress());
            stmt.setBoolean(5, pat.getEmailOptIn());
            stmt.setBoolean(6, pat.getBelayCertified());
            stmt.setBoolean(7, pat.getLeadCertified());
            stmt.setString(8, pat.getSuspended());
            stmt.setInt(9, pat.getID());
            stmt.executeUpdate();
            stmt.closeOnCompletion();
        } catch (SQLException e) {
            log.error("Could not update Patron in Database", e);
        }
    }

    public static void update(Patron patrons[]) {
        for (Patron i : patrons) {
            update(i);
        }
    }

    public static void selectAll() {
        String query = "SELECT id,firstname,lastname,gender,email,subscriber,belaycert,leadcert,suspension FROM Patron";
        try {
            Connection conn = DriverManager.getConnection(DBInterface.getUrl());
            conn.setAutoCommit(true);
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            stmt.closeOnCompletion();
            while(rs.next()) {
                log.info(String.valueOf(rs.getInt("id")));
            }
            rs.close();
        } catch (SQLException e) {
            log.error("Could not select from Patron: ", e);
        }
    }
}
