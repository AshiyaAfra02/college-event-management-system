package cems;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDAO {

    public static List<Department> getAllDepartments() {
        List<Department> list = new ArrayList<>();
        String sql = "SELECT dept_id, name FROM departments";
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Department d = new Department();
                d.setDeptId(rs.getInt("dept_id"));
                d.setName(rs.getString("name"));
                list.add(d);
            }
        } catch (SQLException ex) { ex.printStackTrace(); }
        return list;
    }

    public static boolean addDepartment(String name) {
        String sql = "INSERT INTO departments (name) VALUES (?)";
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) { ex.printStackTrace(); return false; }
    }

    public static boolean deleteDepartment(int deptId) {
        String sql = "DELETE FROM departments WHERE dept_id=?";
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, deptId);
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) { ex.printStackTrace(); return false; }
    }
}
