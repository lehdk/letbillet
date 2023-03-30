package dk.letbillet.presentation.controller;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import dk.letbillet.Main;
import dk.letbillet.entity.Event;
import dk.letbillet.entity.EventDTO;
import dk.letbillet.entity.Role;
import dk.letbillet.entity.User;
import dk.letbillet.presentation.model.UserModel;
import dk.letbillet.util.EventLoader;
import dk.letbillet.util.LogoLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UserController implements Initializable {

    public Button deleteUserButton;
    private UserModel userModel;
    @FXML
    public TableColumn<User, String> usernameColumn;
    @FXML
    public TableColumn<User, String> roleColumn;
    @FXML
    private TableView<User> userTableView;
    @FXML
    public TextField searchField;
    @FXML
    private Button newUserButton;

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
        var x = userModel.getUserObservableList();
        userTableView.setItems(x);
    }

    public void handleNewUser(ActionEvent actionEvent) throws IOException {
        Stage popupStage = new Stage();

        FXMLLoader loader = new FXMLLoader(Main.class.getResource("presentation/view/NewUser.fxml"));
        Parent root = loader.load();
        NewUserController controller = loader.getController();
        controller.setUserModel(userModel);
        Scene popupScene = new Scene(root);

        popupStage.setScene(popupScene);
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.initStyle(StageStyle.UNDECORATED);

        LogoLoader.addLogoToStage(popupStage);

        popupStage.showAndWait();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("Username"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("Role"));
    }

    public void handleDeleteUser() {
        User selectedUser = userTableView.getSelectionModel().getSelectedItem();
        try {
            userModel.deleteUser(selectedUser);
            userModel.getUserObservableList().remove(selectedUser);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
