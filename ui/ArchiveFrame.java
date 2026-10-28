/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.flowerbouquet.ui;

import com.mycompany.flowerbouquet.data.DataStore;
import com.mycompany.flowerbouquet.model.Bouquet;
import com.mycompany.flowerbouquet.data.BouquetDAO;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Paul Adanza
 */
public class ArchiveFrame extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ArchiveFrame.class.getName());

    /**
     * Creates new form ArchiveFrame
     */
    public ArchiveFrame() {
    initComponents();
    setLocationRelativeTo(null);
    loadArchivedBouquets();
    setupTableStyle();
    }
    
   private void loadArchivedBouquets() {
    DefaultTableModel model =
            (DefaultTableModel) tblArchive.getModel();

    model.setRowCount(0);

    model.setColumnIdentifiers(new String[]{
        "Bouquet ID", "Name", "Description",
        "Price", "Category", "Quantity"
    });

    try {
        BouquetDAO dao = new BouquetDAO();
        List<Bouquet> bouquets = dao.getArchivedBouquets();

        for (Bouquet b : bouquets) {
            model.addRow(new Object[]{
                b.getId(),
                b.getName(),
                b.getDescription(),
                b.getPrice(),
                b.getCategoryName(),
                b.getQuantity()
            });
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(
                this,
                "Failed to load archived bouquets:\n" + e.getMessage(),
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
    tblArchive.setBackground(lightPink);
    tblArchive.setForeground(textBlue);
    tblArchive.setFont(
            new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14)
    );
    tblArchive.setRowHeight(48);
    tblArchive.setShowGrid(false);
    tblArchive.setIntercellSpacing(new java.awt.Dimension(0, 0));
    tblArchive.setSelectionBackground(selectedPurple);
    tblArchive.setSelectionForeground(textBlue);
    tblArchive.setFillsViewportHeight(true);

    // Background behind empty rows
    jScrollPane2.setBackground(lightPink);
    jScrollPane2.getViewport().setBackground(lightPink);
    jScrollPane2.setOpaque(true);
    jScrollPane2.getViewport().setOpaque(true);

    // Header renderer: explicitly paint purple with white text
    javax.swing.table.JTableHeader header = tblArchive.getTableHeader();

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

    for (int i = 0; i < tblArchive.getColumnCount(); i++) {
        tblArchive.getColumnModel()
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

        jPanel1 = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        btnRestore = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        btnDeletePermanent = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblArchive = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(246, 234, 249));

        lblTitle.setBackground(new java.awt.Color(91, 58, 115));
        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(91, 58, 115));
        lblTitle.setText("Archived Bouquets");

        jPanel5.setBackground(new java.awt.Color(252, 245, 253));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 232, 247), 4));
        jPanel5.setToolTipText("");
        jPanel5.setMaximumSize(new java.awt.Dimension(943, 546));
        jPanel5.setMinimumSize(new java.awt.Dimension(943, 546));

        btnRestore.setBackground(new java.awt.Color(116, 105, 171));
        btnRestore.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnRestore.setForeground(new java.awt.Color(255, 255, 255));
        btnRestore.setText("Restore");
        btnRestore.addActionListener(this::btnRestoreActionPerformed);

        btnRefresh.setBackground(new java.awt.Color(116, 105, 171));
        btnRefresh.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnRefresh.setForeground(new java.awt.Color(255, 255, 255));
        btnRefresh.setText("Refresh");
        btnRefresh.addActionListener(this::btnRefreshActionPerformed);

        btnDeletePermanent.setBackground(new java.awt.Color(221, 71, 104));
        btnDeletePermanent.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnDeletePermanent.setForeground(new java.awt.Color(255, 255, 255));
        btnDeletePermanent.setText("Delete");
        btnDeletePermanent.addActionListener(this::btnDeletePermanentActionPerformed);

        tblArchive.setBackground(new java.awt.Color(252, 245, 253));
        tblArchive.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        tblArchive.setForeground(new java.awt.Color(46, 69, 121));
        tblArchive.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Bouquet Name", "Description", "Price", "Category", "Quantity"
            }
        ));
        jScrollPane2.setViewportView(tblArchive);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1071, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 77, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(95, 95, 95))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(btnRestore, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnDeletePermanent, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(98, 98, 98))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap(58, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRestore, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDeletePermanent, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addComponent(lblTitle))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(104, 104, 104)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(120, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(lblTitle)
                .addGap(56, 56, 56)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(67, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRestoreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRestoreActionPerformed
            int row = tblArchive.getSelectedRow();

    if (row == -1) {
        JOptionPane.showMessageDialog(
                this, "Please select a bouquet to restore.");
        return;
    }

    int modelRow = tblArchive.convertRowIndexToModel(row);
    int id = Integer.parseInt(
            tblArchive.getModel().getValueAt(modelRow, 0).toString());

    int confirm = JOptionPane.showConfirmDialog(
            this,
            "Restore this bouquet to the active catalog?",
            "Confirm Restore",
            JOptionPane.YES_NO_OPTION
    );

    if (confirm != JOptionPane.YES_OPTION) {
        return;
    }

    try {
        BouquetDAO dao = new BouquetDAO();
        dao.restoreBouquet(id);

        loadArchivedBouquets();

        JOptionPane.showMessageDialog(
                this, "Bouquet restored successfully!");

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(
                this,
                "Failed to restore bouquet:\n" + e.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
    }//GEN-LAST:event_btnRestoreActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        loadArchivedBouquets();
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void btnDeletePermanentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeletePermanentActionPerformed
        int row = tblArchive.getSelectedRow();

    if (row == -1) {
        JOptionPane.showMessageDialog(
                this, "Please select a bouquet to delete.");
        return;
    }

    int modelRow = tblArchive.convertRowIndexToModel(row);

    int id = Integer.parseInt(
            tblArchive.getModel().getValueAt(modelRow, 0).toString());

    String name = tblArchive.getModel()
            .getValueAt(modelRow, 1).toString();

    int confirm = JOptionPane.showConfirmDialog(
            this,
            "Permanently delete \"" + name
                    + "\"?\nThis cannot be undone.",
            "Confirm Permanent Delete",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
    );

    if (confirm != JOptionPane.YES_OPTION) {
        return;
    }

    try {
        BouquetDAO dao = new BouquetDAO();
        dao.deleteBouquet(id);

        loadArchivedBouquets();

        JOptionPane.showMessageDialog(
                this, "Bouquet permanently deleted.");

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(
                this,
                "Failed to delete bouquet:\n" + e.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
    }//GEN-LAST:event_btnDeletePermanentActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new ArchiveFrame().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDeletePermanent;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnRestore;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JTable tblArchive;
    // End of variables declaration//GEN-END:variables
}
