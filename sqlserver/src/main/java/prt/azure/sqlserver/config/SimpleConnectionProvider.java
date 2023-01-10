package prt.azure.sqlserver.config;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class SimpleConnectionProvider {

    private static Properties dbProperties;

    private static final String connectionUrlSeperator = ";";

    static {
        try (FileReader reader = new FileReader("db.properties")) {
            dbProperties = new Properties();
            dbProperties.load(reader);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {

        String jdbcUrl = dbProperties.getProperty("jdbcUrl");
        String connectionUrl = jdbcUrl.endsWith(connectionUrlSeperator) ?
                jdbcUrl + getCredentialSubString(dbProperties) + connectionUrlSeperator :
                jdbcUrl + connectionUrlSeperator + getCredentialSubString(dbProperties) + connectionUrlSeperator;
        System.out.println("connection String:" + connectionUrl);
        return DriverManager.getConnection(connectionUrl);
    }

    private static String getCredentialSubString(Properties dbProperties) {
        return "user=" + dbProperties.getProperty("username") + connectionUrlSeperator
                + "password=" + dbProperties.getProperty("password");
    }

}
