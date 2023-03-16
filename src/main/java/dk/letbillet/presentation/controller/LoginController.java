package dk.letbillet.presentation.controller;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    public Button exitButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Text icon = GlyphsDude.createIcon(FontAwesomeIcons.CLOSE, "20pt");
            icon.setStyle("-fx-font-family: FontAwesome; -fx-fill: RED;");

            exitButton.setGraphic(icon);
            exitButton.setText("");
        } catch (Exception ignored) {

        }

    }

    public void handleExit() {
        System.exit(0);
    }

}
