package dk.letbillet.database;

import dk.letbillet.entity.Event;
import dk.letbillet.entity.EventDTO;
import dk.letbillet.services.UserService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventDatabaseDAO {

    public List<Event> getAllEvents() {
        List<Event> events = new ArrayList<>();

        try(Connection connection = DatabaseConnectionHandler.getInstance().getConnection()) {
            String sql = "SELECT" +
                    "[Id]," +
                    "[Name]," +
                    "[Location]," +
                    "[Notes]," +
                    "[StartTime]," +
                    "[EndTime]," +
                    "[Price]," +
                    "(SELECT COUNT(*) FROM [Ticket] WHERE [EventId] = [Event].[Id]) AS 'TicketsSold'" +
                    "FROM [Event]" +
                    "ORDER BY [StartTime];";

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
                    resultSet.getInt("TicketsSold")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return events;
    }

    public Event createEvent(EventDTO event) throws SQLException {
        try(Connection connection = DatabaseConnectionHandler.getInstance().getConnection()) {
            String sql = "INSERT INTO [Event] ([Name], [Location], [StartTime], [EndTime], [Notes], [Price], [CreatedBy]) VALUES (?, ?, ?, ?, ?, ?, ?);";

            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, event.getName());
            statement.setString(2, event.getLocation());
            statement.setTimestamp(3, event.getStart());
            statement.setTimestamp(4, event.getEnd());
            statement.setString(5, event.getNotes());
            statement.setInt(6, event.getPrice());
            statement.setInt(7, UserService.getInstance().getLoggedInUser().getId());

            statement.executeUpdate();

            try(ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if(generatedKeys.next()) {
                    return event.convertToEvent(generatedKeys.getInt(1));
                }
            }
        }

        return null;
    }

    public boolean deleteEvent(Event event) throws SQLException {

        try(Connection connection = DatabaseConnectionHandler.getInstance().getConnection()) {
            String sql = "DELETE FROM [Event] WHERE [Id] = ?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, event.getId());

            int result = statement.executeUpdate();

            return (result != 0);
        }
    }

    public boolean editEvent(int eventId, EventDTO eventDTO) throws SQLException {
        try(Connection connection = DatabaseConnectionHandler.getInstance().getConnection()) {
            String sql = "UPDATE [Event] SET [Name] = ?, [Location] = ?, [Notes] = ?, [StartTime] = ?, [EndTime] = ?, Price = ? WHERE [Id] = ?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, eventDTO.getName());
            statement.setString(2, eventDTO.getLocation());
            statement.setString(3, eventDTO.getName());
            statement.setTimestamp(4, eventDTO.getStart());
            statement.setTimestamp(5, eventDTO.getEnd());
            statement.setInt(6, eventDTO.getPrice());
            statement.setInt(7, eventId);

            int result = statement.executeUpdate();

            return (result != 0);
        }
    }
}
