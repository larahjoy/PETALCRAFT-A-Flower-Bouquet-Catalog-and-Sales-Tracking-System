/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.flowerbouquet.ui;

import com.mycompany.flowerbouquet.data.DataStore;
import com.mycompany.flowerbouquet.data.DatabaseConnection;
import com.mycompany.flowerbouquet.model.Bouquet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Paul Adanza
 */
public class SoldBouquetsFrame extends javax.swing.JFrame {
    
    private void loadBouquets() {
    cmbBouquet.removeAllItems();

    String sql = """
        SELECT bouquet_id, bouquet_name, quantity
        FROM bouquets
        WHERE is_archived = 0 AND quantity > 0
        ORDER BY bouquet_name
    """;

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            cmbBouquet.addItem(
                rs.getInt("bouquet_id") + " - "
                + rs.getString("bouquet_name") + " - "
                + rs.getInt("quantity")
            );
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(
            this,
            "Unable to load bouquets: " + e.getMessage(),
            "Database Error",
            JOptionPane.ERROR_MESSAGE
        );
    }
}
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(SoldBouquetsFrame.class.getName());

    public SoldBouquetsFrame() {
        initComponents();
        setupTableStyle();
        loadBouquets();
        setSize(1475, 750);
        setLocationRelativeTo(null);
        loadBouquets();
        loadSales();
        
        jScrollPane1.setBorder(null);
        jScrollPane1.setOpaque(true);
        jScrollPane1.setFocusable(false);
    }
    
    private void loadSales() {
    DefaultTableModel model =
        (DefaultTableModel) tblSales.getModel();

    model.setRowCount(0);

    String sql = """
        SELECT s.sale_id,
               b.bouquet_name,
               s.customer_name,
               sd.quantity,
               (sd.quantity * sd.unit_price) AS subtotal,
               s.sale_date
        FROM sales s
        JOIN sale_details sd ON s.sale_id = sd.sale_id
        LEFT JOIN bouquets b ON sd.bouquet_id = b.bouquet_id
        ORDER BY s.sale_date DESC, s.sale_id DESC
    """;

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getInt("sale_id"),
                rs.getString("bouquet_name") == null
                    ? "Unknown" : rs.getString("bouquet_name"),
                rs.getString("customer_name"),
                rs.getInt("quantity"),
                rs.getBigDecimal("subtotal"),
                rs.getTimestamp("sale_date")
            });
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(
            this,
            "Unable to load sales: " + e.getMessage(),
            "Database Error",
            JOptionPane.ERROR_MESSAGE
        );
    }
}
    
    private void setupTableStyle() {

    java.awt.Color lightPink = new java.awt.Color(252, 245, 253);
    java.awt.Color alternatePink = new java.awt.Color(246, 234, 249);
    java.awt.Color purple = new java.awt.Color(116, 105, 171);
    java.awt.Color textBlue = new java.awt.Color(46, 69, 121);
    java.awt.Color selectedPurple = new java.awt.Color(226, 213, 248);

    // Table background and selection
    tblSales.setBackground(lightPink);
    tblSales.setForeground(textBlue);
    tblSales.setFont(
            new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14)
    );
    tblSales.setRowHeight(48);
    tblSales.setShowGrid(false);
    tblSales.setIntercellSpacing(new java.awt.Dimension(0, 0));
    tblSales.setSelectionBackground(selectedPurple);
    tblSales.setSelectionForeground(textBlue);
    tblSales.setFillsViewportHeight(true);

    // Background behind empty rows
    jScrollPane1.setBackground(lightPink);
    jScrollPane1.getViewport().setBackground(lightPink);
    jScrollPane1.setOpaque(true);
    jScrollPane1.getViewport().setOpaque(true);

    // Header renderer: explicitly paint purple with white text
    javax.swing.table.JTableHeader header = tblSales.getTableHeader();

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

    for (int i = 0; i < tblSales.getColumnCount(); i++) {
        tblSales.getColumnModel()
                .getColumn(i)
                .setCellRenderer(cellRenderer);
    }
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        lblBouquet = new javax.swing.JLabel();
        cmbBouquet = new javax.swing.JComboBox<>();
        txtCustomer = new javax.swing.JTextField();
        lblCustomer = new javax.swing.JLabel();
        lblQuantity = new javax.swing.JLabel();
        txtQuantity = new javax.swing.JTextField();
        btnRecord = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        scrollPane = new javax.swing.JScrollPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSales = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1200, 660));

        jPanel1.setBackground(new java.awt.Color(246, 234, 249));
        jPanel1.setMaximumSize(new java.awt.Dimension(1200, 660));
        jPanel1.setMinimumSize(new java.awt.Dimension(1200, 660));
        jPanel1.setPreferredSize(new java.awt.Dimension(1200, 660));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(91, 58, 115));
        jLabel1.setText("Record Sold Bouquet");

        jPanel2.setBackground(new java.awt.Color(252, 245, 253));
        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(239, 232, 247), 4, true));
        jPanel2.setMaximumSize(new java.awt.Dimension(427, 498));
        jPanel2.setMinimumSize(new java.awt.Dimension(427, 498));

        lblBouquet.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        lblBouquet.setForeground(new java.awt.Color(46, 69, 121));
        lblBouquet.setText("Bouquet:");

        cmbBouquet.setBackground(new java.awt.Color(255, 255, 255));
        cmbBouquet.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbBouquet.addActionListener(this::cmbBouquetActionPerformed);

        txtCustomer.setBackground(new java.awt.Color(255, 255, 255));
        txtCustomer.addActionListener(this::txtCustomerActionPerformed);

        lblCustomer.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        lblCustomer.setForeground(new java.awt.Color(46, 69, 121));
        lblCustomer.setText("Costumer:");

        lblQuantity.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        lblQuantity.setForeground(new java.awt.Color(46, 69, 121));
        lblQuantity.setText("Quantity:");

        txtQuantity.setBackground(new java.awt.Color(255, 255, 255));
        txtQuantity.addActionListener(this::txtQuantityActionPerformed);

        btnRecord.setBackground(new java.awt.Color(116, 105, 171));
        btnRecord.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnRecord.setForeground(new java.awt.Color(255, 255, 255));
        btnRecord.setText("Record Sale");
        btnRecord.addActionListener(this::btnRecordActionPerformed);

        jLabel6.setFont(new java.awt.Font("Segoe UI Semibold", 0, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(46, 69, 121));
        jLabel6.setText("Sale Details:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel6))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lblBouquet)
                        .addGap(40, 40, 40)
                        .addComponent(cmbBouquet, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lblCustomer)
                        .addGap(31, 31, 31)
                        .addComponent(txtCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lblQuantity)
                        .addGap(39, 39, 39)
                        .addComponent(txtQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnRecord, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(59, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(jLabel6)
                .addGap(42, 42, 42)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(lblBouquet))
                    .addComponent(cmbBouquet, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblCustomer)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(txtCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(37, 37, 37)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(lblQuantity))
                    .addComponent(txtQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(69, 69, 69)
                .addComponent(btnRecord, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(188, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(252, 245, 253));
        jPanel3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(239, 232, 247), 4, true));
        jPanel3.setMaximumSize(new java.awt.Dimension(703, 95));
        jPanel3.setMinimumSize(new java.awt.Dimension(703, 95));
        jPanel3.setLayout(new java.awt.GridBagLayout());

        scrollPane.setBackground(new java.awt.Color(255, 255, 255));

        tblSales.setBackground(new java.awt.Color(252, 245, 253));
        tblSales.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        tblSales.setForeground(new java.awt.Color(46, 69, 121));
        tblSales.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Sale ID", "Bouquet Name", "Customer", "Quantity", "Total", "Date"
            }
        ));
        jScrollPane1.setViewportView(tblSales);

        scrollPane.setViewportView(jScrollPane1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 645;
        gridBagConstraints.ipady = 335;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(12, 22, 23, 14);
        jPanel3.add(scrollPane, gridBagConstraints);

        jLabel5.setFont(new java.awt.Font("Segoe UI Semibold", 0, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(46, 69, 121));
        jLabel5.setText("Sold Bouquets:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(36, 47, 0, 0);
        jPanel3.add(jLabel5, gridBagConstraints);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 901, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(jLabel1))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(jLabel1)
                .addGap(38, 38, 38)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 589, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1475, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 750, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRecordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRecordActionPerformed
    if (cmbBouquet.getSelectedItem() == null) {
        JOptionPane.showMessageDialog(this, "No bouquet available.");
        return;
    }

    String customer = txtCustomer.getText().trim();
    String quantityText = txtQuantity.getText().trim();

    if (customer.isEmpty() || quantityText.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please fill in all fields.");
        return;
    }

    int quantity;

    try {
        quantity = Integer.parseInt(quantityText);
        if (quantity <= 0) {
            JOptionPane.showMessageDialog(
                this, "Quantity must be greater than 0."
            );
            return;
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(
            this, "Please enter a valid quantity."
        );
        return;
    }

    String selectedItem = cmbBouquet.getSelectedItem().toString();
    int bouquetId;

    try {
        bouquetId = Integer.parseInt(
            selectedItem.substring(0, selectedItem.indexOf("-")).trim()
        );
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Invalid bouquet selection.");
        return;
    }

    String stockSql = """
        SELECT bouquet_name, price, quantity
        FROM bouquets
        WHERE bouquet_id = ? AND is_archived = 0
        FOR UPDATE
    """;

    String saleSql = """
        INSERT INTO sales (user_id, customer_name, total_amount)
        VALUES (?, ?, ?)
    """;

    String detailSql = """
        INSERT INTO sale_details (sale_id, bouquet_id, quantity, unit_price)
        VALUES (?, ?, ?, ?)
    """;

    String updateStockSql = """
        UPDATE bouquets
        SET quantity = quantity - ?
        WHERE bouquet_id = ? AND quantity >= ?
    """;

    try (Connection conn = DatabaseConnection.getConnection()) {
        conn.setAutoCommit(false);

        try {
            String bouquetName;
            java.math.BigDecimal price;
            int availableStock;

            try (PreparedStatement stmt = conn.prepareStatement(stockSql)) {
                stmt.setInt(1, bouquetId);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (!rs.next()) {
                        throw new SQLException(
                            "Bouquet not found or archived."
                        );
                    }

                    bouquetName = rs.getString("bouquet_name");
                    price = rs.getBigDecimal("price");
                    availableStock = rs.getInt("quantity");
                }
            }

            if (quantity > availableStock) {
                throw new SQLException(
                    "Not enough stock. Available: " + availableStock
                );
            }

            java.math.BigDecimal total =
                price.multiply(java.math.BigDecimal.valueOf(quantity));

            int saleId;

            try (PreparedStatement stmt = conn.prepareStatement(
                    saleSql, Statement.RETURN_GENERATED_KEYS)) {

                if (DataStore.currentUserId > 0) {
                    stmt.setInt(1, DataStore.currentUserId);
                } else {
                    stmt.setNull(1, java.sql.Types.INTEGER);
                }

                stmt.setString(2, customer);
                stmt.setBigDecimal(3, total);
                stmt.executeUpdate();

                try (ResultSet keys = stmt.getGeneratedKeys()) {
                    if (!keys.next()) {
                        throw new SQLException("Could not obtain sale ID.");
                    }
                    saleId = keys.getInt(1);
                }
            }

            try (PreparedStatement stmt = conn.prepareStatement(detailSql)) {
                stmt.setInt(1, saleId);
                stmt.setInt(2, bouquetId);
                stmt.setInt(3, quantity);
                stmt.setBigDecimal(4, price);
                stmt.executeUpdate();
            }

            try (PreparedStatement stmt = conn.prepareStatement(updateStockSql)) {
                stmt.setInt(1, quantity);
                stmt.setInt(2, bouquetId);
                stmt.setInt(3, quantity);

                if (stmt.executeUpdate() != 1) {
                    throw new SQLException("Stock update failed.");
                }
            }

            conn.commit();

            JOptionPane.showMessageDialog(
                this,
                "Sale recorded successfully!\n\n"
                + "Bouquet: " + bouquetName
                + "\nQuantity: " + quantity
                + "\nTotal: ₱" + total.toPlainString()
            );

            txtCustomer.setText("");
            txtQuantity.setText("");

            loadBouquets();
            loadSales();

        } catch (Exception e) {
            conn.rollback();

            JOptionPane.showMessageDialog(
                this,
                "Sale was not recorded:\n" + e.getMessage(),
                "Sale Error",
                JOptionPane.ERROR_MESSAGE
            );
        } finally {
            conn.setAutoCommit(true);
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(
            this,
            "Database error: " + e.getMessage(),
            "Database Error",
            JOptionPane.ERROR_MESSAGE
        );
    }
    }//GEN-LAST:event_btnRecordActionPerformed

    private void txtQuantityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtQuantityActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtQuantityActionPerformed

    private void txtCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCustomerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCustomerActionPerformed

    private void cmbBouquetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbBouquetActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbBouquetActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new SoldBouquetsFrame().setVisible(true));
      
        
        
    }
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRecord;
    private javax.swing.JComboBox<String> cmbBouquet;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblBouquet;
    private javax.swing.JLabel lblCustomer;
    private javax.swing.JLabel lblQuantity;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JTable tblSales;
    private javax.swing.JTextField txtCustomer;
    private javax.swing.JTextField txtQuantity;
    // End of variables declaration//GEN-END:variables
}
