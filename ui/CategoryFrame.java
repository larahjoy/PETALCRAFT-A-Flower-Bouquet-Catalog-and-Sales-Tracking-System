/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.flowerbouquet.ui;
import com.mycompany.flowerbouquet.data.DataStore;
import com.mycompany.flowerbouquet.model.Category;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import com.mycompany.flowerbouquet.data.CategoryDAO;
import java.sql.SQLException;
import java.util.List;
/**
 *
 * @author Paul Adanza
 */
public class CategoryFrame extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(CategoryFrame.class.getName());

    public CategoryFrame() {
        initComponents();
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setSize(1475, 750);
        setupTableStyle();
        setLocationRelativeTo(null);
        loadCategories();
        
        tblCategories.addMouseListener(new java.awt.event.MouseAdapter() {
    @Override
    public void mouseClicked(java.awt.event.MouseEvent evt) {
        int row = tblCategories.getSelectedRow();

        if (row != -1) {
            txtCategoryName.setText(
                    tblCategories.getValueAt(row, 1).toString()
            );

            Object description =
                    tblCategories.getValueAt(row, 2);

            txtDescription.setText(
                    description == null ? "" : description.toString()
            );
        }
    }
});
        
        jScrollPane1.setBorder(null);
        jScrollPane1.setOpaque(true);
        jScrollPane1.setFocusable(false);
    }
    
private void setupTableStyle() {

    java.awt.Color lightPink = new java.awt.Color(252, 245, 253);
    java.awt.Color alternatePink = new java.awt.Color(246, 234, 249);
    java.awt.Color purple = new java.awt.Color(116, 105, 171);
    java.awt.Color textBlue = new java.awt.Color(46, 69, 121);
    java.awt.Color selectedPurple = new java.awt.Color(226, 213, 248);

    // Table background and selection
    tblCategories.setBackground(lightPink);
    tblCategories.setForeground(textBlue);
    tblCategories.setFont(
            new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14)
    );
    tblCategories.setRowHeight(48);
    tblCategories.setShowGrid(false);
    tblCategories.setIntercellSpacing(new java.awt.Dimension(0, 0));
    tblCategories.setSelectionBackground(selectedPurple);
    tblCategories.setSelectionForeground(textBlue);
    tblCategories.setFillsViewportHeight(true);
    
    // Background behind empty rows
    jScrollPane1.setBackground(lightPink);
    jScrollPane1.getViewport().setBackground(lightPink);
    jScrollPane1.setOpaque(true);
    jScrollPane1.getViewport().setOpaque(true);

    // Header renderer: explicitly paint purple with white text
    javax.swing.table.JTableHeader header = tblCategories.getTableHeader();

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

    for (int i = 0; i < tblCategories.getColumnCount(); i++) {
        tblCategories.getColumnModel()
                .getColumn(i)
                .setCellRenderer(cellRenderer);
    }
}


    

    private void loadCategories() {
    DefaultTableModel model =
            (DefaultTableModel) tblCategories.getModel();

    model.setRowCount(0);

    try {
        CategoryDAO dao = new CategoryDAO();
        List<Category> categories = dao.getAllCategories();

        for (Category c : categories) {
            model.addRow(new Object[]{
                c.getCategoryId(),
                c.getCategoryName(),
                c.getDescription()
            });
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(
                this,
                "Failed to load categories:\n" + e.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE
        );
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
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        txtCategoryName = new javax.swing.JTextField();
        lblCategoryName = new javax.swing.JLabel();
        lblDescription = new javax.swing.JLabel();
        txtDescription = new javax.swing.JTextField();
        btnAdd = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        scrollPane = new javax.swing.JScrollPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCategories = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1300, 700));
        setSize(new java.awt.Dimension(1300, 700));

        jPanel1.setBackground(new java.awt.Color(246, 234, 249));
        jPanel1.setMaximumSize(new java.awt.Dimension(1300, 700));
        jPanel1.setMinimumSize(new java.awt.Dimension(1300, 700));
        jPanel1.setPreferredSize(new java.awt.Dimension(1300, 700));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(91, 58, 115));
        jLabel1.setText("Category Management");

        jPanel2.setBackground(new java.awt.Color(252, 245, 253));
        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(239, 232, 247), 4, true));
        jPanel2.setMaximumSize(new java.awt.Dimension(427, 498));
        jPanel2.setMinimumSize(new java.awt.Dimension(427, 498));

        txtCategoryName.setBackground(new java.awt.Color(255, 255, 255));
        txtCategoryName.addActionListener(this::txtCategoryNameActionPerformed);

        lblCategoryName.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        lblCategoryName.setForeground(new java.awt.Color(46, 69, 121));
        lblCategoryName.setText("Category name:");

        lblDescription.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        lblDescription.setForeground(new java.awt.Color(46, 69, 121));
        lblDescription.setText("Description:");

        txtDescription.setBackground(new java.awt.Color(255, 255, 255));
        txtDescription.addActionListener(this::txtDescriptionActionPerformed);

        btnAdd.setBackground(new java.awt.Color(116, 105, 171));
        btnAdd.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnAdd.setForeground(new java.awt.Color(255, 255, 255));
        btnAdd.setText("Add");
        btnAdd.addActionListener(this::btnAddActionPerformed);

        btnUpdate.setBackground(new java.awt.Color(116, 105, 171));
        btnUpdate.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnUpdate.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdate.setText("Update");
        btnUpdate.addActionListener(this::btnUpdateActionPerformed);

        btnDelete.setBackground(new java.awt.Color(221, 71, 104));
        btnDelete.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnDelete.setForeground(new java.awt.Color(255, 255, 255));
        btnDelete.setText("Delete");
        btnDelete.addActionListener(this::btnDeleteActionPerformed);

        btnClear.setBackground(new java.awt.Color(236, 233, 248));
        btnClear.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnClear.setForeground(new java.awt.Color(46, 69, 121));
        btnClear.setText("Clear");
        btnClear.addActionListener(this::btnClearActionPerformed);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(41, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lblCategoryName)
                        .addGap(31, 31, 31)
                        .addComponent(txtCategoryName, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lblDescription)
                        .addGap(61, 61, 61)
                        .addComponent(txtDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(37, 37, 37))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblCategoryName)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(txtCategoryName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(37, 37, 37)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(lblDescription))
                    .addComponent(txtDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(101, 101, 101)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(128, 128, 128))
        );

        jPanel3.setBackground(new java.awt.Color(252, 245, 253));
        jPanel3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(239, 232, 247), 4, true));
        jPanel3.setMaximumSize(new java.awt.Dimension(703, 95));
        jPanel3.setMinimumSize(new java.awt.Dimension(703, 95));

        scrollPane.setBackground(new java.awt.Color(255, 255, 255));

        tblCategories.setBackground(new java.awt.Color(252, 245, 253));
        tblCategories.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        tblCategories.setForeground(new java.awt.Color(46, 69, 121));
        tblCategories.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Category ID", "Category Name", "Description"
            }
        ));
        jScrollPane1.setViewportView(tblCategories);

        scrollPane.setViewportView(jScrollPane1);

        jLabel5.setFont(new java.awt.Font("Segoe UI Semibold", 0, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(46, 69, 121));
        jLabel5.setText("Category List:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addComponent(jLabel5))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(scrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 762, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(scrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(69, 69, 69)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(jLabel1)))
                .addGap(75, 75, 75))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jLabel1)
                .addGap(65, 65, 65)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 62, Short.MAX_VALUE))
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

    private void txtCategoryNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCategoryNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCategoryNameActionPerformed

    private void txtDescriptionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescriptionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescriptionActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
    String name = txtCategoryName.getText().trim();
