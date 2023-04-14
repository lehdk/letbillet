package dk.letbillet.presentation.controller.voucher;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NewVoucherTypeController {

    @FXML
    public TextField voucherNameField;

    private String result;

    public NewVoucherTypeController() {
        result = null;
    }

    public String getResult() {
        return result;
    }

    public void handleCancel() {
        result = null;
        closeWindow();
    }

    public void handleCreateVoucher() {
        result = voucherNameField.textProperty().getValue().trim();
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) voucherNameField.getScene().getWindow();
        stage.close();
    }
}
