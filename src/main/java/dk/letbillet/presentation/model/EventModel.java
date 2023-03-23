package dk.letbillet.presentation.model;

import dk.letbillet.BLL.EventManager;
import dk.letbillet.entity.Event;
import dk.letbillet.entity.EventDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;

public class EventModel {

    private final EventManager eventManager;

    private ObservableList<Event> eventObservableList;

    public EventModel() {
        eventManager = new EventManager();

        eventObservableList = FXCollections.observableArrayList(eventManager.getAllEvents());
    }

    public ObservableList<Event> getEventObservableList() {
        return eventObservableList;
    }

    public Event createEvent(EventDTO result) throws SQLException {
        Event newEvent = eventManager.createEvent(result);

        if(newEvent != null) eventObservableList.add(newEvent);

        return newEvent;
    }

    public void deleteEvent(Event event) throws SQLException {
        boolean wasDeleted = eventManager.deleteEvent(event);

        if(wasDeleted) {
            eventObservableList.remove(event);
        }
    }

    public boolean editEvent(int eventId, EventDTO result) throws SQLException {
        boolean wasEdited = eventManager.editEvent(eventId, result);

        if(!wasEdited) return false;

        Event event = eventObservableList.stream().filter(e -> e.getId() == eventId).findFirst().orElseGet(null);

        int index = eventObservableList.indexOf(event);

        if(event != null) {
            event.setName(result.getName());
            event.setLocation(result.getLocation());
            event.setStart(result.getStart());
            event.setEnd(result.getEnd());
            event.setPrice(result.getPrice());
            event.setNotes(result.getNotes());
        }

        eventObservableList.set(index, event);

        return true;
    }
}
