package cems;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NotificationDAO {

    public static boolean addNotification(Integer userId, String message) {
        String sql = "INSERT INTO notifications (user_id, message) VALUES (?,?)";
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            if (userId == null) ps.setNull(1, Types.INTEGER); else ps.setInt(1, userId);
            ps.setString(2, message);
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) { ex.printStackTrace(); return false; }
    }

    public static List<Notification> getNotificationsForUser(int userId) {
        List<Notification> list = new ArrayList<>();
        String sql = "SELECT note_id, user_id, message, created_at FROM notifications WHERE user_id=? ORDER BY created_at DESC";
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Notification n = new Notification();
                    n.setNoteId(rs.getInt("note_id"));
                    n.setUserId(rs.getInt("user_id"));
                    n.setMessage(rs.getString("message"));
                    n.setCreatedAt(rs.getTimestamp("created_at"));
                    list.add(n);
                }
            }
        } catch (SQLException ex) { ex.printStackTrace(); }
        return list;
    }

    public static List<Notification> getAllNotifications() {
        List<Notification> list = new ArrayList<>();
        String sql = "SELECT note_id, user_id, message, created_at FROM notifications ORDER BY created_at DESC";
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Notification n = new Notification();
                n.setNoteId(rs.getInt("note_id"));
                n.setUserId(rs.getInt("user_id"));
                n.setMessage(rs.getString("message"));
                n.setCreatedAt(rs.getTimestamp("created_at"));
                list.add(n);
            }
        } catch (SQLException ex) { ex.printStackTrace(); }
        return list;
    }
}
