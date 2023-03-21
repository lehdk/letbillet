package dk.letbillet.presentation.controller;

import dk.letbillet.entity.Event;
import dk.letbillet.services.UserService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewEventController implements Initializable {

    @FXML
    public Label locationLabel;
    @FXML
    public Label startsAtLabel;
    @FXML
    public Label endsAtLabel;
    @FXML
    public Label priceLabel;
    @FXML
    public Label ticketsSoldLabel;
    @FXML
    public Label notesLabel;
    @FXML
    public Button closeButton;
    @FXML
    public Button ticketButton;
    @FXML
    public Button deleteButton;
    @FXML
    public Button editButton;
    @FXML
    private Event currentEvent;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(UserService.getInstance().getLoggedInUser().getRole().getRoleName().equals("Admin")) {
            ticketButton.setDisable(true);
            editButton.setDisable(true);
        }
    }

    public void setCurrentEvent(Event event) {
        currentEvent = event;

        locationLabel.textProperty().setValue(event.getLocation());
        startsAtLabel.textProperty().setValue(event.getStart().toString());
        endsAtLabel.textProperty().setValue((event.getEnd() == null) ? "" : event.getEnd().toString());
        priceLabel.textProperty().setValue(event.getPrice() + "");
        ticketsSoldLabel.textProperty().setValue(event.getTicketsSold() + "");
        notesLabel.textProperty().setValue(event.getNotes());
    }

    public void handleCloseButton() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}