String description = txtDescription.getText().trim();

if (name.isEmpty()) {
    JOptionPane.showMessageDialog(this,
            "Please enter a category name.");
    return;
}

try {
    CategoryDAO dao = new CategoryDAO();

    // Check for duplicate category names
    List<Category> categories = dao.getAllCategories();

    for (Category c : categories) {
        if (c.getCategoryName().equalsIgnoreCase(name)) {
            JOptionPane.showMessageDialog(this,
                    "This category already exists.");
            return;
        }
    }

    Category category = new Category(0, name, description);
    dao.addCategory(category);

    JOptionPane.showMessageDialog(this,
            "Category added successfully!");

    loadCategories();
    clearFields();

} catch (SQLException e) {
    JOptionPane.showMessageDialog(this,
            "Failed to add category:\n" + e.getMessage(),
            "Database Error",
            JOptionPane.ERROR_MESSAGE);
}
    
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        int row = tblCategories.getSelectedRow();

    if (row == -1) {
        JOptionPane.showMessageDialog(this,
                "Please select a category to update.");
        return;
    }

    int id = Integer.parseInt(
            tblCategories.getValueAt(row, 0).toString());

    String name = txtCategoryName.getText().trim();
    String description = txtDescription.getText().trim();

    if (name.isEmpty()) {
        JOptionPane.showMessageDialog(this,
                "Please enter a category name.");
        return;
    }

    try {
        CategoryDAO dao = new CategoryDAO();
        List<Category> categories = dao.getAllCategories();

        for (Category c : categories) {
            if (c.getCategoryId() != id
                    && c.getCategoryName().equalsIgnoreCase(name)) {
                JOptionPane.showMessageDialog(this,
                        "Another category already uses this name.");
                return;
            }
        }

        Category category = new Category(id, name, description);
        dao.updateCategory(category);

        JOptionPane.showMessageDialog(this,
                "Category updated successfully!");

        loadCategories();
        clearFields();

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this,
                "Failed to update category:\n" + e.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        int row = tblCategories.getSelectedRow();

    if (row == -1) {
        JOptionPane.showMessageDialog(this,
                "Please select a category to delete.");
        return;
    }

    int id = Integer.parseInt(
            tblCategories.getValueAt(row, 0).toString());

    String name = tblCategories.getValueAt(row, 1).toString();

    int choice = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to delete \"" + name + "\"?",
            "Confirm Delete",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
    );

    if (choice != JOptionPane.YES_OPTION) {
        return;
    }

    try {
        CategoryDAO dao = new CategoryDAO();
        dao.deleteCategory(id);

        JOptionPane.showMessageDialog(this,
                "Category deleted successfully!");

        loadCategories();
        clearFields();

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this,
                "Failed to delete category:\n" + e.getMessage()
                + "\n\nThis category may be used by existing bouquets.",
                "Database Error",
                JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed

    txtCategoryName.setText("");
    txtDescription.setText("");

    tblCategories.clearSelection();
    }//GEN-LAST:event_btnClearActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new CategoryFrame().setVisible(true));
    }
    
    private void clearFields() {

    txtCategoryName.setText("");
    txtDescription.setText("");

    tblCategories.clearSelection();
    }
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCategoryName;
    private javax.swing.JLabel lblDescription;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JTable tblCategories;
    private javax.swing.JTextField txtCategoryName;
    private javax.swing.JTextField txtDescription;
    // End of variables declaration//GEN-END:variables
}
