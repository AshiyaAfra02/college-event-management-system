package cems;

import javax.swing.*;
import java.awt.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

public class EventFormPanel {
    private JPanel main;
    private JTextField txtTitle, txtDate, txtSeats;
    private JTextArea txtDesc;
    private JComboBox<String> cbVenue;
    private int organizerId;
    private Integer editingEventId = null;

    public EventFormPanel(int organizerId) {
        this.organizerId = organizerId;
        main = new JPanel(null);

        JLabel l1 = new JLabel("Title:"); l1.setBounds(20,20,80,25); main.add(l1);
        txtTitle = new JTextField(); txtTitle.setBounds(120,20,420,25); main.add(txtTitle);

        JLabel l2 = new JLabel("Description:"); l2.setBounds(20,60,80,25); main.add(l2);
        txtDesc = new JTextArea(); JScrollPane sp = new JScrollPane(txtDesc); sp.setBounds(120,60,420,100); main.add(sp);

        JLabel l3 = new JLabel("Venue:"); l3.setBounds(20,180,80,25); main.add(l3);
        cbVenue = new JComboBox<>(); cbVenue.setBounds(120,180,300,25); main.add(cbVenue);

        JLabel l4 = new JLabel("Date (yyyy-MM-dd HH:mm:ss):"); l4.setBounds(20,220,200,25); main.add(l4);
        txtDate = new JTextField(); txtDate.setBounds(220,220,200,25); main.add(txtDate);

        JLabel l5 = new JLabel("Seats:"); l5.setBounds(20,260,80,25); main.add(l5);
        txtSeats = new JTextField(); txtSeats.setBounds(120,260,100,25); main.add(txtSeats);

        JButton save = new JButton("Save"); save.setBounds(180,310,120,35); main.add(save);
        JButton cancel = new JButton("Cancel"); cancel.setBounds(320,310,120,35); main.add(cancel);

        save.addActionListener(e -> saveEvent());
        cancel.addActionListener(e -> SwingUtilities.getWindowAncestor(main).dispose());

        loadVenues();
    }

    public JPanel getMainPanel(){ return main; }

    void loadVenues() {
        cbVenue.removeAllItems();
        List<Venue> list = VenueDAO.getAllVenuesList();
        for (Venue v : list) cbVenue.addItem(v.getVenueId()+" - "+v.getName());
    }

    public void loadForEdit(Event ev) {
        if (ev==null) return;
        editingEventId = ev.getEventId();
        txtTitle.setText(ev.getTitle());
        txtDesc.setText(ev.getDescription());
        txtDate.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(ev.getEventDate()));
        txtSeats.setText(String.valueOf(ev.getSeatsTotal()));
        loadVenues();
        for (int i=0;i<cbVenue.getItemCount();i++) {
            if (cbVenue.getItemAt(i).startsWith(ev.getVenueId()+" -")) { cbVenue.setSelectedIndex(i); break; }
        }
    }

    private void saveEvent() {
        try {
            String title = txtTitle.getText().trim();
            String desc = txtDesc.getText().trim();
            String venueSel = (String) cbVenue.getSelectedItem();
            if (venueSel==null) { JOptionPane.showMessageDialog(main,"Select venue"); return; }
            int venueId = Integer.parseInt(venueSel.split(" - ")[0]);
            String dateStr = txtDate.getText().trim();
            Timestamp ts = Timestamp.valueOf(dateStr);
            int seats = Integer.parseInt(txtSeats.getText().trim());

            boolean ok;
            if (editingEventId==null) {
                ok = EventDAO.addEvent(title, desc, organizerId, venueId, ts, seats);
            } else {
                ok = EventDAO.updateEvent(editingEventId, title, desc, venueId, ts, seats);
            }
            if (ok) {
                JOptionPane.showMessageDialog(main,"Saved successfully");
                NotificationDAO.addNotification(null, "Event updated/created: "+title);
                SwingUtilities.getWindowAncestor(main).dispose();
            } else JOptionPane.showMessageDialog(main,"Save failed");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(main,"Error: " + ex.getMessage());
        }
    }
}
