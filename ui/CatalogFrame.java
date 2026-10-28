/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.flowerbouquet.ui;

import com.mycompany.flowerbouquet.data.DataStore;
import com.mycompany.flowerbouquet.model.Bouquet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.Component;
import javax.swing.ListSelectionModel;
import com.mycompany.flowerbouquet.data.BouquetDAO;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Paul Adanza
 */
public class CatalogFrame extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(CatalogFrame.class.getName());

    public CatalogFrame() {
        initComponents();
        
        lblBouquetImage.setText("");
        lblBouquetImage.setHorizontalAlignment(
        javax.swing.SwingConstants.CENTER
        );
        lblBouquetImage.setVerticalAlignment(
        javax.swing.SwingConstants.CENTER
        );

        lblBouquetDetails.setHorizontalAlignment(
        javax.swing.SwingConstants.LEFT
        );
        lblBouquetDetails.setVerticalAlignment(
        javax.swing.SwingConstants.TOP
        );
        lblBouquetDetails.setVisible(false);
    
        setSize(1475, 750);
        
        setupTableStyle();
        loadBouquets();
        
        jScrollPane2.setBorder(null);
        jScrollPane2.setOpaque(true);
        jScrollPane2.setFocusable(false);
        
        jPanel2.setMinimumSize(new java.awt.Dimension(961, 547));
        jPanel2.setPreferredSize(new java.awt.Dimension(961, 547));
        jPanel2.setMaximumSize(new java.awt.Dimension(961, 547));
        
        pnlImage.setMinimumSize(new java.awt.Dimension(458, 547));
        pnlImage.setPreferredSize(new java.awt.Dimension(458, 547));
        pnlImage.setMaximumSize(new java.awt.Dimension(458, 547));
        
        pnlImage1.setMinimumSize(new java.awt.Dimension(418, 340));
        pnlImage1.setPreferredSize(new java.awt.Dimension(418, 340));
        pnlImage1.setMaximumSize(new java.awt.Dimension(418, 340));
        
        tblBouquets.setMinimumSize(new java.awt.Dimension(941, 527));
        tblBouquets.setPreferredSize(new java.awt.Dimension(941, 527));
        tblBouquets.setMaximumSize(new java.awt.Dimension(941, 527));
        
        if (tblBouquets.getRowCount() > 0) {
    tblBouquets.setRowSelectionInterval(0, 0);
} else {
    showSelectedBouquetImage();
}
        
        setLocationRelativeTo(null);
        
        tblBouquets.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tblBouquets.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                showSelectedBouquetImage();
            }
        });
    }
    
    private void displayBouquetImage(String imagePath) {
    if (imagePath == null || imagePath.trim().isEmpty()) {
        lblBouquetImage.setIcon(null);
        lblBouquetImage.setText("No image available");
        return;
    }

    java.net.URL imageURL = getClass().getResource(imagePath);

    if (imageURL != null) {
        javax.swing.ImageIcon originalIcon =
                new javax.swing.ImageIcon(imageURL);

        java.awt.Image scaledImage = originalIcon.getImage()
                .getScaledInstance(
                        lblBouquetImage.getWidth(),
                        lblBouquetImage.getHeight(),
                        java.awt.Image.SCALE_SMOOTH
                );

        lblBouquetImage.setIcon(
                new javax.swing.ImageIcon(scaledImage)
        );
        lblBouquetImage.setText("");
    } else {
        lblBouquetImage.setIcon(null);
        lblBouquetImage.setText("Image not found");
        System.out.println("Image not found: " + imagePath);
    }
}
    
    private void showSelectedBouquetImage() {
    int selectedRow = tblBouquets.getSelectedRow();

    if (selectedRow < 0) {
        lblBouquetImage.setIcon(null);
        lblBouquetImage.setText("Select a bouquet");
        lblBouquetDetails.setText("");
        lblBouquetDetails.setVisible(false);
        return;
    }

    int modelRow = tblBouquets.convertRowIndexToModel(selectedRow);
    int bouquetId = Integer.parseInt(
            tblBouquets.getModel().getValueAt(modelRow, 0).toString()
    );

    String name = String.valueOf(
            tblBouquets.getModel().getValueAt(modelRow, 1)
    );
    String category = String.valueOf(
            tblBouquets.getModel().getValueAt(modelRow, 4)
    );
    double price = Double.parseDouble(
            tblBouquets.getModel().getValueAt(modelRow, 3).toString()
    );
    int quantity = Integer.parseInt(
            tblBouquets.getModel().getValueAt(modelRow, 5).toString()
    );

    lblBouquetImage.setIcon(null);
    lblBouquetImage.setText("Loading image...");

    try {
        Bouquet selectedBouquet = null;

        for (Bouquet b : new BouquetDAO().getAllBouquets()) {
            if (b.getId() == bouquetId) {
                selectedBouquet = b;
                break;
            }
        }

        if (selectedBouquet == null) {
            lblBouquetImage.setText("Bouquet not found");
        } else {
            String path = selectedBouquet.getImagePath();

            if (path == null || path.trim().isEmpty()) {
                lblBouquetImage.setText("No image path in database");
            } else {
                java.net.URL imageURL = getClass().getResource(path);

                if (imageURL == null) {
                    lblBouquetImage.setText("Image not found in resources");
                    System.out.println("Resource not found: " + path);
                } else {
                    ImageIcon original = new ImageIcon(imageURL);

                    Image scaled = original.getImage().getScaledInstance(
                            410, 332, Image.SCALE_SMOOTH
                    );

                    lblBouquetImage.setIcon(new ImageIcon(scaled));
                    lblBouquetImage.setText("");
                }
            }
        }

    } catch (SQLException e) {
        lblBouquetImage.setIcon(null);
        lblBouquetImage.setText("Database error");
        JOptionPane.showMessageDialog(
                this,
                "Could not load bouquet image:\n" + e.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE
        );
    }

    lblBouquetDetails.setText(
            "<html><div style='text-align:left;'>"
            + "<b>" + name + "</b><br>"
            + category + "<br>"
            + String.format(java.util.Locale.US, "₱%.2f", price)
            + "<br>Stock: " + quantity
            + "</div></html>"
    );

    lblBouquetDetails.setVisible(true);
    pnlImage1.revalidate();
    pnlImage1.repaint();
    pnlImage.repaint();
}

    
private void setupTableStyle() { 

    java.awt.Color lightPink = new java.awt.Color(252, 245, 253);
    java.awt.Color alternatePink = new java.awt.Color(246, 234, 249);
    java.awt.Color purple = new java.awt.Color(116, 105, 171);
    java.awt.Color textBlue = new java.awt.Color(46, 69, 121);
    java.awt.Color selectedPurple = new java.awt.Color(226, 213, 248);

    // Table background and selection
    tblBouquets.setBackground(lightPink);
    tblBouquets.setForeground(textBlue);
    tblBouquets.setFont(
            new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14)
    );
    tblBouquets.setRowHeight(48);
    tblBouquets.setShowGrid(false);
    tblBouquets.setIntercellSpacing(new java.awt.Dimension(0, 0));
    tblBouquets.setSelectionBackground(selectedPurple);
    tblBouquets.setSelectionForeground(textBlue);
    tblBouquets.setFillsViewportHeight(true);

    // Background behind empty rows
    jScrollPane2.setBackground(lightPink);
    jScrollPane2.getViewport().setBackground(lightPink);
    jScrollPane2.setOpaque(true);
    jScrollPane2.getViewport().setOpaque(true);

    // Header renderer: explicitly paint purple with white text
    javax.swing.table.JTableHeader header = tblBouquets.getTableHeader();

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

    for (int i = 0; i < tblBouquets.getColumnCount(); i++) {
        tblBouquets.getColumnModel()
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

        pnlHeader = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();
        btnRefresh = new javax.swing.JButton();
        btnSearch = new javax.swing.JButton();
        txtSearch = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblBouquets = new javax.swing.JTable();
        pnlImage = new javax.swing.JPanel();
        pnlImage1 = new javax.swing.JPanel();
        lblBouquetImage = new javax.swing.JLabel();
        lblBouquetDetails = new javax.swing.JLabel();
        lblSearch1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(935, 660));

        pnlHeader.setBackground(new java.awt.Color(246, 234, 249));
        pnlHeader.setMaximumSize(new java.awt.Dimension(1920, 1920));
        pnlHeader.setMinimumSize(new java.awt.Dimension(935, 660));
        pnlHeader.setPreferredSize(new java.awt.Dimension(1920, 1920));

        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(91, 58, 115));
        lblTitle.setText("Bouquet Catalog");

        btnRefresh.setBackground(new java.awt.Color(116, 105, 171));
        btnRefresh.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnRefresh.setForeground(new java.awt.Color(255, 255, 255));
        btnRefresh.setText("Refresh");
        btnRefresh.addActionListener(this::btnRefreshActionPerformed);

        btnSearch.setBackground(new java.awt.Color(116, 105, 171));
        btnSearch.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnSearch.setForeground(new java.awt.Color(255, 255, 255));
        btnSearch.setText("Search");
        btnSearch.addActionListener(this::btnSearchActionPerformed);

        txtSearch.setBackground(new java.awt.Color(255, 255, 255));
        txtSearch.addActionListener(this::txtSearchActionPerformed);

        jPanel2.setBackground(new java.awt.Color(252, 245, 253));
        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(239, 232, 247), 4, true));
        jPanel2.setMaximumSize(new java.awt.Dimension(8, 8));
        jPanel2.setMinimumSize(new java.awt.Dimension(8, 8));

        tblBouquets.setBackground(new java.awt.Color(255, 255, 255));
        tblBouquets.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        tblBouquets.setForeground(new java.awt.Color(46, 69, 121));
        tblBouquets.setModel(new javax.swing.table.DefaultTableModel(
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
        tblBouquets.setMaximumSize(new java.awt.Dimension(375, 80));
        tblBouquets.setMinimumSize(new java.awt.Dimension(375, 80));
        jScrollPane2.setViewportView(tblBouquets);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 941, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 527, Short.MAX_VALUE)
                .addContainerGap())
        );

        pnlImage.setBackground(new java.awt.Color(252, 245, 253));
        pnlImage.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(239, 232, 247), 4, true));
        pnlImage.setMaximumSize(new java.awt.Dimension(294, 8));
        pnlImage.setMinimumSize(new java.awt.Dimension(294, 8));

        pnlImage1.setBackground(new java.awt.Color(252, 245, 253));
        pnlImage1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(239, 232, 247), 4, true));
        pnlImage1.setMaximumSize(new java.awt.Dimension(294, 8));
        pnlImage1.setMinimumSize(new java.awt.Dimension(294, 8));

        javax.swing.GroupLayout pnlImage1Layout = new javax.swing.GroupLayout(pnlImage1);
        pnlImage1.setLayout(pnlImage1Layout);
        pnlImage1Layout.setHorizontalGroup(
            pnlImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblBouquetImage, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 410, Short.MAX_VALUE)
        );
        pnlImage1Layout.setVerticalGroup(
            pnlImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblBouquetImage, javax.swing.GroupLayout.DEFAULT_SIZE, 332, Short.MAX_VALUE)
        );

        lblBouquetDetails.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        lblBouquetDetails.setForeground(new java.awt.Color(46, 69, 121));
        lblBouquetDetails.setText("jLabel1");

        javax.swing.GroupLayout pnlImageLayout = new javax.swing.GroupLayout(pnlImage);
        pnlImage.setLayout(pnlImageLayout);
        pnlImageLayout.setHorizontalGroup(
            pnlImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlImageLayout.createSequentialGroup()
                .addGroup(pnlImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlImageLayout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(pnlImage1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlImageLayout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(lblBouquetDetails)))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        pnlImageLayout.setVerticalGroup(
            pnlImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlImageLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(pnlImage1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblBouquetDetails)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lblSearch1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        lblSearch1.setForeground(new java.awt.Color(46, 69, 121));
        lblSearch1.setText("Search Keyword:");

        javax.swing.GroupLayout pnlHeaderLayout = new javax.swing.GroupLayout(pnlHeader);
        pnlHeader.setLayout(pnlHeaderLayout);
        pnlHeaderLayout.setHorizontalGroup(
            pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHeaderLayout.createSequentialGroup()
                .addGroup(pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlHeaderLayout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(lblTitle))
                    .addGroup(pnlHeaderLayout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addComponent(lblSearch1)
                        .addGap(18, 18, 18)
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlHeaderLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(pnlImage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        pnlHeaderLayout.setVerticalGroup(
            pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHeaderLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(lblTitle)
                .addGap(49, 49, 49)
                .addGroup(pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSearch1))
                .addGap(18, 18, 18)
                .addGroup(pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlImage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlHeader, javax.swing.GroupLayout.DEFAULT_SIZE, 1450, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlHeader, javax.swing.GroupLayout.DEFAULT_SIZE, 750, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        txtSearch.setText("");
    loadBouquets();

    if (tblBouquets.getRowCount() > 0) {
        tblBouquets.setRowSelectionInterval(0, 0);
    } else {
        showSelectedBouquetImage();
    }
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        
    String search = txtSearch.getText().trim().toLowerCase();

    DefaultTableModel model =
            (DefaultTableModel) tblBouquets.getModel();

    model.setRowCount(0);

    try {
        List<Bouquet> bouquets = new BouquetDAO().getAllBouquets();

        bouquets.sort(
                java.util.Comparator.comparingInt(Bouquet::getId)
        );

        for (Bouquet b : bouquets) {
            String name = b.getName() == null
                    ? "" : b.getName().toLowerCase();

            String description = b.getDescription() == null
                    ? "" : b.getDescription().toLowerCase();

            String category = b.getCategoryName() == null
                    ? "" : b.getCategoryName().toLowerCase();

            if (name.contains(search)
                    || description.contains(search)
                    || category.contains(search)) {

                model.addRow(new Object[]{
                    b.getId(),
                    b.getName(),
                    b.getDescription(),
                    b.getPrice(),
                    b.getCategoryName(),
                    b.getQuantity()
                });
            }
        }

        if (tblBouquets.getRowCount() > 0) {
            tblBouquets.setRowSelectionInterval(0, 0);
        } else {
            showSelectedBouquetImage();

            if (!search.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this, "No bouquet found."
                );
            }
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(
                this,
                "Failed to search bouquets:\n" + e.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
    }//GEN-LAST:event_btnSearchActionPerformed

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchActionPerformed

    /**
     * @param args the command line arguments
     */
    private void loadBouquets() {
    DefaultTableModel model =
            (DefaultTableModel) tblBouquets.getModel();

    model.setRowCount(0);

    BouquetDAO dao = new BouquetDAO();

    try {
        List<Bouquet> bouquets = dao.getAllBouquets();

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
                "Failed to load bouquets from database:\n"
                + e.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
}
    
    private void setupQuantityColor() {

    DefaultTableCellRenderer renderer =
            new DefaultTableCellRenderer() {

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
                            table,
                            value,
                            isSelected,
                            hasFocus,
                            row,
                            column
                    );

            if (value != null) {

                int quantity =
                        Integer.parseInt(value.toString());

                if (quantity <= 5) {
                    component.setForeground(Color.RED);
                } else {
                    component.setForeground(Color.BLACK);
                }
            }

            return component;
        }
    };

    tblBouquets
            .getColumnModel()
            .getColumn(5)
            .setCellRenderer(renderer);
}
    
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
        java.awt.EventQueue.invokeLater(() -> new CatalogFrame().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnSearch;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblBouquetDetails;
    private javax.swing.JLabel lblBouquetImage;
    private javax.swing.JLabel lblSearch1;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JPanel pnlImage;
    private javax.swing.JPanel pnlImage1;
    private javax.swing.JTable tblBouquets;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
