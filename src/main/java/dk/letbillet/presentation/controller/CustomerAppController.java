package dk.letbillet.presentation.controller;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons;
import dk.letbillet.entity.Event;
import dk.letbillet.presentation.model.EventModel;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class CustomerAppController implements Initializable {
    private final EventModel eventModel;
    public Button minimizeButton;
    public Button exitButton;
    public Label windowTitle;
    public TableColumn<Event, String> eventColumn;
    public TableColumn<Event, String> startColumn;
    public TableColumn<Event, String> locationColumn;
    public TableColumn<Event, String> priceColumn;
    public TableView tableView;

    public CustomerAppController() {
        eventModel = new EventModel();
    }

    public void minimizeApplication() {
        Stage stage = (Stage) minimizeButton.getScene().getWindow();
        stage.setIconified(true);
    }

    public void exitApplication() {
        System.exit(0);
    }

    private void setButtonIcon(Text icon, Button button) {
        button.setContentDisplay(ContentDisplay.LEFT);
        button.setGraphicTextGap(10);
        button.setGraphic(icon);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        var events = eventModel.getEventObservableList();
        eventColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        startColumn.setCellValueFactory(new PropertyValueFactory<>("Start"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("Location"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("Price"));
        tableView.setItems(events);

        try {
            Text exitIcon = GlyphsDude.createIcon(FontAwesomeIcons.CLOSE, "25pt");
            exitIcon.setStyle("-fx-font-family: FontAwesome; -fx-fill: RED;");
            exitButton.setGraphic(exitIcon);
            exitButton.setText("");
            exitButton.getStyleClass().add("noDecoration");

            Text minimizeIcon = GlyphsDude.createIcon(FontAwesomeIcons.MINUS, "25pt");
            minimizeIcon.setStyle("-fx-font-family: FontAwesome; -fx-fill: GRAY;");
            minimizeButton.setGraphic(minimizeIcon);
            minimizeButton.setText("");
            minimizeButton.getStyleClass().add("noDecoration");
        } catch (Exception ignored) { }
    }
}
