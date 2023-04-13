package dk.letbillet.database;

import dk.letbillet.entity.VoucherType;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
}
