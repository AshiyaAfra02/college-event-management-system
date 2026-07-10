package cems;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class RegistrationPanel {
    private JPanel main;
    private JTable table;
    private DefaultTableModel model;
    private int studentId;

    public RegistrationPanel(int studentId) {
        this.studentId = studentId;
        main = new JPanel(new BorderLayout());
        model = new DefaultTableModel(new Object[]{"Reg ID","Event ID","Date","Status"},0) {
            public boolean isCellEditable(int r,int c){ return false; }
        };
        table = new JTable(model);
        main.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnCancel = new JButton("Cancel Registration");
        JButton btnRefresh = new JButton("Refresh");
        bottom.add(btnCancel); bottom.add(btnRefresh);
        main.add(bottom, BorderLayout.SOUTH);

        loadRegs();

        btnCancel.addActionListener(e -> {
            int r = table.getSelectedRow(); if (r==-1) { JOptionPane.showMessageDialog(main,"Select row"); return; }
            int eventId = (int) model.getValueAt(r,1);
            boolean ok = RegistrationDAO.cancelRegistration(eventId, studentId);
            if (ok) { JOptionPane.showMessageDialog(main,"Cancelled"); loadRegs(); }
            else JOptionPane.showMessageDialog(main,"Cancel failed");
        });

        btnRefresh.addActionListener(e -> loadRegs());
    }

    public JPanel getMainPanel(){ return main; }

    private void loadRegs() {
        model.setRowCount(0);
        try {
            List<Registration> list = RegistrationDAO.getRegistrationsByStudent(studentId);
            for (Registration r : list) {
                model.addRow(new Object[]{r.getRegId(), r.getEventId(), r.getRegTime(), r.getStatus()});
            }
        } catch (Exception ex) { ex.printStackTrace(); JOptionPane.showMessageDialog(main,"Load failed"); }
    }
}
