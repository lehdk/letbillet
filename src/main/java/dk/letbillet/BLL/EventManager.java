package dk.letbillet.BLL;

import dk.letbillet.database.EventDatabaseDAO;
import dk.letbillet.entity.Event;

import java.util.List;

public class EventManager {

    private EventDatabaseDAO eventDAO;

    public EventManager() {
        eventDAO = new EventDatabaseDAO();
    }

    public List<Event> getAllEvents() {
        return eventDAO.getAllEvents();
    }
}
