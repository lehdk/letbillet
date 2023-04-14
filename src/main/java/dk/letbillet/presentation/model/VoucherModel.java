package dk.letbillet.presentation.model;

import dk.letbillet.BLL.VoucherManager;
import dk.letbillet.entity.VoucherType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class VoucherModel {

    private VoucherManager voucherManager;

    private ObservableList<VoucherType> voucherTypeObservableList;

    public VoucherModel() {
        voucherManager = new VoucherManager();

        voucherTypeObservableList = FXCollections.observableList(voucherManager.getAllVoucherTypes());
    }

    public ObservableList<VoucherType> getVoucherTypeObservableList() {
        return voucherTypeObservableList;
    }

    public VoucherType createVoucher(String name) throws SQLException {
        VoucherType result = voucherManager.createVoucher(name);

        if(result == null) return null;

        voucherTypeObservableList.add(result);

        return result;
    }
}
