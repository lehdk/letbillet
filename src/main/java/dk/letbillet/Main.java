package dk.letbillet;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        var loader = new FXMLLoader(getClass().getResource("presentation/view/Application.fxml"));

        Parent root = loader.load();
        var scene = new Scene(root, 1280, 720);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}