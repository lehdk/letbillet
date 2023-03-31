package dk.letbillet.presentation.model;

import dk.letbillet.BLL.TicketManager;
import dk.letbillet.entity.Ticket;
import dk.letbillet.entity.TicketDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class TicketModel {

    private TicketManager ticketManager;

    public TicketModel() {
        ticketManager = new TicketManager();
    }

    public ArrayList<Ticket> createTicket(TicketDTO ticketDTO) throws SQLException {
        return ticketManager.createTicket(ticketDTO);
    }
}
