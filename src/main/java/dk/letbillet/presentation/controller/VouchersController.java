package dk.letbillet.presentation.controller;

import dk.letbillet.entity.VoucherType;
import dk.letbillet.presentation.model.VoucherModel;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class VouchersController {

    private VoucherModel voucherModel;

    @FXML
    private ListView<VoucherType> listView;

    public void init(VoucherModel voucherModel) {
        this.voucherModel = voucherModel;

        listView.setItems(voucherModel.getVoucherTypeObservableList());
    }

    public void handleNewVoucher() {
        System.out.println("New voucher");
    }
}
