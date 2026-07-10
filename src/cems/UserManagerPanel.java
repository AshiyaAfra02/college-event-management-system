package cems;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class UserManagerPanel {
    private JPanel main;
    private JTable table;
    private DefaultTableModel model;

    public UserManagerPanel() {
        main = new JPanel(new BorderLayout());
        model = new DefaultTableModel(new Object[]{"User ID","Username","Full Name","Email","Role","Dept ID"},0) {
            public boolean isCellEditable(int r,int c){ return false; }
        };
        table = new JTable(model);
        main.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel right = new JPanel(new GridLayout(3,1,8,8));
        JButton add = new JButton("Add User");
        JButton edit = new JButton("Edit Selected");
        JButton del = new JButton("Delete Selected");
        right.add(add); right.add(edit); right.add(del);
        main.add(right, BorderLayout.EAST);

        add.addActionListener(e -> addUser());
        edit.addActionListener(e -> editUser());
        del.addActionListener(e -> deleteUser());

        load();
    }

    public JPanel getMainPanel(){ return main; }

    private void load() {
        model.setRowCount(0);
        try {
            List<User> list = UserDAO.getAllUsers();
            for (User u : list) model.addRow(new Object[]{u.getUserId(), u.getUsername(), u.getFullName(), u.getEmail(), u.getRole(), u.getDeptId()});
        } catch (Exception ex) { ex.printStackTrace(); JOptionPane.showMessageDialog(main,"Load failed"); }
    }

    private void addUser() {
        try {
            String username = JOptionPane.showInputDialog(main,"Username:");
            if (username==null||username.trim().isEmpty()) return;
            String pwd = JOptionPane.showInputDialog(main,"Password:");
            String full = JOptionPane.showInputDialog(main,"Full name:");
            String email = JOptionPane.showInputDialog(main,"Email:");
            String[] roles = {"admin","organizer","student"};
            String role = (String) JOptionPane.showInputDialog(main,"Role:", "Role", JOptionPane.PLAIN_MESSAGE, null, roles, roles[2]);
            String deptStr = JOptionPane.showInputDialog(main,"Dept ID (or leave blank):");
            Integer deptId = null; if (deptStr!=null && !deptStr.trim().isEmpty()) deptId = Integer.parseInt(deptStr);
            boolean ok = UserDAO.addUser(username, pwd, full, email, role, deptId);
            if (ok) { JOptionPane.showMessageDialog(main,"Added"); load(); }
            else JOptionPane.showMessageDialog(main,"Add failed");
        } catch (Exception ex) { ex.printStackTrace(); JOptionPane.showMessageDialog(main,"Add error"); }
    }

    private void editUser() {
        int r = table.getSelectedRow(); if (r==-1){ JOptionPane.showMessageDialog(main,"Select row"); return; }
        int id = (int) model.getValueAt(r,0);
        String curName = (String) model.getValueAt(r,2);
        String curRole = (String) model.getValueAt(r,4);
        String newName = JOptionPane.showInputDialog(main,"Full name:", curName);
        String[] roles = {"admin","organizer","student"};
        String newRole = (String) JOptionPane.showInputDialog(main,"Role:", "Role", JOptionPane.PLAIN_MESSAGE, null, roles, curRole);
        String deptStr = JOptionPane.showInputDialog(main,"Dept ID (blank to null):", model.getValueAt(r,5));
        Integer deptId = null; if (deptStr!=null && !deptStr.trim().isEmpty()) deptId = Integer.parseInt(deptStr);
        boolean ok = UserDAO.updateUser(id, newName, newRole, deptId);
        if (ok) { JOptionPane.showMessageDialog(main,"Updated"); load(); } else JOptionPane.showMessageDialog(main,"Update failed");
    }

    private void deleteUser() {
        int r = table.getSelectedRow(); if (r==-1){ JOptionPane.showMessageDialog(main,"Select row"); return; }
        int id = (int) model.getValueAt(r,0);
        int okc = JOptionPane.showConfirmDialog(main,"Delete user #"+id+"?","Confirm",JOptionPane.YES_NO_OPTION);
        if (okc!=JOptionPane.YES_OPTION) return;
        boolean ok = UserDAO.deleteUser(id);
        if (ok) { JOptionPane.showMessageDialog(main,"Deleted"); load(); } else JOptionPane.showMessageDialog(main,"Delete failed");
    }
}
