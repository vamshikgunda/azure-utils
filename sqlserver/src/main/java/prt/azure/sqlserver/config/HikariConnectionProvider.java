package prt.azure.sqlserver.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class HikariConnectionProvider {

    private static Logger log = LoggerFactory.getLogger(HikariConnectionProvider.class);

    private static HikariConfig config = null;
    private static HikariDataSource ds;

    static {
        try (FileReader reader = new FileReader("db.properties")) {
            Properties dbProperties = new Properties();
            dbProperties.load(reader);
            config = new HikariConfig(dbProperties);
            ds = new HikariDataSource(config);
        } catch (IOException ex) {
            log.error("Exception occurred reading DB properties", ex);
        }
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

}
