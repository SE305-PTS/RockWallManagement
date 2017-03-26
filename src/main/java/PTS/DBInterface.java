package PTS;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.lang3.SystemUtils;
import org.apache.commons.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
            String url = "jdbc:sqlite:" + Paths.get(getDataPath().toString(), "rockwall.db").toString();
            Connection conn = DriverManager.getConnection(url);
            conn.setAutoCommit(true);
            Statement stmt = conn.createStatement();
            String sql = new String(Files.readAllBytes(Paths.get(DBInterface.class.getResource("/dbInit.sql").toURI())));
            stmt.executeUpdate(sql);
            sql = "INSERT INTO Patron (id,firstname,lastname,gender,email,subscriber,belaycert,leadcert,suspension)"+
                    " VALUES (0,\"Zach\",\"Needham\",\"M\",NULL,1,1,0,NULL)";
            stmt.executeUpdate(sql);
            stmt.close();
            conn.close();
        } catch (SQLException | URISyntaxException | IOException e) {
            log.error("Failed to initialize database", e);
        }
    }
}
