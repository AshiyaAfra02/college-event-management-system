package cems;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VenueDAO {

    public static List<Venue> getAllVenuesList() {
        List<Venue> list = new ArrayList<>();
        String sql = "SELECT venue_id, name, capacity FROM venues";
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Venue v = new Venue();
                v.setVenueId(rs.getInt("venue_id"));
                v.setName(rs.getString("name"));
                v.setCapacity(rs.getInt("capacity"));
                list.add(v);
            }
        } catch (SQLException ex) { ex.printStackTrace(); }
        return list;
    }

    public static boolean addVenue(String name, int capacity) {
        String sql = "INSERT INTO venues (name, capacity) VALUES (?,?)";
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setInt(2, capacity);
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) { ex.printStackTrace(); return false; }
    }

    public static boolean deleteVenue(int venueId) {
        String sql = "DELETE FROM venues WHERE venue_id=?";
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, venueId);
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) { ex.printStackTrace(); return false; }
    }
}
