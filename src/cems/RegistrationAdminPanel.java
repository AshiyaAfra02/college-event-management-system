package cems;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class RegistrationAdminPanel {

    private JPanel main;
    private JTable table;
    private DefaultTableModel model;

    public RegistrationAdminPanel() {

        main = new JPanel(new BorderLayout());

        model = new DefaultTableModel(
                new Object[]{"Reg ID", "Event ID", "Student ID", "Time", "Status"}, 0
        ) {
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        table = new JTable(model);
        main.add(new JScrollPane(table), BorderLayout.CENTER);

        JButton refresh = new JButton("Refresh");
        JPanel top = new JPanel();
        top.add(refresh);
        main.add(top, BorderLayout.NORTH);

        refresh.addActionListener(e -> load());

        load();
    }

    public JPanel getMainPanel() {
        return main;
    }

    private void load() {
        model.setRowCount(0);

        try {
            List<Registration> list = RegistrationDAO.getAllRegistrations();
            for (Registration r : list) {
                model.addRow(new Object[]{
                        r.getRegId(),
                        r.getEventId(),
                        r.getStudentId(),
                        r.getRegTime(),
                        r.getStatus()
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(main, "Load failed");
        }
    }
}
