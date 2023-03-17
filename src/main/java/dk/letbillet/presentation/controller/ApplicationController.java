package dk.letbillet.presentation.controller;

import dk.letbillet.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ApplicationController implements Initializable {
    @FXML
    public Pane contentPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        handleShowEvents();
    }

    private void loadContent(String filename) {
        try {
            Node content = FXMLLoader.load(Main.class.getResource("presentation/view/"+filename+".fxml"));
            contentPane.getChildren().setAll(content);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void handleShowEvents() {
        loadContent("Events");
    }

    public void handleShowUsers() {
        loadContent("Users");
    }

    public void handleShowAbout() {
        loadContent("About");
    }
}
