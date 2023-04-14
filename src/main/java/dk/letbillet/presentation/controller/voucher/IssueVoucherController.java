package dk.letbillet.presentation.controller.voucher;

import dk.letbillet.entity.VoucherType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class IssueVoucherController implements Initializable {

    @FXML
    public Label voucherNameLabel;
    @FXML
    public TextField amountTextField;
    @FXML
    public Button createVoucherButton;

    private VoucherType voucherType;

    private int result;

    public IssueVoucherController() {
        result = 0;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        amountTextField.textProperty().addListener(((observable, oldValue, newValue) -> {
            if(!newValue.matches("\\d*")) {
                amountTextField.textProperty().setValue(oldValue); // Only allows numbers
            } else {
                if(newValue.length() > 1 && newValue.charAt(0) == '0') { // Remove 0 if number > 0
                    amountTextField.textProperty().setValue(newValue.substring(1));
                }
                checkCreateDisable(); // Only check if needed
            }
        }));

        checkCreateDisable();
    }

    public void init(VoucherType voucherType) {
        this.voucherType = voucherType;

        voucherNameLabel.setText(voucherType.getName());
    }

    public void handleCancel() {
        result = 0;
        closeWindow();
    }

    public void handleCreateVoucher() {

        try {
            result = Integer.parseInt(amountTextField.getText());
        } catch (NumberFormatException e) {
            System.out.println("Could not parse amount to number!");
            return;
        }

        if(result < 1) return;

        closeWindow();
    }

    private void closeWindow() {
        Stage popupStage = (Stage) voucherNameLabel.getScene().getWindow();
        popupStage.close();
    }

    public int getResult() {
        return result;
    }

    private void checkCreateDisable() {
        boolean isNumberOk = true;
        int amount = -1;

        try {
            amount = Integer.parseInt(amountTextField.getText());
            isNumberOk = amount > 0;
        } catch (NumberFormatException e) {
            isNumberOk = false;
        }

        createVoucherButton.setDisable(!isNumberOk);
    }
}
