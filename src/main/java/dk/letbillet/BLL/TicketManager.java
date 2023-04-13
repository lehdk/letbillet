package dk.letbillet.BLL;

import com.lowagie.text.DocumentException;
import dk.letbillet.database.TicketDatabaseDAO;
import dk.letbillet.entity.Ticket;
import dk.letbillet.entity.TicketDTO;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import static dk.letbillet.util.PDFGenerator.TICKET_TEMPLATE;
import static dk.letbillet.util.PDFGenerator.parseThymeleafTemplate;

public class TicketManager {

    private TicketDatabaseDAO ticketDatabaseDAO;

    public TicketManager() {
        ticketDatabaseDAO = new TicketDatabaseDAO();
    }

    public ArrayList<Ticket> createTicket(TicketDTO ticketDTO) throws SQLException {
        if(ticketDTO.getAmount() < 1) return null;

        ArrayList<Ticket> result = ticketDatabaseDAO.newTicket(ticketDTO);

        var event = ticketDTO.getEvent();
        event.setTicketsSold(event.getTicketsSold() + result.size());

        // Print tickets here
        try {
            generatePdfFromTickets(result);
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
            System.err.println("Could not print tickets!");
        }

        return result;
    }

    private void generatePdfFromTickets(ArrayList<Ticket> tickets) throws IOException, DocumentException {

        if(tickets == null || tickets.size() < 1) return;

        for(Ticket t : tickets) {

            var variables = new HashMap<String, String>();
            variables.put("event_name", t.getEvent().getName());
            variables.put("start_time", t.getEvent().getStart().toString());
            variables.put("end_time", (t.getEvent().getEnd() == null) ? "No end time specified" : t.getEvent().getEnd().toString());
            variables.put("ticket_guid", t.getGuid());
            variables.put("customer_email", t.getCustomerEmail().isEmpty() ?  "Anonymous" : t.getCustomerEmail());
            variables.put("customer_name", t.getCustomerName().isEmpty() ?  "Anonymous" : t.getCustomerName());

            String html = parseThymeleafTemplate(TICKET_TEMPLATE, variables);

            String outputFolder = System.getProperty("user.home") + File.separator + "Downloads" + File.separator + t.getGuid() + ".pdf";
            OutputStream outputStream = new FileOutputStream(outputFolder);

            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(html);
            renderer.layout();
            renderer.createPDF(outputStream);

            outputStream.close();
        }
    }
}
