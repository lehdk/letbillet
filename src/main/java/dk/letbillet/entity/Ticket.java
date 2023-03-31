package dk.letbillet.entity;

import java.sql.Timestamp;

public class Ticket {

    private int id;
    private String guid;
    private Timestamp issuedAt;
    private String customerName;
    private String customerEmail;
    private Event event;

    public Ticket(int id, String guid, Timestamp issuedAt, String customerName, String customerEmail, Event event) {
        this.id = id;
        this.guid = guid;
        this.issuedAt = issuedAt;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.event = event;
    }

    public int getId() {
        return id;
    }

    public Timestamp getIssuedAt() {
        return issuedAt;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public Event getEvent() {
        return event;
    }
}
