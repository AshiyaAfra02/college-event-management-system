package cems;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnection {
    private static final String URL = "jdbc:mysql://localhost:3306/cems";
    private static final String USER = "root";
    private static final String PASSWORD = "Root@123";

    public static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
        // Optional: System.out.println("Database Connected Successfully!");
        return conn;
    }
}
