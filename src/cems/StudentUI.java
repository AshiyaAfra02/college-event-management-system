package cems;

import javax.swing.*;
import java.awt.*;

public class StudentUI extends JFrame {
    private int userId; private String name;
    public StudentUI(int userId, String name) { this.userId = userId; this.name = name; init(); }
    private void init() {
        setTitle("CEMS - Student ("+name+")");
        setSize(1100,700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT,10,10));
        top.setBackground(new Color(245,245,245));
        JButton bBrowse = new JButton("Browse Events");
        JButton bMyRegs = new JButton("My Registrations");
        JButton bNotifications = new JButton("Notifications");
        JButton bLogout = new JButton("Logout");
        top.add(bBrowse); top.add(bMyRegs); top.add(bNotifications); top.add(bLogout);
        add(top, BorderLayout.NORTH);

        JPanel content = new JPanel(new BorderLayout());
        add(content, BorderLayout.CENTER);
        content.add(new EventBrowserPanel(userId).getMainPanel(), BorderLayout.CENTER);

        bBrowse.addActionListener(e -> {
            content.removeAll();
            content.add(new EventBrowserPanel(userId).getMainPanel(), BorderLayout.CENTER);
            revalidate(); repaint();
        });
        bMyRegs.addActionListener(e -> {
            content.removeAll();
            content.add(new RegistrationPanel(userId).getMainPanel(), BorderLayout.CENTER);
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
