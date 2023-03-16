package dk.letbillet.util;

import dk.letbillet.Main;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyLoader {

    public static PropertyResult loadProperties() throws IOException {

        var url = Main.class.getResource("config.properties");
        var properties = new Properties();

        if(url == null) {
            System.err.println("Could not read config.properties!");
            return null;
        }

        try(InputStream input = url.openStream()) {
            properties.load(input);
        }

        //Gets the DB Value from a config file
        String dbIp = properties.getProperty("DB_IP");
        String dbPort = properties.getProperty("DB_PORT");
        String dbName= properties.getProperty("DB_NAME");
        String dbUsername = properties.getProperty("DB_USERNAME");
        String dbPassword = properties.getProperty("DB_PASSWORD");

        return new PropertyResult(
            dbIp,
            dbPort,
            dbName,
            dbUsername,
            dbPassword
        );
   }
}

