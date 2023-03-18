package dk.letbillet.presentation.controller;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons;
import dk.letbillet.entity.Event;
import dk.letbillet.presentation.model.EventModel;
import javafx.animation.PauseTransition;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Filter;

public class EventsController implements Initializable {

    private final EventModel eventModel;

    @FXML
    public TableView<Event> tableView;
    @FXML
    public Button newEventButton;
    @FXML
    public TextField searchField;
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

        // Set data for tableview
        tableView.setOnMouseClicked(event -> {
            if(event.getClickCount() == 2) {
                Event e = tableView.getSelectionModel().getSelectedItem();
                System.out.println("Open: " + e);
            }
        });

        var events = eventModel.getEventObservableList();
        eventColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        startColumn.setCellValueFactory(new PropertyValueFactory<>("Start"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("Location"));
        ticketsSoldColumn.setCellValueFactory(new PropertyValueFactory<>("TicketsSold"));
        tableView.setItems(events);

        // Icon for new event button
        try {
            Text icon = GlyphsDude.createIcon(FontAwesomeIcons.FILE, "20pt");
            icon.setStyle("-fx-font-family: FontAwesome; -fx-fill: #03C988;");

            newEventButton.setContentDisplay(ContentDisplay.RIGHT);
            newEventButton.setGraphicTextGap(10);
            newEventButton.setGraphic(icon);
        } catch (Exception ignored) { }

        // Search field
        PauseTransition searchDelay = new PauseTransition(Duration.millis(150));
        searchField.textProperty().addListener(((observable, oldValue, newValue) -> {
            searchDelay.setOnFinished(e -> {
                String filterString = newValue.toLowerCase();
                FilteredList<Event> filteredList = new FilteredList<>(eventModel.getEventObservableList());
                filteredList.setPredicate(event -> event.getName().toLowerCase().contains(filterString)); // where the magic happens
                SortedList<Event> sortedList = new SortedList<>(filteredList);
                sortedList.comparatorProperty().bind(tableView.comparatorProperty());
                tableView.setItems(sortedList);
            });
            searchDelay.playFromStart();
        }));

    }
}
