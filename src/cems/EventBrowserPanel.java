package cems;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class EventBrowserPanel {
    private JPanel main;
    private JTable table;
    private DefaultTableModel model;
    private int studentId;

    public EventBrowserPanel(int studentId) {
        this.studentId = studentId;
        main = new JPanel(new BorderLayout());
        model = new DefaultTableModel(new Object[]{"ID","Title","Venue","Date","Seats Left"},0) {
            public boolean isCellEditable(int r,int c){ return false; }
        };
        table = new JTable(model);
        main.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnRegister = new JButton("Register");
        JButton btnRefresh = new JButton("Refresh");
        bottom.add(btnRegister); bottom.add(btnRefresh);
        main.add(bottom, BorderLayout.SOUTH);

        loadEvents();

        btnRegister.addActionListener(e -> {
            int r = table.getSelectedRow(); if (r==-1) { JOptionPane.showMessageDialog(main,"Select event"); return; }
            int eventId = (int) model.getValueAt(r,0);
            boolean ok = RegistrationDAO.registerStudent(eventId, studentId);
            if (ok) { JOptionPane.showMessageDialog(main,"Registered"); loadEvents(); }
            else JOptionPane.showMessageDialog(main,"Register failed");
        });

        btnRefresh.addActionListener(e -> loadEvents());
    }

    public JPanel getMainPanel(){ return main; }

    private void loadEvents() {
        model.setRowCount(0);
        try {
            List<Event> list = EventDAO.getAllEvents();
            for (Event e : list) {
                String venueName = "N/A";
                for (Venue v : VenueDAO.getAllVenuesList()) if (v.getVenueId()==e.getVenueId()) venueName = v.getName();
                model.addRow(new Object[]{e.getEventId(), e.getTitle(), venueName, e.getEventDate(), e.getSeatsLeft()});
            }
        } catch (Exception ex) { ex.printStackTrace(); JOptionPane.showMessageDialog(main,"Load failed"); }
    }
}
