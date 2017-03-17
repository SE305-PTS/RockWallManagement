package PTS;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBInterface {
    private static final Logger log = LoggerFactory.getLogger(DBInterface.class);
    private static final BasicDataSource pool = new BasicDataSource();
    private static final String appDirName = "RockWallManagement";

    static {
        try {
            pool.setDriverClassName("org.sqlite.JDBC");
            pool.setUrl("jdbc:sqlite:" + getDBStore());
            pool.setInitialSize(1);
        } catch (IOException e) {
            log.error("Failed to initialize database");
            System.exit(1);
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

    public static String getDBStore() throws IOException {
        return Paths.get(getDataPath().toString(), "index.sqlite").toString();
    }

    public static void init() {
        try {
            QueryRunner run = new QueryRunner(pool);
            Connection conn = DriverManager.getConnection(pool.getUrl());
            ResultSetHandler<Account> resultHandler = new BeanHandler<Account>(Account.class);
            String sql = new String(Files.readAllBytes(Paths.get(DBInterface.class.getResource("/dbInit.sql").toURI())));
            run.query(conn, sql, resultHandler);
            DbUtils.close(conn);
        } catch (SQLException | URISyntaxException | IOException e) {
            log.error("Failed to initialize DB", e);
        }
    }
}
