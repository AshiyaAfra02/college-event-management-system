package cems;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Timestamp;
import java.util.List;

public class EventManagerPanel {
    private JPanel main;
    private JTable table;
    private DefaultTableModel model;
    private int userId;
    private String role;

    public EventManagerPanel(int userId, String role) {
        this.userId = userId; this.role = role;
        main = new JPanel(new BorderLayout());
        model = new DefaultTableModel(new Object[]{"ID","Title","Venue","Date","Seats Total","Seats Left","Organizer"},0) {
            public boolean isCellEditable(int r,int c){ return false; }
        };
        table = new JTable(model);
        main.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel right = new JPanel(new GridLayout(4,1,8,8));
        JButton add = new JButton("Add");
        JButton edit = new JButton("Edit Selected");
        JButton del = new JButton("Delete Selected");
        JButton refresh = new JButton("Refresh");
        right.add(add); right.add(edit); right.add(del); right.add(refresh);
        main.add(right, BorderLayout.EAST);

        loadEvents();

        add.addActionListener(e -> {
            JFrame f = new JFrame("Create Event");
            f.setSize(600,420);
            f.setLocationRelativeTo(null);
            f.setContentPane(new EventFormPanel(userId).getMainPanel());
            f.setVisible(true);
        });

        edit.addActionListener(e -> {
            int r = table.getSelectedRow(); if (r==-1){ JOptionPane.showMessageDialog(main,"Select a row"); return; }
            int id = (int) model.getValueAt(r,0);
            Event ev = EventDAO.getEventById(id);
            if (ev==null){ JOptionPane.showMessageDialog(main,"Cannot load event"); return; }
            JFrame f = new JFrame("Edit Event");
            f.setSize(600,420); f.setLocationRelativeTo(null);
            EventFormPanel ef = new EventFormPanel(userId);
            ef.loadForEdit(ev);
            f.setContentPane(ef.getMainPanel()); f.setVisible(true);
        });

        del.addActionListener(e -> {
            int r = table.getSelectedRow(); if (r==-1){ JOptionPane.showMessageDialog(main,"Select a row"); return; }
            int id = (int) model.getValueAt(r,0);
            int ok = JOptionPane.showConfirmDialog(main,"Delete event #"+id+"?","Confirm",JOptionPane.YES_NO_OPTION);
            if (ok!=JOptionPane.YES_OPTION) return;
            boolean res = EventDAO.deleteEvent(id);
            if (res) { JOptionPane.showMessageDialog(main,"Deleted"); loadEvents(); }
            else JOptionPane.showMessageDialog(main,"Delete failed");
        });

        refresh.addActionListener(e -> loadEvents());
    }

    public JPanel getMainPanel(){ return main; }

    private void loadEvents() {
        model.setRowCount(0);
        try {
            List<Event> list = EventDAO.getAllEvents();
            for (Event ev : list) {
                String venueName = "N/A";
                // find venue
                for (Venue v : VenueDAO.getAllVenuesList()) if (v.getVenueId()==ev.getVenueId()) venueName = v.getName();
                String orgName = UserDAO.getUserName(ev.getOrganizerId());
                model.addRow(new Object[]{ev.getEventId(), ev.getTitle(), venueName, ev.getEventDate(), ev.getSeatsTotal(), ev.getSeatsLeft(), orgName});
            }
        } catch (Exception ex) { ex.printStackTrace(); JOptionPane.showMessageDialog(main,"Failed to load events"); }
    }
}
