package cems;

import javax.swing.*;
import java.awt.*;

public class LoginForm extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;

    public LoginForm() {
        setTitle("CEMS - Login");
        setSize(420, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        JLabel l1 = new JLabel("Username:");
        l1.setBounds(40, 40, 100, 25);
        add(l1);

        txtUsername = new JTextField();
        txtUsername.setBounds(150, 40, 200, 25);
        add(txtUsername);

        JLabel l2 = new JLabel("Password:");
        l2.setBounds(40, 90, 100, 25);
        add(l2);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(150, 90, 200, 25);
        add(txtPassword);

        // ---- Buttons for Role-based Login ----
        JButton btnAdmin = new JButton("Login as Admin");
        btnAdmin.setBounds(120, 140, 170, 30);
        add(btnAdmin);

        JButton btnOrg = new JButton("Login as Organizer");
        btnOrg.setBounds(120, 180, 170, 30);
        add(btnOrg);

        JButton btnStudent = new JButton("Login as Student");
        btnStudent.setBounds(120, 220, 170, 30);
        add(btnStudent);

        // Event listeners
        btnAdmin.addActionListener(e -> loginWithRole("admin"));
        btnOrg.addActionListener(e -> loginWithRole("organizer"));
        btnStudent.addActionListener(e -> loginWithRole("student"));
    }

    // Validate login based on role
    private void loginWithRole(String requiredRole) {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());

        try {
            UserDAO dao = new UserDAO();
            String role = dao.validateLogin(username, password);

            if (role == null) {
                JOptionPane.showMessageDialog(this, "Invalid username or password!");
                return;
            }

            if (!role.equals(requiredRole)) {
                JOptionPane.showMessageDialog(this, 
                    "You are not allowed to login as " + requiredRole.toUpperCase());
                return;
            }

            // SUCCESS
            JOptionPane.showMessageDialog(this, "Login successful!");

            int userId = dao.getUserId(username);
            String name = dao.getUserName(userId);

            if (role.equals("admin")) {
                new AdminUI(userId, name).setVisible(true);
            } else if (role.equals("organizer")) {
                new OrganizerUI(userId, name).setVisible(true);
            } else {
                new StudentUI(userId, name).setVisible(true);
            }

            dispose();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Login failed: " + ex);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginForm().setVisible(true));
    }
}
