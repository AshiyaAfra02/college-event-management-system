package cems;

import javax.swing.*;
import java.awt.*;

public class AdminUI extends JFrame {
    private int userId;
    private String name;

    public AdminUI(int userId, String name) {
        this.userId = userId; this.name = name;
        init();
    }

    private void init() {
        setTitle("CEMS - Admin ("+name+")");
        setSize(1100,700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Top bar (Layout B)
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT,10,10));
        top.setBackground(new Color(245,245,245));
        JButton bEvents = new JButton("Events");
        JButton bUsers = new JButton("Users");
        JButton bDepartments = new JButton("Departments");
        JButton bVenues = new JButton("Venues");
        JButton bRegistrations = new JButton("Registrations");
        JButton bNotifications = new JButton("Notifications");
        JButton bLogout = new JButton("Logout");
        top.add(bEvents); top.add(bUsers); top.add(bDepartments); top.add(bVenues);
        top.add(bRegistrations); top.add(bNotifications); top.add(bLogout);
        add(top, BorderLayout.NORTH);

        JPanel content = new JPanel(new BorderLayout());
        add(content, BorderLayout.CENTER);

        // default
        content.add(new EventManagerPanel(userId, "admin").getMainPanel(), BorderLayout.CENTER);

        bEvents.addActionListener(e -> {
            content.removeAll();
            content.add(new EventManagerPanel(userId, "admin").getMainPanel(), BorderLayout.CENTER);
            revalidate(); repaint();
        });

        bUsers.addActionListener(e -> {
            content.removeAll();
            content.add(new UserManagerPanel().getMainPanel(), BorderLayout.CENTER);
            revalidate(); repaint();
        });

        bDepartments.addActionListener(e -> {
            content.removeAll();
            content.add(new DepartmentPanel().getMainPanel(), BorderLayout.CENTER);
            revalidate(); repaint();
        });

        bVenues.addActionListener(e -> {
            content.removeAll();
            content.add(new VenuePanel().getMainPanel(), BorderLayout.CENTER);
            revalidate(); repaint();
        });

        bRegistrations.addActionListener(e -> {
            content.removeAll();
            content.add(new RegistrationAdminPanel().getMainPanel(), BorderLayout.CENTER);
            revalidate(); repaint();
        });

        bNotifications.addActionListener(e -> {
            content.removeAll();
            content.add(new NotificationPanel(userId, true).getMainPanel(), BorderLayout.CENTER);
            revalidate(); repaint();
        });

        bLogout.addActionListener(e -> {
            new LoginForm().setVisible(true);
            dispose();
        });
    }
}
