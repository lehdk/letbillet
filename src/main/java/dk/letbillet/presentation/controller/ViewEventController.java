package dk.letbillet.presentation.controller;

import dk.letbillet.Main;
import dk.letbillet.entity.Event;
import dk.letbillet.entity.EventDTO;
import dk.letbillet.presentation.model.EventModel;
import dk.letbillet.services.UserService;
import dk.letbillet.util.LogoLoader;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
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
        updateEventShown();
    }

    private void updateEventShown() {
        eventNameLabel.textProperty().setValue(currentEvent.getName());
        locationLabel.textProperty().setValue(currentEvent.getLocation());
        startsAtLabel.textProperty().setValue(currentEvent.getStart().toString());

        if(currentEvent.getTicketsSold() != 0) {
            deleteButton.setDisable(true);
        }

        if(currentEvent.getEnd() == null) {
            endsAtLabel.textProperty().setValue("No end date set");
            endsAtLabel.getStyleClass().add("grayed");
        } else {
            endsAtLabel.textProperty().setValue(currentEvent.getEnd().toString());
        }

        if(currentEvent.getPrice() == 0) {
            priceLabel.textProperty().setValue("Free");
            priceLabel.getStyleClass().add("grayed");
        } else {
            priceLabel.textProperty().setValue(currentEvent.getPrice() + "");
        }

        if(currentEvent.getTicketsSold() == 0) {
            ticketsSoldLabel.textProperty().setValue("No tickets sold");
            ticketsSoldLabel.getStyleClass().add("grayed");
        } else {
            ticketsSoldLabel.textProperty().setValue(currentEvent.getTicketsSold() + "");
        }

        if(currentEvent.getNotes() == null || currentEvent.getNotes().isEmpty()) {
            notesLabel.textProperty().setValue("No notes");
            notesLabel.getStyleClass().add("grayed");
        } else {
            notesLabel.textProperty().setValue(currentEvent.getNotes());
        }
    }

    public void handleCloseButton() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    public void handleDeleteEvent() {
        if(currentEvent.getTicketsSold() != 0) {
            System.out.println("Can not delete event since tickets has been sold!");
            return;
        }

        try {
            eventModel.deleteEvent(currentEvent);
            handleCloseButton();
        } catch (SQLException e) {
            System.out.println("Could not delete event!");
        }
    }

    public void handleEditButton() throws IOException {
        Stage popupStage = new Stage();

        FXMLLoader loader = new FXMLLoader(Main.class.getResource("presentation/view/NewEvent.fxml"));
        Parent root = loader.load();
        NewEventController controller = loader.getController();
        controller.editEvent(currentEvent);
        Scene popupScene = new Scene(root);

        popupStage.setScene(popupScene);
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.initStyle(StageStyle.UNDECORATED);

        LogoLoader.addLogoToStage(popupStage);

        popupStage.showAndWait();

        EventDTO result = controller.getResult();
        if(result == null) return; // The cancel button has been pressed

        try {
            eventModel.editEvent(currentEvent.getId(), result);
            updateEventShown();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}