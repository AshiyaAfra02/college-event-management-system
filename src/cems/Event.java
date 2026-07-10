package cems;

import java.sql.Timestamp;

public class Event {
    private int eventId;
    private String title;
    private String description;
    private int organizerId;
    private int venueId;
    private Timestamp eventDate;
    private int seatsTotal;
    private int seatsLeft;

    public Event() {}

    // getters & setters
    public int getEventId() { return eventId; }
    public void setEventId(int eventId) { this.eventId = eventId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public int getOrganizerId() { return organizerId; }
    public void setOrganizerId(int organizerId) { this.organizerId = organizerId; }
    public int getVenueId() { return venueId; }
    public void setVenueId(int venueId) { this.venueId = venueId; }
    public Timestamp getEventDate() { return eventDate; }
    public void setEventDate(Timestamp eventDate) { this.eventDate = eventDate; }
    public int getSeatsTotal() { return seatsTotal; }
    public void setSeatsTotal(int seatsTotal) { this.seatsTotal = seatsTotal; }
    public int getSeatsLeft() { return seatsLeft; }
    public void setSeatsLeft(int seatsLeft) { this.seatsLeft = seatsLeft; }
}
