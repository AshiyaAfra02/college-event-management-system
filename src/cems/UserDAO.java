package cems;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    // validate login; returns role or null
    public static String validateLogin(String username, String password) {
        String role = null;
        String sql = "SELECT role FROM users WHERE username = ? AND password = ?";
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) role = rs.getString("role");
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return role;
    }

    public static int getUserId(String username) {
        int id = -1;
        String sql = "SELECT user_id FROM users WHERE username = ?";
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) id = rs.getInt("user_id");
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return id;
    }

    public static String getUserName(int userId) {
        String name = null;
        String sql = "SELECT full_name FROM users WHERE user_id = ?";
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) name = rs.getString("full_name");
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return name;
    }

    public static boolean addUser(String username, String password, String fullName, String email, String role, Integer deptId) {
        String sql = "INSERT INTO users (username,password,full_name,email,role,dept_id) VALUES (?,?,?,?,?,?)";
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, fullName);
            ps.setString(4, email);
            ps.setString(5, role);
            if (deptId == null) ps.setNull(6, Types.INTEGER); else ps.setInt(6, deptId);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public static boolean updateUser(int userId, String fullName, String role, Integer deptId) {
        String sql = "UPDATE users SET full_name=?, role=?, dept_id=? WHERE user_id=?";
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, fullName);
            ps.setString(2, role);
            if (deptId == null) ps.setNull(3, Types.INTEGER); else ps.setInt(3, deptId);
            ps.setInt(4, userId);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public static boolean deleteUser(int userId) {
        String sql = "DELETE FROM users WHERE user_id=?";
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public static List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT user_id, username, full_name, email, role, dept_id FROM users";
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                User u = new User();
                u.setUserId(rs.getInt("user_id"));
                u.setUsername(rs.getString("username"));
                u.setFullName(rs.getString("full_name"));
                u.setEmail(rs.getString("email"));
                u.setRole(rs.getString("role"));
                int d = rs.getInt("dept_id"); if (rs.wasNull()) u.setDeptId(null); else u.setDeptId(d);
                list.add(u);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
}
