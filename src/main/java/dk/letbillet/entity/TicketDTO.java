package dk.letbillet.entity;

import java.sql.Timestamp;

public class TicketDTO {

    private Event event;

    private String customerName;
    private String customerEmail;

    private int amount;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Ticket convertToTicket(int id, String guid, Timestamp issuedAt) {
        return new Ticket(id, guid, issuedAt, customerName, customerEmail, event);
    }
}
