package dk.letbillet.database;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import dk.letbillet.util.PropertyResult;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnectionHandler {

    private static DatabaseConnectionHandler instance = null;

    private final SQLServerDataSource source;

    private DatabaseConnectionHandler(PropertyResult property) {
        source = new SQLServerDataSource();
        source.setServerName(property.getDbIp());
        source.setPortNumber(property.getDbPort());
        source.setUser(property.getDbUsername());
        source.setPassword(property.getDbPassword());
        source.setDatabaseName(property.getDbName());
        source.setTrustServerCertificate(true);

        if(!isConnectionWorking()) {
            System.err.println("Could not connect to database!");
            System.exit(-1);
        }

        System.out.println("Connection made to database!");
    }

    public boolean isConnectionWorking() {
        try {
            if (getConnection() != null && !getConnection().isClosed()) {
                return true;
            } else {
                System.err.println("No connection to database!");
            }
        } catch (SQLException e) {
            System.err.println("No connection to database!");
        }
        return false;
    }

    public static void initialize(PropertyResult property) {
        if(instance == null) {
            instance = new DatabaseConnectionHandler(property);
        } else {
            System.err.println("DatabaseConnectionHandler has already been initialized!");
        }
    }

    public Connection getConnection() throws SQLServerException {
        return source.getConnection();
    }

    public static DatabaseConnectionHandler getInstance() {
        if(instance == null) {
            System.err.println("DatabaseConnectionHandler has not been initialized yet!");
            System.exit(-1);
        }

        return instance;
    }
}
