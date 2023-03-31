package dk.letbillet.presentation.controller;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons;
import dk.letbillet.Main;
import dk.letbillet.presentation.model.UserModel;
import dk.letbillet.util.LogoLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    public Button customerButton;
    private double xOffset = 0; // Used for drag window
    private double yOffset = 0; // Used for drag window

    @FXML
    public Pane pane;

    @FXML
    public Button exitButton;

    @FXML
    public Button loginButton;

    @FXML
    public TextField usernameField;

    @FXML
    public PasswordField passwordField;

    @FXML
    public Label badUsernameOrPasswordLabel;

    private final UserModel userModel;

    public LoginController() {
        userModel = new UserModel();
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

        usernameField.setOnKeyPressed(event -> {
            if(event.getCode().equals(KeyCode.ENTER)) loginButton.fire();
        });

        passwordField.setOnKeyPressed(event -> {
            if(event.getCode().equals(KeyCode.ENTER)) loginButton.fire();
        });

        setLoginButtonEnableState();
    }

    private void setLoginButtonEnableState() {
        boolean usernameOk = usernameField.textProperty().getValue().length() > 0;
        boolean passwordOk = passwordField.textProperty().getValue().length() > 1;

        loginButton.setDisable(!(usernameOk && passwordOk));
    }

    public void handleExit() {
        System.exit(0);
    }

    public void handleLogin() {
        String username = usernameField.textProperty().getValue();
        String password = passwordField.textProperty().getValue();

        if(username.isEmpty() || password.isEmpty()) return;

        // Handle login here
        boolean success = userModel.logIn(username, password);
        badUsernameOrPasswordLabel.setVisible(!success);
        if(!success) return;

        try {
            var loader = new FXMLLoader(Main.class.getResource("presentation/view/Application.fxml"));
            Parent root1 = loader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Letbillet");
            stage.setScene(new Scene(root1));
            pane.getScene().getWindow().hide();

            root1.setOnMousePressed(event -> {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            });
            root1.setOnMouseDragged(event -> {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            });
            LogoLoader.addLogoToStage(stage);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void handleCustomerLogin() {
        try {
            var loader = new FXMLLoader(Main.class.getResource("presentation/view/CustomerApp.fxml"));
            Parent root1 = loader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Letbillet");
            stage.setScene(new Scene(root1));
            pane.getScene().getWindow().hide();

            root1.setOnMousePressed(event -> {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            });
            root1.setOnMouseDragged(event -> {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            });
            LogoLoader.addLogoToStage(stage);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
