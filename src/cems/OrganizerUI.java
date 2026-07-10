package cems;

import javax.swing.*;
import java.awt.*;

public class OrganizerUI extends JFrame {
    private int userId; private String name;
    public OrganizerUI(int userId, String name) {
        this.userId = userId; this.name = name; init();
    }
    private void init() {
        setTitle("CEMS - Organizer ("+name+")");
        setSize(1100,700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT,10,10));
        top.setBackground(new Color(245,245,245));
        JButton bMyEvents = new JButton("My Events");
        JButton bCreate = new JButton("Create Event");
        JButton bRegistrations = new JButton("Registrations");
        JButton bNotifications = new JButton("Notifications");
        JButton bLogout = new JButton("Logout");
        top.add(bMyEvents); top.add(bCreate); top.add(bRegistrations); top.add(bNotifications); top.add(bLogout);
        add(top, BorderLayout.NORTH);

        JPanel content = new JPanel(new BorderLayout());
        add(content, BorderLayout.CENTER);
        content.add(new EventManagerPanel(userId, "organizer").getMainPanel(), BorderLayout.CENTER);

        bMyEvents.addActionListener(e -> {
            content.removeAll();
            content.add(new EventManagerPanel(userId, "organizer").getMainPanel(), BorderLayout.CENTER);
            revalidate(); repaint();
        });
        bCreate.addActionListener(e -> {
            content.removeAll();
            content.add(new EventFormPanel(userId).getMainPanel(), BorderLayout.CENTER);
            revalidate(); repaint();
        });
        bRegistrations.addActionListener(e -> {
            content.removeAll();
            content.add(new RegistrationForOrganizerPanel(userId).getMainPanel(), BorderLayout.CENTER);
            revalidate(); repaint();
        });
        bNotifications.addActionListener(e -> {
            content.removeAll();
            content.add(new NotificationPanel(userId, false).getMainPanel(), BorderLayout.CENTER);
            revalidate(); repaint();
        });
        bLogout.addActionListener(e -> {
            new LoginForm().setVisible(true);
            dispose();
        });
    }
}
