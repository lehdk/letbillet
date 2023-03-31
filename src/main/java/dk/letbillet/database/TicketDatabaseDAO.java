package dk.letbillet.database;

import dk.letbillet.entity.Ticket;
import dk.letbillet.entity.TicketDTO;

import java.sql.*;
import java.util.ArrayList;

public class TicketDatabaseDAO {

    public ArrayList<Ticket> newTicket(TicketDTO ticketDTO) throws SQLException {
        var tickets = new ArrayList<Ticket>(ticketDTO.getAmount());

        try(Connection connection = DatabaseConnectionHandler.getInstance().getConnection()) {
            StringBuilder sql = new StringBuilder("BEGIN TRANSACTION; INSERT INTO [Ticket] ([CustomerId], [EventId]) OUTPUT inserted.* VALUES (?, ?)");
            sql.append(",(?, ?)".repeat(Math.max(0, ticketDTO.getAmount() - 1)));
            sql.append(" COMMIT TRANSACTION;");

            PreparedStatement statement = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);

            for (int i = 0; i < ticketDTO.getAmount() * 2; i += 2) {
                statement.setInt(i + 1, 1); // TODO: Fix this CustomerId
                statement.setInt(i + 2, ticketDTO.getEvent().getId());
            }

            var rs = statement.executeQuery();

            while (rs.next()) {
                tickets.add(ticketDTO.convertToTicket(
                        rs.getInt("Id"),
                        rs.getString("Guid"),
                        rs.getTimestamp("IssuedAt")
                ));
            }

            return tickets;
        }
    }
}
