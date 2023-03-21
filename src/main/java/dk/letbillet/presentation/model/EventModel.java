package dk.letbillet.presentation.model;

import dk.letbillet.BLL.EventManager;
import dk.letbillet.entity.Event;
import dk.letbillet.entity.EventDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class EventModel {

    private final EventManager eventManager;

    private final ObservableList<Event> eventObservableList;

    public EventModel() {
        eventManager = new EventManager();

        eventObservableList = FXCollections.observableList(eventManager.getAllEvents());
    }

    public ObservableList<Event> getEventObservableList() {
        return eventObservableList;
    }

    public void createEvent(EventDTO result) throws SQLException {
        Event newEvent = eventManager.createEvent(result);

        if(newEvent != null) {
            eventObservableList.add(newEvent);
        }
    }

    public void deleteEvent(Event event) throws SQLException {
        boolean wasDeleted = eventManager.deleteEvent(event);

        if(wasDeleted) {
            eventObservableList.remove(event);
        }
    }
}
