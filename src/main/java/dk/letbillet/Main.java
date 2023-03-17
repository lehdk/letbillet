package dk.letbillet;

import dk.letbillet.database.DatabaseConnectionHandler;
import dk.letbillet.util.PropertyLoader;
import dk.letbillet.util.PropertyResult;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage stage) throws Exception {

        PropertyResult properties;
        try {
            properties = PropertyLoader.loadProperties();
            if(properties == null) {
                System.exit(-1);
            }
            DatabaseConnectionHandler.initialize(properties);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }

        var loader = new FXMLLoader(getClass().getResource("presentation/view/Login.fxml"));

        Parent root = loader.load();
        root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
        var scene = new Scene(root);

        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(false);
        stage.show();
    }
}