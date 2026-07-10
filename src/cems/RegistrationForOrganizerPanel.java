package cems;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class RegistrationForOrganizerPanel {
    private JPanel main;
    private JTable table;
    private DefaultTableModel model;
    private int organizerId;

    public RegistrationForOrganizerPanel(int organizerId) {
        this.organizerId = organizerId;
        main = new JPanel(new BorderLayout());
        model = new DefaultTableModel(new Object[]{"Reg ID","Event","Student ID","Student Name","Time","Status"},0) {
            public boolean isCellEditable(int r,int c){ return false; }
        };
        table = new JTable(model);
        main.add(new JScrollPane(table), BorderLayout.CENTER);

        JButton refresh = new JButton("Refresh");
        JPanel top = new JPanel(new FlowLayout(FlowLayout.RIGHT)); top.add(refresh);
        main.add(top, BorderLayout.NORTH);

        refresh.addActionListener(e -> load());
        load();
    }

    public JPanel getMainPanel(){ return main; }

    private void load() {
        model.setRowCount(0);
        try {
            // get organizer events
            List<Event> evs = EventDAO.getAllEvents();
            for (Event ev : evs) {
                if (ev.getOrganizerId()!=organizerId) continue;
                List<Registration> regs = RegistrationDAO.getRegistrationsByEvent(ev.getEventId());
                for (Registration r : regs) {
                    String studentName = UserDAO.getUserName(r.getStudentId());
                    model.addRow(new Object[]{r.getRegId(), ev.getTitle(), r.getStudentId(), studentName, r.getRegTime(), r.getStatus()});
                }
            }
        } catch (Exception ex) { ex.printStackTrace(); JOptionPane.showMessageDialog(main,"Load failed"); }
    }
}
