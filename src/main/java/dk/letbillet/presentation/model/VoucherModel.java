package dk.letbillet.presentation.model;

import dk.letbillet.BLL.VoucherManager;
import dk.letbillet.entity.VoucherType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
}
