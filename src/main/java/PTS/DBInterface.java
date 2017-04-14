package PTS;

import javafx.collections.ObservableList;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.SystemUtils;
import org.apache.commons.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.ConnectException;
import java.sql.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBInterface {
    private static final Logger log = LoggerFactory.getLogger(DBInterface.class);
    private static final String appDirName = "RockWallManagement";
    private static String url;

    public static String getUrl() {
        return url;
    }

    public static void init() {
        try {
            url = "jdbc:sqlite:" + Paths.get(getDataPath().toString(), "rockwall.db").toString();
            String sql = new String(Files.readAllBytes(Paths.get(DBInterface.class.getResource("/dbInit.sql").toURI())));
            Connection conn = DriverManager.getConnection(url);
            conn.setAutoCommit(true);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.closeOnCompletion();
        } catch (URISyntaxException | IOException | SQLException e) {
            log.error("Failed to initialize database file", e);
        }

        Patron pat = new Patron(7336666, "Mitchell", "Petit", "M", "mitchell.petit@jacks.sdstate.edu", false);
        pat.setSuspended("2017-10-09 12:24:32");
        PatronTableDAO.insert(pat);
        Item item = new Item();
        item.setID(1234);
        item.setType("Rope");
        item.setRetireDate("2017-06-01 12:25");
        item.setPrice(3.50);
        InventoryTableDAO.insert(item);
    }

    public static Path getDataPath() throws IOException {
        Path path;
        if (SystemUtils.IS_OS_WINDOWS) {
            path = Paths.get(System.getenv("AppData"), appDirName);
        } else if (SystemUtils.IS_OS_MAC_OSX) {
            path = Paths.get(System.getProperty("user.home"), "Library", "Application Support", appDirName);
        } else {
            path = Paths.get(System.getProperty("user.home"), "." + appDirName.toLowerCase());
        }
        if (!Files.exists(path)) {
            log.info("Path: '{}' does not exist, creating.", path);
            Files.createDirectories(path);
        }
        return path;
    }
}
