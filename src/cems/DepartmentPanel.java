package cems;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class DepartmentPanel {
    private JPanel main;
    private DefaultTableModel model;
    private JTable table;

    public DepartmentPanel() {
        main = new JPanel(new BorderLayout());
        model = new DefaultTableModel(new Object[]{"Dept ID","Name"},0) {
            public boolean isCellEditable(int r,int c){return false;}
        };
        table = new JTable(model);
        main.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel right = new JPanel(new GridLayout(2,1,8,8));
        JButton add = new JButton("Add");
        JButton del = new JButton("Delete");
        right.add(add); right.add(del);
        main.add(right, BorderLayout.EAST);

        add.addActionListener(e -> {
            String name = JOptionPane.showInputDialog(main,"Department name:");
            if (name==null||name.trim().isEmpty()) return;
            boolean ok = DepartmentDAO.addDepartment(name);
            if (ok) { JOptionPane.showMessageDialog(main,"Added"); load(); } else JOptionPane.showMessageDialog(main,"Add failed");
        });

        del.addActionListener(e -> {
            int r = table.getSelectedRow(); if (r==-1){ JOptionPane.showMessageDialog(main,"Select"); return; }
            int id = (int) model.getValueAt(r,0);
            int c = JOptionPane.showConfirmDialog(main,"Delete dept #"+id+"?","Confirm",JOptionPane.YES_NO_OPTION);
            if (c!=JOptionPane.YES_OPTION) return;
            boolean ok = DepartmentDAO.deleteDepartment(id);
            if (ok) { JOptionPane.showMessageDialog(main,"Deleted"); load(); } else JOptionPane.showMessageDialog(main,"Delete failed");
        });

        load();
    }

    private void load() {
        model.setRowCount(0);
        try {
            List<Department> list = DepartmentDAO.getAllDepartments();
            for (Department d : list) model.addRow(new Object[]{d.getDeptId(), d.getName()});
        } catch (Exception ex) { ex.printStackTrace(); JOptionPane.showMessageDialog(main,"Load failed"); }
    }

    public JPanel getMainPanel(){ return main; }
}
