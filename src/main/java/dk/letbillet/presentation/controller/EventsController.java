package dk.letbillet.presentation.controller;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons;
import dk.letbillet.Main;
import dk.letbillet.entity.Event;
import dk.letbillet.entity.EventDTO;
import dk.letbillet.presentation.model.EventModel;
import dk.letbillet.services.UserService;
import dk.letbillet.util.LogoLoader;
import javafx.animation.PauseTransition;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

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
    private TableColumn<Event, String> priceColumn;
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
                try {
                    Event e = tableView.getSelectionModel().getSelectedItem();
                    if(e == null) return;

                    Stage popupStage = new Stage();

                    FXMLLoader loader = new FXMLLoader(Main.class.getResource("presentation/view/ViewEvent.fxml"));
                    Parent root = loader.load();
                    ViewEventController controller = loader.getController();
                    controller.setCurrentEvent(e);

                    Scene popupScene = new Scene(root);

                    popupStage.setScene(popupScene);
                    popupStage.initModality(Modality.APPLICATION_MODAL);
                    popupStage.initStyle(StageStyle.UNDECORATED);

                    LogoLoader.addLogoToStage(popupStage);

                    popupStage.showAndWait();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        var events = eventModel.getEventObservableList();
        eventColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        startColumn.setCellValueFactory(new PropertyValueFactory<>("Start"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("Location"));
        ticketsSoldColumn.setCellValueFactory(new PropertyValueFactory<>("TicketsSold"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("Price"));
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

        // Set what you are able to do depending on what role you have
        int userRole = UserService.getInstance().getLoggedInUser().getRole().getId();
        if(userRole == 1) {
            newEventButton.setDisable(true);
        }


    }

    public void handleNewEvent() throws IOException {
        Stage popupStage = new Stage();

        FXMLLoader loader = new FXMLLoader(Main.class.getResource("presentation/view/NewEvent.fxml"));
        Parent root = loader.load();
        NewEventController controller = loader.getController();
        Scene popupScene = new Scene(root);

        popupStage.setScene(popupScene);
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.initStyle(StageStyle.UNDECORATED);

        LogoLoader.addLogoToStage(popupStage);

        popupStage.showAndWait();

        EventDTO result = controller.getResult();
        if(result == null) return; // The cancel button has been pressed

        try {
            eventModel.createEvent(result);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
