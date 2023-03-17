package dk.letbillet.database;

import dk.letbillet.entity.Role;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RoleDatabaseDAO {

    public List<Role> getAllRoles() {
        try(Connection connection = DatabaseConnectionHandler.getInstance().getConnection()) {
            String sql = "SELECT * FROM [Role];";

            Statement statement = connection.createStatement();

            var resultSet = statement.executeQuery(sql);

            var resultList = new ArrayList<Role>();

            while(resultSet.next()) {
                resultList.add(new Role(
                    resultSet.getInt("Id"),
                    resultSet.getString("Name")
                ));
            }

            return resultList;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
