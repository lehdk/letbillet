package dk.letbillet.presentation.controller;

import dk.letbillet.Main;
import dk.letbillet.services.UserService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ApplicationController implements Initializable {
    @FXML
    public AnchorPane contentPane;

    @FXML
    public Label loggedInAsLabel;

    @FXML
    public Button eventButton;
    @FXML
    public Button userButton;
    @FXML
    public Button aboutButton;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        loggedInAsLabel.setText("Logged in as: " + UserService.getInstance().getLoggedInUser().getUsername());

        int userRole = UserService.getInstance().getLoggedInUser().getRole().getId();
        if(userRole == 2) {
           userButton.setDisable(true);
        }

        handleShowEvents();
    }

    private void loadContent(String filename) {
        try {
            Node content = FXMLLoader.load(Main.class.getResource("presentation/view/"+filename+".fxml"));
            //AnchorPane.setLeftAnchor(content, 0D);
            AnchorPane.setRightAnchor(content, 0D);
            AnchorPane.setTopAnchor(content, 0D);
            AnchorPane.setBottomAnchor(content, 0D);
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
