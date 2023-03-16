package dk.letbillet.presentation.controller;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons;
import dk.letbillet.presentation.model.RoleModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    public Button exitButton;

    @FXML
    public Button loginButton;

    @FXML
    public TextField usernameField;

    @FXML
    public PasswordField passwordField;

    private RoleModel roleModel;

    public LoginController() {
        roleModel = new RoleModel();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Text icon = GlyphsDude.createIcon(FontAwesomeIcons.CLOSE, "20pt");
            icon.setStyle("-fx-font-family: FontAwesome; -fx-fill: RED;");

            exitButton.setGraphic(icon);
            exitButton.setText("");
        } catch (Exception ignored) { }

        usernameField.textProperty().addListener(((observable, oldValue, newValue) -> setLoginButtonEnableState()));
        passwordField.textProperty().addListener(((observable, oldValue, newValue) -> setLoginButtonEnableState()));
    }

    private void setLoginButtonEnableState() {
        boolean usernameOk = usernameField.textProperty().getValue().length() > 0;
        boolean passwordOk = passwordField.textProperty().getValue().length() > 1;

        loginButton.setDisable(!(usernameOk && passwordOk));
    }

    public void handleExit() {
        System.exit(0);
    }

}
