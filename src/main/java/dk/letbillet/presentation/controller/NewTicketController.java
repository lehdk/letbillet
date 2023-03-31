package dk.letbillet.presentation.controller;

import dk.letbillet.entity.Event;
import dk.letbillet.entity.TicketDTO;
import dk.letbillet.presentation.model.TicketModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NewTicketController {

    private TicketModel ticketModel;

    private Event currentEvent;

    private TicketDTO result;

    @FXML
    public TextField customerNameText;
    @FXML
    public TextField customerEmailText;
    @FXML
    public Label eventNameLabel;
    @FXML
    public TextField amountLabel;
    @FXML
    public Button cancelButton;
    @FXML
    public Button createButton;

    public void init(TicketModel ticketModel, Event event) {
        this.ticketModel = ticketModel;
        currentEvent = event;

        eventNameLabel.setText(event.getName());
    }

    public TicketDTO getResult() {
        return result;
    }

    public void handleCancel() {
        result = null;
        Stage popupStage = (Stage) cancelButton.getScene().getWindow();
        popupStage.close();
    }

    public void handleCreate() {
        result = new TicketDTO();
        result.setAmount(Integer.parseInt(amountLabel.textProperty().getValue()));
        result.setEvent(currentEvent);
        result.setCustomerName(customerNameText.textProperty().getValue());
        result.setCustomerEmail(customerEmailText.textProperty().getValue());

        Stage popupStage = (Stage) createButton.getScene().getWindow();
        popupStage.close();
    }
}
