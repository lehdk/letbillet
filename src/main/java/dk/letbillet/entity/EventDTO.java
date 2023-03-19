package dk.letbillet.entity;

import java.sql.Timestamp;

public class EventDTO {

    private String name;
    private String location;
    private String notes;
    private Timestamp start;
    private Timestamp end;
    private int price;

    public EventDTO(String name, String location, String notes, Timestamp start, Timestamp end, int price) {
        this.name = name;
        this.location = location;
        this.notes = notes;
        this.start = start;
        this.end = end;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Timestamp getStart() {
        return start;
    }

    public void setStart(Timestamp start) {
        this.start = start;
    }

    public Timestamp getEnd() {
        return end;
    }

    public void setEnd(Timestamp end) {
        this.end = end;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Event convertToEvent(int id) {
        return new Event(
                id,
                name,
                location,
                notes,
                start,
                end,
                price,
                0
        );
    }
}
