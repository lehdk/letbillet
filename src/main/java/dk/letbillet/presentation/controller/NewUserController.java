package dk.letbillet.presentation.controller;

import dk.letbillet.entity.Role;
import dk.letbillet.entity.UserDTO;
import dk.letbillet.presentation.model.UserModel;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class NewUserController implements Initializable {
    public PasswordField passwordField;
    public TextField usernameField;
    public Button cancelButton;
    public Button createUserButton;
    public Label badUsernameOrPasswordLabel;
    public PasswordField passwordRepeat;

    private UserModel userModel;

    public void handleCloseWindow() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void handleCreateUser() throws SQLException {
        String username = usernameField.textProperty().getValue();
        String password = passwordField.textProperty().getValue();
        UserDTO user = new UserDTO(username, password, new Role(2, "Event Coordinator"));
        userModel.createUser(user);
        handleCloseWindow();
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        usernameField.textProperty().addListener(((observable, oldValue, newValue) -> setNewUserEnableState()));
        passwordField.textProperty().addListener(((observable, oldValue, newValue) -> setNewUserEnableState()));
        passwordRepeat.textProperty().addListener(((observable, oldValue, newValue) -> setNewUserEnableState()));

        setNewUserEnableState();
    }

    private void setNewUserEnableState() {
        boolean usernameOk = usernameField.textProperty().getValue().length() > 1;
        boolean passwordOk = passwordField.textProperty().getValue().length() > 3;
        boolean matchPassword = passwordField.textProperty().getValue().equals(passwordRepeat.textProperty().getValue());

        createUserButton.setDisable(!(usernameOk && passwordOk && matchPassword));
    }
}
