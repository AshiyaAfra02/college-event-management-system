package cems;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class NotificationPanel {
    private JPanel main;
    private DefaultTableModel model;
    private JTable table;
    private int userId;
    private boolean adminView;

    public NotificationPanel(int userId, boolean adminView) {
        this.userId = userId; this.adminView = adminView;
        main = new JPanel(new BorderLayout());
        model = new DefaultTableModel(new Object[]{"Note ID","User ID","Message","Time"},0) {
            public boolean isCellEditable(int r,int c){return false;}
        };
        table = new JTable(model);
        main.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel top = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton add = new JButton("Add Notification");
        JButton refresh = new JButton("Refresh");
        top.add(add); top.add(refresh);
        main.add(top, BorderLayout.NORTH);

        add.addActionListener(e -> {
            String msg = JOptionPane.showInputDialog(main,"Message:");
            if (msg==null||msg.trim().isEmpty()) return;
            Integer uid = null;
            if (adminView) {
                String uidStr = JOptionPane.showInputDialog(main,"User ID (blank for all):");
                if (uidStr!=null && !uidStr.trim().isEmpty()) uid = Integer.parseInt(uidStr);
            } else uid = userId;
            boolean ok = NotificationDAO.addNotification(uid, msg);
            if (ok) { JOptionPane.showMessageDialog(main,"Added"); load(); } else JOptionPane.showMessageDialog(main,"Add failed");
        });

        refresh.addActionListener(e -> load());
        load();
    }

    private void load() {
        model.setRowCount(0);
        try {
            List<Notification> list;
            if (adminView) list = NotificationDAO.getAllNotifications();
            else list = NotificationDAO.getNotificationsForUser(userId);
            for (Notification n : list) model.addRow(new Object[]{n.getNoteId(), n.getUserId(), n.getMessage(), n.getCreatedAt()});
        } catch (Exception ex) { ex.printStackTrace(); JOptionPane.showMessageDialog(main,"Load failed"); }
    }

    public JPanel getMainPanel(){ return main; }
}
