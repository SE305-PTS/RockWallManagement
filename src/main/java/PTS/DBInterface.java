package PTS;

import org.apache.commons.dbcp2.BasicDataSource;
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
    private static Connection conn;

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

    public static void init() {
        try {
            url = "jdbc:sqlite:" + Paths.get(getDataPath().toString(), "rockwall.db").toString();
            String sql = new String(Files.readAllBytes(Paths.get(DBInterface.class.getResource("/dbInit.sql").toURI())));
            executeUpdate(sql);
        } catch (URISyntaxException | IOException e) {
            log.error("Failed to initialize database file", e);
        }
    }

    public static int executeUpdate(String sql) {
        int rc = -1;
        try {
            conn = DriverManager.getConnection(url);
            conn.setAutoCommit(true);
            Statement stmt = conn.createStatement();
            rc = stmt.executeUpdate(sql);
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            log.error("Could not execute the update query: ", e);
        }
        return rc;
    }

    public static ResultSet executeQuery(String sql) {
        try {
            conn = DriverManager.getConnection(url);
            conn.setAutoCommit(true);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            stmt.close();
            conn.close();
            return rs;
        } catch (SQLException e) {
            log.error("Could not execute the ResultSet producing query: ", e);
            return null;
        }
    }

    public static String BoolToString(boolean bool) {
        String out;
        if (bool) {
            out = "1";
        } else {
            out = "0";
        }
        return out;
    }
}
