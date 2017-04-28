package PTS;

import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sqlite.SQLiteConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

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

            // gets data from dbInit.sql for fatjar, exclusive with below
            JarFile jarFile = new JarFile("RockWallManagement.jar");
            JarEntry entry = jarFile.getJarEntry("dbInit.sql");
            InputStream input = jarFile.getInputStream(entry);
            InputStreamReader isr = new InputStreamReader(input);
            BufferedReader reader = new BufferedReader(isr);
            String line;
            StringBuilder sqlBuilder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                sqlBuilder.append(line);
            }
            reader.close();

            String sql = sqlBuilder.toString();

            // gets data from dbInit.sql for running in IDE, exclusive with above
//            String sql = new String(Files.readAllBytes(Paths.get(DBInterface.class.getResource("/dbInit.sql").toURI())));


            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql.toString());
            stmt.closeOnCompletion();
        } catch ( IOException | SQLException /*| URISyntaxException*/ e) {
            log.error("Failed to initialize database file", e);
        }
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

    public static Path getExportPath(String filename) throws IOException {
        return Paths.get(System.getProperty("user.home"), "Desktop", filename);
    }

    public static Connection getConnection() throws SQLException {
        SQLiteConfig config = new SQLiteConfig();
        config.enforceForeignKeys(true);
        Connection conn =  DriverManager.getConnection(url, config.toProperties());
        conn.setAutoCommit(true);
        return conn;
    }
}
