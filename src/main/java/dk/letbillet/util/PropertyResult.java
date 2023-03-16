package dk.letbillet.util;

public class PropertyResult {

    private final String dbIp;
    private int dbPort;
    private final String dbName;
    private final String dbUsername;
    private final String dbPassword;

    public PropertyResult(String ip, String port, String dbName, String dbUsername, String dbPassword) {
        this.dbIp = ip;
        try {
            this.dbPort = Integer.parseInt(port);
        } catch (Exception e) {
            System.err.println("could not parse DB_PORT to a valid ip!");
        }
        this.dbName = dbName;
        this.dbUsername = dbUsername;
        this.dbPassword = dbPassword;
    }

    public String getDbIp() {
        return dbIp;
    }

    public int getDbPort() {
        return dbPort;
    }

    public String getDbName() {
        return dbName;
    }

    public String getDbUsername() {
        return dbUsername;
    }

    public String getDbPassword() {
        return dbPassword;
    }
}