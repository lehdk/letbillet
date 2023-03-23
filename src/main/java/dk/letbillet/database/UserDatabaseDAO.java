package dk.letbillet.database;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import dk.letbillet.entity.Event;
import dk.letbillet.entity.Role;
import dk.letbillet.entity.User;
import dk.letbillet.entity.UserDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDatabaseDAO {

    public User getUser(String username) {
        try(Connection connection = DatabaseConnectionHandler.getInstance().getConnection()) {
            String sql = "SELECT [User].[Id], [User].[Username], [User].[PasswordHash], [Role].[Id] as 'RoleId', [Role].[Name] as 'RoleName' FROM [User]\n" +
                    "JOIN [Role] on [User].[Role] = [Role].[Id]\n" +
                    "WHERE [Username]=?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);

            var resultSet = statement.executeQuery();

            if(resultSet.next()) {
                Role role = new Role(
                    resultSet.getInt("RoleId"),
                    resultSet.getString("RoleName")
                );

                User user = new User(
                    resultSet.getInt("Id"),
                    resultSet.getString("Username"),
                    role,
                    resultSet.getString("PasswordHash")
                );

                return user;
            }

            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try(Connection connection = DatabaseConnectionHandler.getInstance().getConnection()) {
            String sql = "SELECT [User].[Id], [User].[Username], [User].[PasswordHash], [Role].[Id] as 'RoleId', " +
                    "[Role].[Name] as 'RoleName' FROM [User]" +
                    "JOIN [Role] on [User].[Role] = [Role].[Id]";

            PreparedStatement statement = connection.prepareStatement(sql);
            var resultSet = statement.executeQuery();

            while(resultSet.next()) {
                Role role = new Role(
                        resultSet.getInt("RoleId"),
                        resultSet.getString("RoleName")
                );

                User user = new User(
                        resultSet.getInt("Id"),
                        resultSet.getString("Username"),
                        role,
                        resultSet.getString("PasswordHash")
                );
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return users;
    }

    public User createUser(UserDTO user) throws SQLException {
        if(user == null) return null;

        try (Connection connection = DatabaseConnectionHandler.getInstance().getConnection()) {
            String sql = "INSERT INTO [User] ([Username], [PasswordHash], [Role]) VALUES (?, ?, ?);";

            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setInt(3, user.getRole().getId());

            statement.executeUpdate();

            try(ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if(generatedKeys.next()) {
                    return user.convertToUser(generatedKeys.getInt(1));
                }
            }
        }

        return null;
    }


}
