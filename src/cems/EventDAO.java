package cems;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventDAO {

    public static boolean addEvent(String title, String description, int organizerId, int venueId, Timestamp dateTime, int totalSeats) {
        String sql = "INSERT INTO events (title, description, organizer_id, venue_id, event_date, seats_total, seats_left) VALUES (?,?,?,?,?,?,?)";
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setInt(3, organizerId);
            ps.setInt(4, venueId);
            ps.setTimestamp(5, dateTime);
            ps.setInt(6, totalSeats);
            ps.setInt(7, totalSeats);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public static Event getEventById(int eventId) {
        String sql = "SELECT * FROM events WHERE event_id=?";
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, eventId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Event e = new Event();
                    e.setEventId(rs.getInt("event_id"));
                    e.setTitle(rs.getString("title"));
                    e.setDescription(rs.getString("description"));
                    e.setOrganizerId(rs.getInt("organizer_id"));
                    e.setVenueId(rs.getInt("venue_id"));
                    e.setEventDate(rs.getTimestamp("event_date"));
                    e.setSeatsTotal(rs.getInt("seats_total"));
                    e.setSeatsLeft(rs.getInt("seats_left"));
                    return e;
                }
            }
        } catch (SQLException ex) { ex.printStackTrace(); }
        return null;
    }

    public static List<Event> getAllEvents() {
        List<Event> list = new ArrayList<>();
        String sql = "SELECT * FROM events ORDER BY event_date";
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Event e = new Event();
                e.setEventId(rs.getInt("event_id"));
                e.setTitle(rs.getString("title"));
                e.setDescription(rs.getString("description"));
                e.setOrganizerId(rs.getInt("organizer_id"));
                e.setVenueId(rs.getInt("venue_id"));
                e.setEventDate(rs.getTimestamp("event_date"));
                e.setSeatsTotal(rs.getInt("seats_total"));
                e.setSeatsLeft(rs.getInt("seats_left"));
                list.add(e);
            }
        } catch (SQLException ex) { ex.printStackTrace(); }
        return list;
    }

    public static boolean updateEvent(int eventId, String title, String description, int venueId, Timestamp dateTime, int totalSeats) {
        String sql = "UPDATE events SET title=?, description=?, venue_id=?, event_date=?, seats_total=? WHERE event_id=?";
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setInt(3, venueId);
            ps.setTimestamp(4, dateTime);
            ps.setInt(5, totalSeats);
            ps.setInt(6, eventId);
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) { ex.printStackTrace(); return false; }
    }

    public static boolean deleteEvent(int eventId) {
        String sql = "DELETE FROM events WHERE event_id=?";
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, eventId);
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) { ex.printStackTrace(); return false; }
    }
}
