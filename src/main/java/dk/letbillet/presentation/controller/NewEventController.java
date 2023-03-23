package dk.letbillet.presentation.controller;

import dk.letbillet.entity.Event;
import dk.letbillet.entity.EventDTO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
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
    public ChoiceBox<Integer> startHChoiceBox;
    @FXML
    public ChoiceBox<Integer> startMChoiceBox;
    @FXML
    public ChoiceBox<Integer> endHChoiceBox;
    @FXML
    public ChoiceBox<Integer> endMChoiceBox;
    @FXML
    public Label headline;
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
        startHChoiceBox.valueProperty().addListener(((observable, oldValue, newValue) -> checkCreateDisable()));
        startMChoiceBox.valueProperty().addListener(((observable, oldValue, newValue) -> checkCreateDisable()));

        endDatePicker.valueProperty().addListener(((observable, oldValue, newValue) -> checkCreateDisable()));
        endHChoiceBox.valueProperty().addListener(((observable, oldValue, newValue) -> checkCreateDisable()));
        endMChoiceBox.valueProperty().addListener(((observable, oldValue, newValue) -> checkCreateDisable()));

        locationTextField.textProperty().addListener(((observable, oldValue, newValue) -> checkCreateDisable()));
        priceTextField.textProperty().addListener(((observable, oldValue, newValue) -> {
            if(!newValue.matches("\\d*")) {
                priceTextField.textProperty().setValue(oldValue); // Only allows numbers
            } else {
                if(newValue.length() > 1 && newValue.charAt(0) == '0') { // Remove 0 if number > 0
                    priceTextField.textProperty().setValue(newValue.substring(1));
                }
                checkCreateDisable(); // Only check if needed
            }
        }));

        // Hour selectors
        endHChoiceBox.getItems().add(null);
        for (int hour = 0; hour < 24; hour++) {
            startHChoiceBox.getItems().add(hour);
            endHChoiceBox.getItems().add(hour);
        }
        startHChoiceBox.setValue(12);
        endHChoiceBox.setValue(null);

        // Minute selectors
        endMChoiceBox.getItems().add(null);
        for (int minute = 0; minute < 60; minute += 15) {
            startMChoiceBox.getItems().add(minute);
            endMChoiceBox.getItems().add(minute);
        }
        startMChoiceBox.setValue(0);
        endMChoiceBox.setValue(null);
    }

    public void editEvent(Event event) {
        result = new EventDTO(event.getName(), event.getLocation(), event.getNotes(), event.getStart(), event.getEnd(), event.getPrice());
        createButton.setText("Save");
        headline.setText("Edit event");

        nameTextField.setText(event.getName());

        var startDate = event.getStart();
        startDatePicker.setValue(LocalDate.of(startDate.getYear(), startDate.getMonth(), startDate.getDate()));
        startHChoiceBox.setValue(startDate.getHours());
        startMChoiceBox.setValue(startDate.getMinutes());

        var endDate = event.getEnd();
        if(endDate != null) {
            endDatePicker.setValue(LocalDate.of(endDate.getYear(), endDate.getMonth(), endDate.getDate()));
            endHChoiceBox.setValue(endDate.getHours());
            endMChoiceBox.setValue(endDate.getMinutes());
        }

        locationTextField.setText(event.getLocation());
        priceTextField.setText(event.getPrice() + "");
        notesTextField.setText(event.getNotes());
    }

    public void handleCancelButton() {
        result = null;
        Stage popupStage = (Stage) cancelButton.getScene().getWindow();
        popupStage.close();
    }

    public void handleSave() {
        String notes = null;

        if(notesTextField.textProperty().getValue() != null && notesTextField.textProperty().getValue().trim().length() > 0) {
            notes = notesTextField.textProperty().getValue().trim();
        }

        int price = 0;
        try {
            price = Integer.parseInt(priceTextField.textProperty().getValue());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Timestamp startTime = generateStartTimestamp();
        Timestamp endTime = null;
        if(endDatePicker.valueProperty().getValue() != null) {
            endTime = generateEndTimestamp();
        }

        result = new EventDTO(
                nameTextField.textProperty().getValue(),
                locationTextField.textProperty().getValue(),
                notes,
                startTime,
                endTime,
                price
        );

        Stage popupStage = (Stage) cancelButton.getScene().getWindow();
        popupStage.close();
    }

    private void checkCreateDisable() {
        boolean nameOk = nameTextField.textProperty().getValue().length() > 0;
        boolean startOk = startDatePicker.valueProperty().getValue() != null && startHChoiceBox.getValue() != null && startMChoiceBox.getValue() != null;
        boolean endOk = (endDatePicker.valueProperty().getValue() == null) || (endHChoiceBox.getValue() != null && endMChoiceBox.getValue() != null);

        Timestamp startTimestamp = generateStartTimestamp();
        Timestamp endTimestamp = generateEndTimestamp();
        boolean isStartBeforeEnd = false;
        if(startOk) isStartBeforeEnd = (endTimestamp == null) || (startTimestamp.before(endTimestamp));

        boolean locationOk = locationTextField.textProperty().getValue().length() > 0;
        boolean priceOk = priceTextField.textProperty().getValue().length() > 0;

        createButton.setDisable(!(nameOk && startOk && endOk && isStartBeforeEnd && locationOk && priceOk));
    }

    public EventDTO getResult() {
        return result;
    }

    private Timestamp generateStartTimestamp() {
        if(startDatePicker.getValue() == null || startHChoiceBox.getValue() == null || startMChoiceBox.getValue() == null) return null;
        return new Timestamp(startDatePicker.getValue().getYear() - 1900, startDatePicker.getValue().getMonthValue(), startDatePicker.getValue().getDayOfMonth(), startHChoiceBox.getValue(), startMChoiceBox.getValue(), 0, 0);
    }

    private Timestamp generateEndTimestamp() {
        if(endDatePicker.getValue() == null || endHChoiceBox.getValue() == null || endMChoiceBox.getValue() == null) return null;
        return new Timestamp(endDatePicker.getValue().getYear() - 1900, endDatePicker.getValue().getMonthValue(), endDatePicker.getValue().getDayOfMonth(), endHChoiceBox.getValue(), endMChoiceBox.getValue(), 0, 0);
    }
}
