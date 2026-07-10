package cems;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RegistrationDAO {

    public static boolean registerStudent(int eventId, int studentId) {
        String sql = "INSERT INTO registrations (event_id, student_id) VALUES (?,?)";
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, eventId);
            ps.setInt(2, studentId);
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) { ex.printStackTrace(); return false; }
    }

    public static boolean cancelRegistration(int eventId, int studentId) {
        String sql = "UPDATE registrations SET status='cancelled' WHERE event_id=? AND student_id=?";
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, eventId);
            ps.setInt(2, studentId);
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) { ex.printStackTrace(); return false; }
    }

    public static List<Registration> getRegistrationsByStudent(int studentId) {
        List<Registration> list = new ArrayList<>();
        String sql = "SELECT r.reg_id, r.event_id, e.title AS event_title, r.reg_time, r.status " +
                     "FROM registrations r JOIN events e ON r.event_id=e.event_id WHERE r.student_id=?";
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Registration r = new Registration();
                    r.setRegId(rs.getInt("reg_id"));
                    r.setEventId(rs.getInt("event_id"));
                    r.setRegTime(rs.getTimestamp("reg_time"));
                    r.setStatus(rs.getString("status"));
                    list.add(r);
                }
            }
        } catch (SQLException ex) { ex.printStackTrace(); }
        return list;
    }

    public static List<Registration> getRegistrationsByEvent(int eventId) {
        List<Registration> list = new ArrayList<>();
        String sql = "SELECT r.reg_id, r.event_id, r.student_id, u.full_name, r.reg_time, r.status " +
                     "FROM registrations r JOIN users u ON r.student_id=u.user_id WHERE r.event_id=?";
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, eventId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Registration r = new Registration();
                    r.setRegId(rs.getInt("reg_id"));
                    r.setEventId(rs.getInt("event_id"));
                    r.setStudentId(rs.getInt("student_id"));
                    r.setRegTime(rs.getTimestamp("reg_time"));
                    r.setStatus(rs.getString("status"));
                    list.add(r);
                }
            }
        } catch (SQLException ex) { ex.printStackTrace(); }
        return list;
    }

    public static List<Registration> getAllRegistrations() {
        List<Registration> list = new ArrayList<>();
        String sql = "SELECT * FROM registrations";
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Registration r = new Registration();
                r.setRegId(rs.getInt("reg_id"));
                r.setEventId(rs.getInt("event_id"));
                r.setStudentId(rs.getInt("student_id"));
                r.setRegTime(rs.getTimestamp("reg_time"));
                r.setStatus(rs.getString("status"));
                list.add(r);
            }
        } catch (SQLException ex) { ex.printStackTrace(); }
        return list;
    }
}
