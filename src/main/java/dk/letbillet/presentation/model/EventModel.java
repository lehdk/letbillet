package dk.letbillet.presentation.model;

import dk.letbillet.BLL.EventManager;
import dk.letbillet.entity.Event;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class EventModel {

    private EventManager eventManager;

    private ObservableList<Event> eventObservableList;

    public EventModel() {
        eventManager = new EventManager();

        eventObservableList = FXCollections.observableList(eventManager.getAllEvents());
    }

    public ObservableList<Event> getEventObservableList() {
        return eventObservableList;
    }
}
