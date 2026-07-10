package cems;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VenuePanel {
    private JPanel main;
    private DefaultTableModel model;
    private JTable table;

    public VenuePanel() {
        main = new JPanel(new BorderLayout());
        model = new DefaultTableModel(new Object[]{"Venue ID","Name","Capacity"},0) {
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
            String name = JOptionPane.showInputDialog(main,"Venue name:");
            if (name==null||name.trim().isEmpty()) return;
            String cap = JOptionPane.showInputDialog(main,"Capacity:");
            try {
                int c = Integer.parseInt(cap);
                boolean ok = VenueDAO.addVenue(name, c);
                if (ok) { JOptionPane.showMessageDialog(main,"Added"); load(); } else JOptionPane.showMessageDialog(main,"Add failed");
            } catch (Exception ex) { JOptionPane.showMessageDialog(main,"Invalid capacity"); }
        });

        del.addActionListener(e -> {
            int r = table.getSelectedRow(); if (r==-1){ JOptionPane.showMessageDialog(main,"Select"); return; }
            int id = (int) model.getValueAt(r,0);
            int c = JOptionPane.showConfirmDialog(main,"Delete venue #"+id+"?","Confirm",JOptionPane.YES_NO_OPTION);
            if (c!=JOptionPane.YES_OPTION) return;
            boolean ok = VenueDAO.deleteVenue(id);
            if (ok) { JOptionPane.showMessageDialog(main,"Deleted"); load(); } else JOptionPane.showMessageDialog(main,"Delete failed");
        });

        load();
    }

    private void load() {
        model.setRowCount(0);
        try {
            List<Venue> list = VenueDAO.getAllVenuesList();
            for (Venue v : list) model.addRow(new Object[]{v.getVenueId(), v.getName(), v.getCapacity()});
        } catch (Exception ex) { ex.printStackTrace(); JOptionPane.showMessageDialog(main,"Load failed"); }
    }

    public JPanel getMainPanel(){ return main; }
}
