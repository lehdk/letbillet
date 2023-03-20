package dk.letbillet.util;

import dk.letbillet.Main;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class LogoLoader {

    public static void addLogoToStage(Stage stage) {
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("img/letbillet.logo.png")));
    }
}
