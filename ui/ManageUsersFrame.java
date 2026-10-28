/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.flowerbouquet.ui;
import com.mycompany.flowerbouquet.data.DatabaseConnection;
import com.mycompany.flowerbouquet.data.DataStore;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Paul Adanza
 */
public class ManageUsersFrame extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ManageUsersFrame.class.getName());

    /**
     * Creates new form ManageUsersFrame
     */
    public ManageUsersFrame() {
        initComponents();
        setupTableStyle();
        
        cmbRoleFilter.setModel(new javax.swing.DefaultComboBoxModel<>(
        new String[] {"All Roles", "Admin", "Staff"}
                
        ));
        
        loadUsers();
        updateUserSummary();
        
        
        
    jPanel4.setMinimumSize(new java.awt.Dimension(314, 107));
    jPanel4.setPreferredSize(new java.awt.Dimension(314, 107));
    jPanel4.setMaximumSize(new java.awt.Dimension(314, 107));
        
    jPanel5.setMinimumSize(new java.awt.Dimension(338, 107));
    jPanel5.setPreferredSize(new java.awt.Dimension(338, 107));
    jPanel5.setMaximumSize(new java.awt.Dimension(338, 107));
    
    jPanel6.setMinimumSize(new java.awt.Dimension(326, 107));
    jPanel6.setPreferredSize(new java.awt.Dimension(326, 107));
    jPanel6.setMaximumSize(new java.awt.Dimension(326, 107));
    
    }
    
    private void updateUserSummary() {
    String sql = """
        SELECT COUNT(*) AS total_users,
               COALESCE(SUM(CASE WHEN role = 'Admin' THEN 1 ELSE 0 END), 0)
                   AS total_admins,
               COALESCE(SUM(CASE WHEN role = 'Staff' THEN 1 ELSE 0 END), 0)
                   AS total_staff
        FROM users
        """;

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        if (rs.next()) {
            lblTotalUsers.setText(
                    "Total Users: " + rs.getInt("total_users"));
            lblTotalAdmins.setText(
                    "Administrators: " + rs.getInt("total_admins"));
            lblTotalStaff.setText(
                    "Staff: " + rs.getInt("total_staff"));
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(
                this, "Failed to load user summary: " + e.getMessage(),
                "Database Error", JOptionPane.ERROR_MESSAGE);
    }
}
    
private void loadUsers() {
    DefaultTableModel model =
            (DefaultTableModel) tblUsers.getModel();

    model.setRowCount(0);

    String keyword = txtSearch.getText().trim();
    String roleFilter = cmbRoleFilter.getSelectedItem() == null
            ? "All Roles"
            : cmbRoleFilter.getSelectedItem().toString();

    String sql = """
        SELECT user_id, first_name, last_name, username, role
        FROM users
        WHERE (
            first_name LIKE ?
            OR last_name LIKE ?
            OR username LIKE ?
        )
        AND (? = 'All Roles' OR role = ?)
        ORDER BY user_id
        """;

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        String pattern = "%" + keyword + "%";
        stmt.setString(1, pattern);
        stmt.setString(2, pattern);
        stmt.setString(3, pattern);
        stmt.setString(4, roleFilter);
        stmt.setString(5, roleFilter);

        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("user_id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("username"),
                    rs.getString("role")
                });
            }
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(
                this, "Failed to load users: " + e.getMessage(),
                "Database Error", JOptionPane.ERROR_MESSAGE);
    }
}

    
private String findUsernameById(int userId) {
    String sql = "SELECT username FROM users WHERE user_id = ?";

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, userId);

        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getString("username");
            }
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(
                this, "Failed to find user: " + e.getMessage(),
                "Database Error", JOptionPane.ERROR_MESSAGE);
    }

    return null;
}

