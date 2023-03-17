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
            String sql = "SELECT * FROM [User] WHERE [Username] = ?;";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);

            var resultSet = statement.executeQuery();

            var resultList = new ArrayList<Role>();

            if(resultSet.next()) {
                return new User(
                    resultSet.getInt("Id"),
                    resultSet.getString("Username"),
                    resultSet.getInt("Role"),
                    resultSet.getString("PasswordHash")
                );
            }

            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
