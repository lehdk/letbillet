package dk.letbillet.presentation.controller;

import dk.letbillet.entity.Event;
import dk.letbillet.entity.EventDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class NewEventController implements Initializable {

    @FXML
    public TextArea notesTextField;
    @FXML
    public TextField nameTextField;
    @FXML
    public TextField locationTextField;
    @FXML
    public TextField priceTextField;
    @FXML
    public DatePicker startDatePicker;
    @FXML
    public DatePicker endDatePicker;
    @FXML
    private EventDTO result;
    @FXML
    public Button createButton;
    @FXML
    public Button cancelButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nameTextField.textProperty().addListener(((observable, oldValue, newValue) -> checkCreateDisable()));
        startDatePicker.valueProperty().addListener(((observable, oldValue, newValue) -> checkCreateDisable()));
        endDatePicker.valueProperty().addListener(((observable, oldValue, newValue) -> checkCreateDisable()));
        locationTextField.textProperty().addListener(((observable, oldValue, newValue) -> checkCreateDisable()));
        priceTextField.textProperty().addListener(((observable, oldValue, newValue) -> {
            if(!newValue.matches("\\d*")) priceTextField.textProperty().setValue(oldValue); // Only allows numbers
            else checkCreateDisable(); // Only check if needed
        }));
    }

    public void handleCancelButton() {
        result = null;
        Stage popupStage = (Stage) cancelButton.getScene().getWindow();
        popupStage.close();
    }

    public void handleCreateButton() {
        String notes = null;
        if(notesTextField.textProperty().getValue().trim().length() > 0) notes = notesTextField.textProperty().getValue().trim();

        int price = 0;
        try {
            price = Integer.parseInt(priceTextField.textProperty().getValue());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // TODO: Make datetime picker!
        result = new EventDTO(
                nameTextField.textProperty().getValue(),
                locationTextField.textProperty().getValue(),
                notes,
                new Timestamp(2023, 3, 19, 0, 0, 0, 0),
                null,
                price
        );

        Stage popupStage = (Stage) cancelButton.getScene().getWindow();
        popupStage.close();
    }

    private void checkCreateDisable() {
        boolean nameOk = nameTextField.textProperty().getValue().length() > 0;
        boolean startOk = startDatePicker.valueProperty().get() != null;
        // TODO:  check if end is after start
        boolean locationOk = locationTextField.textProperty().getValue().length() > 0;
        boolean priceOk = priceTextField.textProperty().getValue().length() > 0;

        createButton.setDisable(!(nameOk && startOk && locationOk && priceOk));
    }

    public EventDTO getResult() {
        return result;
    }

}