private void setupTableStyle() {

    java.awt.Color lightPink = new java.awt.Color(252, 245, 253);
    java.awt.Color alternatePink = new java.awt.Color(246, 234, 249);
    java.awt.Color purple = new java.awt.Color(116, 105, 171);
    java.awt.Color textBlue = new java.awt.Color(46, 69, 121);
    java.awt.Color selectedPurple = new java.awt.Color(226, 213, 248);

    // Table background and selection
    tblUsers.setBackground(lightPink);
    tblUsers.setForeground(textBlue);
    tblUsers.setFont(
            new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14)
    );
    tblUsers.setRowHeight(48);
    tblUsers.setShowGrid(false);
    tblUsers.setIntercellSpacing(new java.awt.Dimension(0, 0));
    tblUsers.setSelectionBackground(selectedPurple);
    tblUsers.setSelectionForeground(textBlue);
    tblUsers.setFillsViewportHeight(true);

    // Background behind empty rows
    jScrollPane2.setBackground(lightPink);
    jScrollPane2.getViewport().setBackground(lightPink);
    jScrollPane2.setOpaque(true);
    jScrollPane2.getViewport().setOpaque(true);

    // Header renderer: explicitly paint purple with white text
    javax.swing.table.JTableHeader header = tblUsers.getTableHeader();

    header.setOpaque(true);
    header.setBackground(purple);
    header.setForeground(java.awt.Color.WHITE);
    header.setFont(
            new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14)
    );
    header.setPreferredSize(new java.awt.Dimension(0, 44));
    header.setReorderingAllowed(false);

    header.setDefaultRenderer(
            new javax.swing.table.DefaultTableCellRenderer() {
        @Override
        public java.awt.Component getTableCellRendererComponent(
                javax.swing.JTable table,
                Object value,
                boolean isSelected,
                boolean hasFocus,
                int row,
                int column) {

            javax.swing.JLabel label =
                    (javax.swing.JLabel) super.getTableCellRendererComponent(
                            table, value, isSelected, hasFocus, row, column
                    );

            label.setOpaque(true);
            label.setBackground(purple);
            label.setForeground(java.awt.Color.WHITE);
            label.setFont(
                    new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14)
            );
            label.setHorizontalAlignment(javax.swing.JLabel.CENTER);
            label.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 0, 5));

            return label;
        }
    });
}
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        lblTotalAdmins = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        lblTotalStaff = new javax.swing.JLabel();
        btnAddUser = new javax.swing.JButton();
        btnEditUser = new javax.swing.JButton();
        cmbRoleFilter = new javax.swing.JComboBox<>();
        txtSearch = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        lblSearch = new javax.swing.JLabel();
        btnRefresh = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        lblTotalUsers = new javax.swing.JLabel();
        btnDeleteUser = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblUsers = new javax.swing.JTable();
        lblTitle = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(246, 234, 249));

        jPanel3.setBackground(new java.awt.Color(249, 239, 252));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 232, 247), 4));
        jPanel3.setToolTipText("");
        jPanel3.setMaximumSize(new java.awt.Dimension(943, 546));
        jPanel3.setMinimumSize(new java.awt.Dimension(943, 546));

        jPanel4.setBackground(new java.awt.Color(252, 245, 253));
        jPanel4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(239, 232, 247), 4, true));

        lblTotalAdmins.setFont(new java.awt.Font("Segoe UI Semibold", 1, 24)); // NOI18N
        lblTotalAdmins.setForeground(new java.awt.Color(46, 69, 121));
        lblTotalAdmins.setText("Administrators:");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(lblTotalAdmins)
                .addContainerGap(110, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(lblTotalAdmins)
                .addContainerGap(34, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(252, 245, 253));
        jPanel5.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(239, 232, 247), 4, true));
        jPanel5.setMaximumSize(new java.awt.Dimension(468, 107));

        lblTotalStaff.setFont(new java.awt.Font("Segoe UI Semibold", 1, 24)); // NOI18N
        lblTotalStaff.setForeground(new java.awt.Color(46, 69, 121));
        lblTotalStaff.setText("Staff:");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(lblTotalStaff)
                .addContainerGap(230, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(34, Short.MAX_VALUE)
                .addComponent(lblTotalStaff)
                .addGap(33, 33, 33))
        );

        btnAddUser.setBackground(new java.awt.Color(116, 105, 171));
        btnAddUser.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnAddUser.setForeground(new java.awt.Color(255, 255, 255));
        btnAddUser.setText("Add User");
        btnAddUser.addActionListener(this::btnAddUserActionPerformed);

        btnEditUser.setBackground(new java.awt.Color(116, 105, 171));
        btnEditUser.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnEditUser.setForeground(new java.awt.Color(255, 255, 255));
        btnEditUser.setText("Edit User");
        btnEditUser.addActionListener(this::btnEditUserActionPerformed);

        cmbRoleFilter.setBackground(new java.awt.Color(255, 255, 255));
        cmbRoleFilter.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbRoleFilter.addActionListener(this::cmbRoleFilterActionPerformed);

        txtSearch.setBackground(new java.awt.Color(255, 255, 255));
        txtSearch.addActionListener(this::txtSearchActionPerformed);

        btnSearch.setBackground(new java.awt.Color(116, 105, 171));
        btnSearch.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnSearch.setForeground(new java.awt.Color(255, 255, 255));
        btnSearch.setText("Search");
        btnSearch.addActionListener(this::btnSearchActionPerformed);

        lblSearch.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        lblSearch.setForeground(new java.awt.Color(46, 69, 121));
        lblSearch.setText("Search Keyword:");

        btnRefresh.setBackground(new java.awt.Color(116, 105, 171));
        btnRefresh.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnRefresh.setForeground(new java.awt.Color(255, 255, 255));
        btnRefresh.setText("Refresh");
        btnRefresh.addActionListener(this::btnRefreshActionPerformed);

        jPanel6.setBackground(new java.awt.Color(252, 245, 253));
        jPanel6.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(239, 232, 247), 4, true));

        lblTotalUsers.setFont(new java.awt.Font("Segoe UI Semibold", 1, 24)); // NOI18N
        lblTotalUsers.setForeground(new java.awt.Color(46, 69, 121));
        lblTotalUsers.setText("Total Users:");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(lblTotalUsers)
                .addContainerGap(148, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(lblTotalUsers)
                .addContainerGap(34, Short.MAX_VALUE))
        );

        btnDeleteUser.setBackground(new java.awt.Color(221, 71, 104));
        btnDeleteUser.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnDeleteUser.setForeground(new java.awt.Color(255, 255, 255));
        btnDeleteUser.setText("Delete User");
        btnDeleteUser.addActionListener(this::btnDeleteUserActionPerformed);

        tblUsers.setBackground(new java.awt.Color(252, 245, 253));
        tblUsers.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        tblUsers.setForeground(new java.awt.Color(46, 69, 121));
        tblUsers.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "User ID", "First Name", "Last Name", "Username", "Role"
            }
        ));
        jScrollPane2.setViewportView(tblUsers);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(188, 188, 188))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(181, 181, 181))))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(141, 141, 141)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(lblSearch)
                        .addGap(18, 18, 18)
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cmbRoleFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnAddUser, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEditUser, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDeleteUser, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(144, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(17, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(lblSearch))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cmbRoleFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnAddUser, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnEditUser, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnDeleteUser, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );

        lblTitle.setBackground(new java.awt.Color(91, 58, 115));
        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(91, 58, 115));
        lblTitle.setText("Manage Users");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(60, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(lblTitle)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(lblTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddUserActionPerformed
    String firstName = JOptionPane.showInputDialog(
            this, "Enter first name:");

    if (firstName == null || firstName.trim().isEmpty()) {
        return;
    }

    String lastName = JOptionPane.showInputDialog(
            this, "Enter last name:");

    if (lastName == null || lastName.trim().isEmpty()) {
        return;
    }

    String username = JOptionPane.showInputDialog(
            this, "Enter username:");

    if (username == null || username.trim().isEmpty()) {
        return;
    }

    username = username.trim();

    javax.swing.JPasswordField passwordField =
            new javax.swing.JPasswordField();

    int result = JOptionPane.showConfirmDialog(
            this, passwordField, "Enter password",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE);

    if (result != JOptionPane.OK_OPTION
            || passwordField.getPassword().length == 0) {
        return;
    }

    String password = new String(passwordField.getPassword());

    String[] roles = {"Staff", "Admin"};

    String role = (String) JOptionPane.showInputDialog(
            this, "Select account role:", "Account Role",
            JOptionPane.QUESTION_MESSAGE, null, roles, roles[0]);

    if (role == null) {
        return;
    }

    String checkSql = "SELECT user_id FROM users WHERE username = ?";

    String insertSql = """
        INSERT INTO users
            (username, password_hash, role, first_name, last_name)
        VALUES (?, ?, ?, ?, ?)
        """;

    try (Connection conn = DatabaseConnection.getConnection()) {

        try (PreparedStatement check = conn.prepareStatement(checkSql)) {
            check.setString(1, username);

            try (ResultSet rs = check.executeQuery()) {
                if (rs.next()) {
                    JOptionPane.showMessageDialog(
                            this, "Username already exists.");
                    return;
                }
            }
        }

        try (PreparedStatement insert = conn.prepareStatement(insertSql)) {
            insert.setString(1, username);
            insert.setString(2, password);
            insert.setString(3, role);
            insert.setString(4, firstName.trim());
            insert.setString(5, lastName.trim());

            insert.executeUpdate();
        }

        loadUsers();
        updateUserSummary();

        JOptionPane.showMessageDialog(
                this, "User account created successfully.");

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(
                this, "Failed to create user: " + e.getMessage(),
                "Database Error", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_btnAddUserActionPerformed

    private void btnEditUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditUserActionPerformed
    int row = tblUsers.getSelectedRow();

    if (row == -1) {
        JOptionPane.showMessageDialog(this, "Please select a user first.");
        return;
    }

    int modelRow = tblUsers.convertRowIndexToModel(row);
    int userId = Integer.parseInt(
            tblUsers.getModel().getValueAt(modelRow, 0).toString());

    if (userId == DataStore.currentUserId) {
        JOptionPane.showMessageDialog(
                this, "You cannot change your own role here.");
        return;
    }

    String currentRole = tblUsers.getModel()
            .getValueAt(modelRow, 4).toString();

    String[] roles = {"Admin", "Staff"};

    String newRole = (String) JOptionPane.showInputDialog(
            this, "Select new role:", "Edit User Role",
            JOptionPane.QUESTION_MESSAGE, null, roles, currentRole);

    if (newRole == null) {
        return;
    }

    String sql = "UPDATE users SET role = ? WHERE user_id = ?";

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, newRole);
        stmt.setInt(2, userId);

        if (stmt.executeUpdate() == 1) {
            loadUsers();
            updateUserSummary();
            JOptionPane.showMessageDialog(this, "User role updated.");
        } else {
            JOptionPane.showMessageDialog(this, "User not found.");
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(
                this, "Failed to update role: " + e.getMessage(),
                "Database Error", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_btnEditUserActionPerformed

    private void cmbRoleFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbRoleFilterActionPerformed
        loadUsers();
    }//GEN-LAST:event_cmbRoleFilterActionPerformed

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        loadUsers();
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        loadUsers();
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void btnDeleteUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteUserActionPerformed
    int row = tblUsers.getSelectedRow();

    if (row == -1) {
        JOptionPane.showMessageDialog(this, "Please select a user first.");
        return;
    }

    int modelRow = tblUsers.convertRowIndexToModel(row);
    int userId = Integer.parseInt(
            tblUsers.getModel().getValueAt(modelRow, 0).toString());

    String username = tblUsers.getModel()
            .getValueAt(modelRow, 3).toString();

    if (userId == DataStore.currentUserId) {
        JOptionPane.showMessageDialog(
                this, "You cannot delete your own account.");
        return;
    }

    int confirm = JOptionPane.showConfirmDialog(
            this,
            "Delete account for " + username + "?",
            "Confirm Delete",
            JOptionPane.YES_NO_OPTION);

    if (confirm != JOptionPane.YES_OPTION) {
        return;
    }

    String sql = "DELETE FROM users WHERE user_id = ?";

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, userId);

        if (stmt.executeUpdate() == 1) {
            loadUsers();
            updateUserSummary();
            JOptionPane.showMessageDialog(this, "User deleted successfully.");
        } else {
            JOptionPane.showMessageDialog(this, "User not found.");
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(
                this,
                "Failed to delete user. This account may be referenced by sales.\n"
                + e.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_btnDeleteUserActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new ManageUsersFrame().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddUser;
    private javax.swing.JButton btnDeleteUser;
    private javax.swing.JButton btnEditUser;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnSearch;
    private javax.swing.JComboBox<String> cmbRoleFilter;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblSearch;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblTotalAdmins;
    private javax.swing.JLabel lblTotalStaff;
    private javax.swing.JLabel lblTotalUsers;
    private javax.swing.JTable tblUsers;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
