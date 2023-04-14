package dk.letbillet.presentation.controller;

import dk.letbillet.Main;
import dk.letbillet.entity.VoucherType;
import dk.letbillet.presentation.model.VoucherModel;
import dk.letbillet.util.LogoLoader;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.SQLException;

public class VouchersController {

    private VoucherModel voucherModel;

    @FXML
    private ListView<VoucherType> listView;

    public void init(VoucherModel voucherModel) {
        this.voucherModel = voucherModel;

        listView.setItems(voucherModel.getVoucherTypeObservableList());
    }

    public void handleNewVoucher() throws IOException {
        Stage popupStage = new Stage();

        FXMLLoader loader = new FXMLLoader(Main.class.getResource("presentation/view/vouchers/NewVoucherType.fxml"));
        Parent root = loader.load();
        NewVoucherTypeController controller = loader.getController();
        Scene popupScene = new Scene(root);

        popupStage.setScene(popupScene);
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.initStyle(StageStyle.UNDECORATED);

        LogoLoader.addLogoToStage(popupStage);

        popupStage.showAndWait();

        String result = controller.getResult();
        if(result == null) return; // The cancel button has been pressed

        try {
            voucherModel.createVoucher(result);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Could not create VoucherType!");
        }
    }
}
