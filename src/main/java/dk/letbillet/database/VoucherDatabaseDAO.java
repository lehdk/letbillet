package dk.letbillet.database;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import dk.letbillet.entity.VoucherType;

import java.sql.*;
import java.util.ArrayList;

public class VoucherDatabaseDAO {

    public ArrayList<VoucherType> getAllVoucherTypes() {
        var voucherTypes = new ArrayList<VoucherType>();

        try(Connection connection = DatabaseConnectionHandler.getInstance().getConnection()) {
            String sql = "SELECT [Id], [Name] FROM [VoucherTypes];";

            PreparedStatement statement = connection.prepareStatement(sql);

            var resultSet = statement.executeQuery();

            while(resultSet.next()) {
                voucherTypes.add(new VoucherType(
                        resultSet.getInt("Id"),
                        resultSet.getString("Name")
                    )
                );
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return voucherTypes;
    }

    public VoucherType createVoucherType(String name) throws SQLException {
        if(name == null || name.length() < 2) return null;

        try(Connection connection = DatabaseConnectionHandler.getInstance().getConnection()) {
            String sql = "INSERT INTO [VoucherTypes] ([Name]) VALUES (?);";

            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, name);

            statement.executeUpdate();

            try(ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if(generatedKeys.next()) {
                    return new VoucherType(generatedKeys.getInt(1), name);
                }
            }
        }

        return null;
    }
}
