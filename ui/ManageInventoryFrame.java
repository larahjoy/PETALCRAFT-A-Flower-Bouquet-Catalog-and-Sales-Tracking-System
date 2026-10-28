/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.flowerbouquet.ui;

import com.mycompany.flowerbouquet.data.DatabaseConnection;
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
public class ManageInventoryFrame extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ManageInventoryFrame.class.getName());

    /**
     * Creates new form ManageInventoryFrame
     */
    public ManageInventoryFrame() {
    initComponents();
    loadCategoryOptions();
    loadInventory();
    updateInventorySummary();
    
    setupTableStyle(); 
    
    jPanel4.setMinimumSize(new java.awt.Dimension(314, 107));
    jPanel4.setPreferredSize(new java.awt.Dimension(314, 107));
    jPanel4.setMaximumSize(new java.awt.Dimension(314, 107));
        
    jPanel5.setMinimumSize(new java.awt.Dimension(338, 107));
    jPanel5.setPreferredSize(new java.awt.Dimension(338, 107));
    jPanel5.setMaximumSize(new java.awt.Dimension(338, 107));
    
    jPanel6.setMinimumSize(new java.awt.Dimension(327, 107));
    jPanel6.setPreferredSize(new java.awt.Dimension(327, 107));
    jPanel6.setMaximumSize(new java.awt.Dimension(327, 107));
    }
    
    private void setupTableStyle() {

    java.awt.Color lightPink = new java.awt.Color(252, 245, 253);
    java.awt.Color alternatePink = new java.awt.Color(246, 234, 249);
    java.awt.Color purple = new java.awt.Color(116, 105, 171);
    java.awt.Color textBlue = new java.awt.Color(46, 69, 121);
    java.awt.Color selectedPurple = new java.awt.Color(226, 213, 248);

    // Table background and selection
    tblInventory.setBackground(lightPink);
    tblInventory.setForeground(textBlue);
    tblInventory.setFont(
            new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14)
    );
    tblInventory.setRowHeight(48);
    tblInventory.setShowGrid(false);
    tblInventory.setIntercellSpacing(new java.awt.Dimension(0, 0));
    tblInventory.setSelectionBackground(selectedPurple);
    tblInventory.setSelectionForeground(textBlue);
    tblInventory.setFillsViewportHeight(true);

    // Background behind empty rows
    jScrollPane2.setBackground(lightPink);
    jScrollPane2.getViewport().setBackground(lightPink);
    jScrollPane2.setOpaque(true);
    jScrollPane2.getViewport().setOpaque(true);

    // Header renderer: explicitly paint purple with white text
    javax.swing.table.JTableHeader header = tblInventory.getTableHeader();

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

    // Apply the row renderer directly to EVERY column
    // This also covers numeric columns such as ID, Price and Quantity.
    javax.swing.table.DefaultTableCellRenderer cellRenderer =
            new javax.swing.table.DefaultTableCellRenderer() {

        @Override
        public java.awt.Component getTableCellRendererComponent(
                javax.swing.JTable table,
                Object value,
                boolean isSelected,
                boolean hasFocus,
                int row,
                int column) {

            java.awt.Component component =
                    super.getTableCellRendererComponent(
                            table, value, isSelected, hasFocus, row, column
                    );

            setOpaque(true);
            setHorizontalAlignment(javax.swing.JLabel.CENTER);

            if (isSelected) {
                component.setBackground(selectedPurple);
                component.setForeground(textBlue);
            } else {
                component.setBackground(
                        row % 2 == 0 ? lightPink : alternatePink
                );

                // Highlight low stock in red (Quantity is column 5)
                if (column == 5 && value != null) {
                    try {
                        int quantity = Integer.parseInt(value.toString());
                        component.setForeground(
                                quantity <= 5
                                ? java.awt.Color.RED
                                : textBlue
                        );
                    } catch (NumberFormatException ex) {
                        component.setForeground(textBlue);
                    }
                } else {
                    component.setForeground(textBlue);
                }
            }

            return component;
        }
    };

    for (int i = 0; i < tblInventory.getColumnCount(); i++) {
        tblInventory.getColumnModel()
                .getColumn(i)
                .setCellRenderer(cellRenderer);
    }
}
    
    private void loadCategoryOptions() {
    cmbCategory.removeAllItems();
    cmbCategory.addItem("All Categories");

    String sql = "SELECT category_name FROM categories ORDER BY category_name";

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            cmbCategory.addItem(rs.getString("category_name"));
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(
                this,
                "Failed to load categories: " + e.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
}
    
   private void updateInventorySummary() {
    String sql = "SELECT COUNT(*) AS total_types FROM bouquets WHERE is_archived = 0";

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        if (rs.next()) {
            lblTotalBouquetTypes.setText(
                    "Total Bouquet Types: " + rs.getInt("total_types")
            );
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(
                this,
                "Failed to load inventory summary: " + e.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
}
    
    private void loadInventory() {
    DefaultTableModel model =
            (DefaultTableModel) tblInventory.getModel();

    model.setRowCount(0);

    String keyword = txtSearch.getText().trim();
    String selectedCategory = cmbCategory.getSelectedItem() == null
            ? "All Categories"
            : cmbCategory.getSelectedItem().toString();

    String sql = """
        SELECT b.bouquet_id, b.bouquet_name, b.price, b.quantity,
               c.category_name
        FROM bouquets b
        LEFT JOIN categories c ON b.category_id = c.category_id
        WHERE b.is_archived = 0
          AND (b.bouquet_name LIKE ?
               OR COALESCE(c.category_name, 'Uncategorized') LIKE ?)
          AND (? = 'All Categories'
               OR COALESCE(c.category_name, 'Uncategorized') = ?)
        ORDER BY b.bouquet_name
        """;

    int totalStock = 0;
    int lowStockTypes = 0;

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        String searchPattern = "%" + keyword + "%";
        stmt.setString(1, searchPattern);
        stmt.setString(2, searchPattern);
        stmt.setString(3, selectedCategory);
        stmt.setString(4, selectedCategory);

        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int quantity = rs.getInt("quantity");
                String category = rs.getString("category_name");

                if (category == null || category.trim().isEmpty()) {
                    category = "Uncategorized";
                }

                String status;
                if (quantity == 0) {
                    status = "Out of Stock";
                } else if (quantity <= 5) {
                    status = "Low Stock";
                } else {
                    status = "In Stock";
                }

                model.addRow(new Object[]{
                    rs.getInt("bouquet_id"),
                    rs.getString("bouquet_name"),
                    category,
                    String.format(java.util.Locale.US, "₱%.2f",
                            rs.getDouble("price")),
                    quantity,
                    status
                });
            }
        }

        // Calculate totals across all active bouquets, not just filtered rows.
        String summarySql = """
            SELECT COALESCE(SUM(quantity), 0) AS total_stock,
                   COALESCE(SUM(CASE WHEN quantity <= 5 THEN 1 ELSE 0 END), 0)
                       AS low_stock_types
            FROM bouquets
            WHERE is_archived = 0
            """;

        try (PreparedStatement summaryStmt = conn.prepareStatement(summarySql);
             ResultSet summaryRs = summaryStmt.executeQuery()) {

            if (summaryRs.next()) {
                totalStock = summaryRs.getInt("total_stock");
                lowStockTypes = summaryRs.getInt("low_stock_types");
            }
        }

        lblTotalStock.setText("Total Stock: " + totalStock);
        lblLowStock.setText("Low Stock Types: " + lowStockTypes);

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(
                this,
                "Failed to load inventory: " + e.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE
        );
    }

    updateInventorySummary();
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
        lblTitle = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        lblTotalStock = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        lblLowStock = new javax.swing.JLabel();
        btnRestock = new javax.swing.JButton();
        btnEditStock = new javax.swing.JButton();
        cmbCategory = new javax.swing.JComboBox<>();
        txtSearch = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        lblSearch = new javax.swing.JLabel();
        btnRefresh = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        lblTotalBouquetTypes = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblInventory = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(246, 234, 249));

        lblTitle.setBackground(new java.awt.Color(91, 58, 115));
        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(91, 58, 115));
        lblTitle.setText("Manage Inventory");

        jPanel3.setBackground(new java.awt.Color(249, 239, 252));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 232, 247), 4));
        jPanel3.setToolTipText("");
        jPanel3.setMaximumSize(new java.awt.Dimension(943, 546));
        jPanel3.setMinimumSize(new java.awt.Dimension(943, 546));

        jPanel4.setBackground(new java.awt.Color(252, 245, 253));
        jPanel4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(239, 232, 247), 4, true));

        lblTotalStock.setFont(new java.awt.Font("Segoe UI Semibold", 1, 24)); // NOI18N
        lblTotalStock.setForeground(new java.awt.Color(46, 69, 121));
        lblTotalStock.setText("Total Stock:");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(lblTotalStock)
                .addContainerGap(138, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(lblTotalStock)
                .addContainerGap(34, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(252, 245, 253));
        jPanel5.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(239, 232, 247), 4, true));
        jPanel5.setMaximumSize(new java.awt.Dimension(468, 107));

        lblLowStock.setFont(new java.awt.Font("Segoe UI Semibold", 1, 24)); // NOI18N
        lblLowStock.setForeground(new java.awt.Color(46, 69, 121));
        lblLowStock.setText("Low Stock:");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(lblLowStock)
                .addContainerGap(168, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(lblLowStock)
                .addContainerGap(34, Short.MAX_VALUE))
        );

        btnRestock.setBackground(new java.awt.Color(116, 105, 171));
        btnRestock.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnRestock.setForeground(new java.awt.Color(255, 255, 255));
        btnRestock.setText("Add Stock");
        btnRestock.addActionListener(this::btnRestockActionPerformed);

        btnEditStock.setBackground(new java.awt.Color(116, 105, 171));
        btnEditStock.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnEditStock.setForeground(new java.awt.Color(255, 255, 255));
        btnEditStock.setText("Edit Stock");
        btnEditStock.addActionListener(this::btnEditStockActionPerformed);

        cmbCategory.setBackground(new java.awt.Color(255, 255, 255));
        cmbCategory.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbCategory.addActionListener(this::cmbCategoryActionPerformed);

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

        lblTotalBouquetTypes.setFont(new java.awt.Font("Segoe UI Semibold", 1, 24)); // NOI18N
        lblTotalBouquetTypes.setForeground(new java.awt.Color(46, 69, 121));
        lblTotalBouquetTypes.setText("Total Bouquet Types:");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(lblTotalBouquetTypes)
                .addContainerGap(74, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(lblTotalBouquetTypes)
                .addContainerGap(34, Short.MAX_VALUE))
        );

        tblInventory.setBackground(new java.awt.Color(252, 245, 253));
        tblInventory.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        tblInventory.setForeground(new java.awt.Color(46, 69, 121));
        tblInventory.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Bouquet Name", "Category", "Price", "Stock", "Stock Status"
            }
        ));
        jScrollPane2.setViewportView(tblInventory);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(187, 187, 187))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(lblSearch)
                            .addGap(18, 18, 18)
                            .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(cmbCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(btnRestock, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnEditStock, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(184, 184, 184)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(177, 177, 177))))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(148, 148, 148)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1053, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 155, Short.MAX_VALUE))
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
                        .addComponent(cmbCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnRestock, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnEditStock, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(lblTitle)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(56, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(lblTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(54, 54, 54))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        loadCategoryOptions();
    loadInventory();
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchActionPerformed

    private void btnRestockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRestockActionPerformed
        int row = tblInventory.getSelectedRow();

    if (row == -1) {
        JOptionPane.showMessageDialog(this, "Select a bouquet first.");
        return;
    }

    int modelRow = tblInventory.convertRowIndexToModel(row);
    int bouquetId = Integer.parseInt(
            tblInventory.getModel().getValueAt(modelRow, 0).toString());

    String input = JOptionPane.showInputDialog(
            this, "Enter additional stock quantity:");

    if (input == null) {
        return;
    }

    try {
        int additionalStock = Integer.parseInt(input.trim());

        if (additionalStock <= 0) {
            JOptionPane.showMessageDialog(
                    this, "Quantity must be greater than zero.");
            return;
        }

        String sql = """
            UPDATE bouquets
            SET quantity = quantity + ?
            WHERE bouquet_id = ? AND is_archived = 0
            """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, additionalStock);
            stmt.setInt(2, bouquetId);

            if (stmt.executeUpdate() == 1) {
                loadInventory();
                JOptionPane.showMessageDialog(
                        this, "Stock updated successfully.");
            } else {
                JOptionPane.showMessageDialog(
                        this, "Bouquet not found or archived.");
            }
        }

    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(
                this, "Enter a valid whole number.");
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(
                this, "Failed to restock: " + e.getMessage(),
                "Database Error", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_btnRestockActionPerformed

    private void cmbCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbCategoryActionPerformed
        loadInventory();
    }//GEN-LAST:event_cmbCategoryActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        loadInventory();
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnEditStockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditStockActionPerformed
        int row = tblInventory.getSelectedRow();

    if (row == -1) {
        JOptionPane.showMessageDialog(this, "Select a bouquet first.");
        return;
    }

    int modelRow = tblInventory.convertRowIndexToModel(row);
    int bouquetId = Integer.parseInt(
            tblInventory.getModel().getValueAt(modelRow, 0).toString());
    int currentQuantity = Integer.parseInt(
            tblInventory.getModel().getValueAt(modelRow, 4).toString());

    String input = JOptionPane.showInputDialog(
            this, "Enter the new stock quantity:", currentQuantity);

    if (input == null) {
        return;
    }

    try {
        int quantity = Integer.parseInt(input.trim());

        if (quantity < 0) {
            JOptionPane.showMessageDialog(
                    this, "Stock cannot be negative.");
            return;
        }

        String sql = """
            UPDATE bouquets
            SET quantity = ?
            WHERE bouquet_id = ? AND is_archived = 0
            """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, quantity);
            stmt.setInt(2, bouquetId);

            if (stmt.executeUpdate() == 1) {
                loadInventory();
                JOptionPane.showMessageDialog(
                        this, "Inventory quantity updated.");
            } else {
                JOptionPane.showMessageDialog(
                        this, "Bouquet not found or archived.");
            }
        }

    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(
                this, "Enter a valid whole number.");
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(
                this, "Failed to update stock: " + e.getMessage(),
                "Database Error", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_btnEditStockActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new ManageInventoryFrame().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEditStock;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnRestock;
    private javax.swing.JButton btnSearch;
    private javax.swing.JComboBox<String> cmbCategory;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblLowStock;
    private javax.swing.JLabel lblSearch;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblTotalBouquetTypes;
    private javax.swing.JLabel lblTotalStock;
    private javax.swing.JTable tblInventory;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
