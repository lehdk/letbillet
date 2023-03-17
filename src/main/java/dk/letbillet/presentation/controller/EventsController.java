package dk.letbillet.presentation.controller;

import dk.letbillet.entity.Event;
import dk.letbillet.presentation.model.EventModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class EventsController implements Initializable {

    private final EventModel eventModel;

    @FXML
    public TableView<Event> tableView;
    @FXML
    private TableColumn<Event, String> eventColumn;
    @FXML
    private TableColumn<Event, String> startColumn;
    @FXML
    private TableColumn<Event, String> locationColumn;
    @FXML
    private TableColumn<Event, String> ticketsSoldColumn;

    public EventsController() {
        eventModel = new EventModel();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        var events = eventModel.getEventObservableList();
        eventColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        startColumn.setCellValueFactory(new PropertyValueFactory<>("Start"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("Location"));
        ticketsSoldColumn.setCellValueFactory(new PropertyValueFactory<>("TicketsSold"));
        tableView.setItems(events);
    }
}
