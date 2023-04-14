package dk.letbillet.presentation.model;

import dk.letbillet.BLL.VoucherManager;
import dk.letbillet.entity.IssuedVoucher;
import dk.letbillet.entity.VoucherType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;

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

    public VoucherType createVoucherType(String name) throws SQLException {
        VoucherType result = voucherManager.createVoucherType(name);

        if(result == null) return null;

        voucherTypeObservableList.add(result);

        return result;
    }

    public void issueVouchers(VoucherType selectedItem, int amount) throws SQLException {
        List<IssuedVoucher> vouchers = voucherManager.issueVouchers(selectedItem, amount);
    }
}
