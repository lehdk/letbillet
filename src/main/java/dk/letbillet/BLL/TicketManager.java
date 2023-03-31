package dk.letbillet.BLL;

import dk.letbillet.database.TicketDatabaseDAO;
import dk.letbillet.entity.Ticket;
import dk.letbillet.entity.TicketDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class TicketManager {

    private TicketDatabaseDAO ticketDatabaseDAO;

    public TicketManager() {
        ticketDatabaseDAO = new TicketDatabaseDAO();
    }

    public ArrayList<Ticket> createTicket(TicketDTO ticketDTO) throws SQLException {
        if(ticketDTO.getAmount() < 1) return null;


        var result = ticketDatabaseDAO.newTicket(ticketDTO);

        var event = ticketDTO.getEvent();
        event.setTicketsSold(event.getTicketsSold() + result.size());

        return result;
    }
}
