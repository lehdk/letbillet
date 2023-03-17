package dk.letbillet.database;

import dk.letbillet.entity.Event;
import dk.letbillet.entity.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EventDatabaseDAO {

    public List<Event> getAllEvents() {
        List<Event> events = new ArrayList<>();

        try(Connection connection = DatabaseConnectionHandler.getInstance().getConnection()) {
            String sql = "SELECT * FROM [Event];";

            PreparedStatement statement = connection.prepareStatement(sql);
            var resultSet = statement.executeQuery();

            while(resultSet.next()) {
                events.add(new Event(
                    resultSet.getInt("Id"),
                    resultSet.getString("Name"),
                    resultSet.getString("Location"),
                    resultSet.getString("Notes"),
                    resultSet.getTimestamp("StartTime"),
                    resultSet.getTimestamp("EndTime"),
                    resultSet.getInt("Price"),
                    0
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return events;
    }
}
