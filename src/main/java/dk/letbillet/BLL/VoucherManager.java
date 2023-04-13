package dk.letbillet.BLL;

import dk.letbillet.database.VoucherDatabaseDAO;
import dk.letbillet.entity.VoucherType;

import java.sql.SQLException;
import java.util.ArrayList;

public class VoucherManager {

    private VoucherDatabaseDAO databaseDAO;

    public VoucherManager() {
        databaseDAO = new VoucherDatabaseDAO();
    }

    public ArrayList<VoucherType> getAllVoucherTypes() {
        return databaseDAO.getAllVoucherTypes();
    }

}
