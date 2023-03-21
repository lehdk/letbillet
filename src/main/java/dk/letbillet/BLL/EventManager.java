package dk.letbillet.BLL;

import dk.letbillet.database.EventDatabaseDAO;
import dk.letbillet.entity.Event;
import dk.letbillet.entity.EventDTO;

import java.sql.SQLException;
import java.util.List;

public class EventManager {

    private final EventDatabaseDAO eventDAO;

    public EventManager() {
        eventDAO = new EventDatabaseDAO();
    }

    public List<Event> getAllEvents() {
        return eventDAO.getAllEvents();
    }

    public Event createEvent(EventDTO event) throws SQLException {
        return eventDAO.createEvent(event);
    }

    public boolean deleteEvent(Event event) throws SQLException {
        return eventDAO.deleteEvent(event);
    }
}
