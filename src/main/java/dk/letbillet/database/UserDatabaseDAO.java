package dk.letbillet.database;

import dk.letbillet.entity.Role;
import dk.letbillet.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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
}
