package dk.letbillet.presentation.controller;

import dk.letbillet.entity.Event;
import dk.letbillet.presentation.model.EventModel;
import dk.letbillet.services.UserService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ViewEventController implements Initializable {

    @FXML
    public Label eventNameLabel;
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

    private Event currentEvent;

    private EventModel eventModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(UserService.getInstance().getLoggedInUser().getRole().getRoleName().equals("Admin")) {
            ticketButton.setDisable(true);
            editButton.setDisable(true);
        }
    }

    public void setEventModel(EventModel model) {
        eventModel = model;
    }

    public void setCurrentEvent(Event event) {
        currentEvent = event;

        eventNameLabel.textProperty().setValue(event.getName());
        locationLabel.textProperty().setValue(event.getLocation());
        startsAtLabel.textProperty().setValue(event.getStart().toString());

        if(event.getEnd() == null) {
            endsAtLabel.textProperty().setValue("No end date set");
            endsAtLabel.getStyleClass().add("grayed");
        } else {
            endsAtLabel.textProperty().setValue(event.getEnd().toString());
        }

        if(event.getPrice() == 0) {
            priceLabel.textProperty().setValue("Free");
            priceLabel.getStyleClass().add("grayed");
        } else {
            priceLabel.textProperty().setValue(event.getPrice() + "");
        }

        if(event.getTicketsSold() == 0) {
            ticketsSoldLabel.textProperty().setValue("No tickets sold");
            ticketsSoldLabel.getStyleClass().add("grayed");
        } else {
            ticketsSoldLabel.textProperty().setValue(event.getTicketsSold() + "");
        }

        if(event.getNotes() == null || event.getNotes().isEmpty()) {
            notesLabel.textProperty().setValue("No notes");
            notesLabel.getStyleClass().add("grayed");
        } else {
            notesLabel.textProperty().setValue(event.getNotes());
        }
    }

    public void handleCloseButton() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    public void handleDeleteEvent() {
        try {
            eventModel.deleteEvent(currentEvent);
            handleCloseButton();
        } catch (SQLException e) {
            System.out.println("Could not delete event!");
        }
    }
}