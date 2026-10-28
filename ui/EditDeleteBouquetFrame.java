/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.flowerbouquet.ui;

import com.mycompany.flowerbouquet.data.DataStore;
import com.mycompany.flowerbouquet.model.Category;
import com.mycompany.flowerbouquet.model.Bouquet;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.Image;
import java.awt.BorderLayout;
import java.io.File;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import com.mycompany.flowerbouquet.data.BouquetDAO;
import java.sql.SQLException;
import java.util.List;
import com.mycompany.flowerbouquet.data.CategoryDAO;

/**
 *
 * @author Paul Adanza
 */
public class EditDeleteBouquetFrame extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(EditDeleteBouquetFrame.class.getName());

    public EditDeleteBouquetFrame() {
    initComponents();

    loadCategoryComboBox();
    loadBouquetComboBox();
    
    
    pnlImage.setMinimumSize(new java.awt.Dimension(480, 549));
    pnlImage.setPreferredSize(new java.awt.Dimension(480, 549));
    pnlImage.setMaximumSize(new java.awt.Dimension(480, 549));
    
    pnlImage1.setMinimumSize(new java.awt.Dimension(418, 340));
    pnlImage1.setPreferredSize(new java.awt.Dimension(418, 340));
    pnlImage1.setMaximumSize(new java.awt.Dimension(418, 340));
    
    jPanel1.setMinimumSize(new java.awt.Dimension(589, 549));
    jPanel1.setPreferredSize(new java.awt.Dimension(589, 549));
    jPanel1.setMaximumSize(new java.awt.Dimension(589, 549));

    setSize(1475, 750);
    setLocationRelativeTo(null);
    showBouquetImage("");

    // Configure the image preview area
    pnlImage1.setLayout(new BorderLayout());
    imageDisplayLabel.setHorizontalAlignment(SwingConstants.CENTER);
    imageDisplayLabel.setVerticalAlignment(SwingConstants.CENTER);
    pnlImage1.add(imageDisplayLabel, BorderLayout.CENTER);

    pnlImage1.revalidate();
    pnlImage1.repaint();
    
}
    
    
    private int getSelectedBouquetId() {
    Object selected = cmbBouquet.getSelectedItem();

    if (selected == null) {
        return -1;
    }

    String item = selected.toString();
    int separator = item.indexOf(" - ");

    if (separator == -1) {
        return -1;
    }

    return Integer.parseInt(item.substring(0, separator));
}
    
    private void loadBouquetComboBox() {
    cmbBouquet.removeAllItems();

    try {
        BouquetDAO dao = new BouquetDAO();
        List<Bouquet> bouquets = dao.getAllBouquets();

        for (Bouquet b : bouquets) {
            cmbBouquet.addItem(
                    b.getId() + " - " + b.getName()
            );
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(
                this,
                "Failed to load bouquets:\n" + e.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
}
    
    private void loadCategoryComboBox() {
    cmbCategory.removeAllItems();

    try {
        CategoryDAO dao = new CategoryDAO();
        List<Category> categories = dao.getAllCategories();

        for (Category category : categories) {
            cmbCategory.addItem(category.getCategoryName());
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
    
    private String selectedImagePath = "";
    private int previewWidth = 410;
    private int previewHeight = 332;
    
    private void showBouquetImage(String imagePath) {
    pnlImage1.removeAll();
    pnlImage1.setLayout(new BorderLayout());

    JLabel previewLabel = new JLabel(
            "No image selected",
            SwingConstants.CENTER
    );

    previewLabel.setForeground(new java.awt.Color(46, 69, 121));

    if (imagePath != null && !imagePath.trim().isEmpty()) {
        ImageIcon originalIcon = null;

        // First, try loading it as a classpath resource.
        java.net.URL imageURL = getClass().getResource(imagePath);

        if (imageURL != null) {
            originalIcon = new ImageIcon(imageURL);
        } else {
            // Otherwise, try loading it as a normal file path.
            File imageFile = new File(imagePath);

            if (imageFile.exists()) {
                originalIcon = new ImageIcon(imagePath);
            }
        }

        if (originalIcon != null
                && originalIcon.getIconWidth() > 0) {

            int panelWidth = pnlImage1.getWidth();
            int panelHeight = pnlImage1.getHeight();

            if (panelWidth <= 0) {
                panelWidth = 418;
            }
            if (panelHeight <= 0) {
                panelHeight = 340;
            }

            int width = Math.max(1, panelWidth - 12);
            int height = Math.max(1, panelHeight - 12);

            Image scaledImage = originalIcon.getImage()
                    .getScaledInstance(
                            width,
                            height,
                            Image.SCALE_SMOOTH
                    );

            previewLabel.setText("");
            previewLabel.setIcon(new ImageIcon(scaledImage));
        } else {
            previewLabel.setText("Image file not found");
        }
    }

    pnlImage1.add(previewLabel, BorderLayout.CENTER);
    pnlImage1.revalidate();
    pnlImage1.repaint();
}

private void clearBouquetFields() {
    cmbBouquet.setSelectedIndex(-1);

    txtName.setText("");
    txtDescription.setText("");
    txtPrice.setText("");
    txtQuantity.setText("");

    cmbCategory.setSelectedIndex(-1);

    selectedImagePath = "";
    showBouquetImage("");
}
    
    private void showImagePreview(String path) {
    imageDisplayLabel.setIcon(null);

    if (path == null || path.trim().isEmpty()) {
        imageDisplayLabel.setText("No image selected");
        imageDisplayLabel.revalidate();
        imageDisplayLabel.repaint();
        return;
    }

    File imageFile = new File(path);

    if (!imageFile.exists()) {
        imageDisplayLabel.setText("Image file not found");
        return;
    }

    ImageIcon originalIcon = new ImageIcon(path);

    int panelWidth = pnlImage1.getWidth();
    int panelHeight = pnlImage1.getHeight();

    if (panelWidth <= 0) {
        panelWidth = 410;
    }

    if (panelHeight <= 0) {
        panelHeight = 332;
    }

    Image originalImage = originalIcon.getImage();

    Image scaledImage = originalImage.getScaledInstance(
            panelWidth - 20,
            panelHeight - 20,
            Image.SCALE_SMOOTH
    );

    imageDisplayLabel.setText("");
    imageDisplayLabel.setIcon(new ImageIcon(scaledImage));

    imageDisplayLabel.revalidate();
    imageDisplayLabel.repaint();
}
    

    private final JLabel imageDisplayLabel = new JLabel(
        "No image selected",
        SwingConstants.CENTER
    );
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlMain = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        lblID = new javax.swing.JLabel();
        lblName = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        lblDescription = new javax.swing.JLabel();
        txtDescription = new javax.swing.JTextField();
        lblPrice = new javax.swing.JLabel();
        txtPrice = new javax.swing.JTextField();
        lblCategory = new javax.swing.JLabel();
        txtQuantity = new javax.swing.JTextField();
        Category = new javax.swing.JLabel();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        cmbBouquet = new javax.swing.JComboBox<>();
        cmbCategory = new javax.swing.JComboBox<>();
        pnlImage = new javax.swing.JPanel();
        pnlImage1 = new javax.swing.JPanel();
        btnUploadImage = new javax.swing.JButton();
        lblImagePreview = new javax.swing.JLabel();
        btnRemoveImage = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(860, 679));

        pnlMain.setBackground(new java.awt.Color(246, 234, 249));
        pnlMain.setMaximumSize(new java.awt.Dimension(860, 679));
        pnlMain.setMinimumSize(new java.awt.Dimension(860, 679));
        pnlMain.setPreferredSize(new java.awt.Dimension(860, 679));

        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(91, 58, 115));
        lblTitle.setText("Edit/Delete Bouquet");

        jPanel1.setBackground(new java.awt.Color(252, 245, 253));
        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(239, 232, 247), 4, true));
        jPanel1.setMaximumSize(new java.awt.Dimension(493, 422));
        jPanel1.setMinimumSize(new java.awt.Dimension(493, 422));

        lblID.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        lblID.setForeground(new java.awt.Color(46, 69, 121));
        lblID.setText("Bouquet:");

        lblName.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        lblName.setForeground(new java.awt.Color(46, 69, 121));
        lblName.setText("Bouquet Name:");

        txtName.setBackground(new java.awt.Color(255, 255, 255));
        txtName.addActionListener(this::txtNameActionPerformed);

        lblDescription.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        lblDescription.setForeground(new java.awt.Color(46, 69, 121));
        lblDescription.setText("Description:");

        txtDescription.setBackground(new java.awt.Color(255, 255, 255));
        txtDescription.addActionListener(this::txtDescriptionActionPerformed);

        lblPrice.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        lblPrice.setForeground(new java.awt.Color(46, 69, 121));
        lblPrice.setText("Price:");

        txtPrice.setBackground(new java.awt.Color(255, 255, 255));
        txtPrice.addActionListener(this::txtPriceActionPerformed);

        lblCategory.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        lblCategory.setForeground(new java.awt.Color(46, 69, 121));
        lblCategory.setText("Quantity:");

        txtQuantity.setBackground(new java.awt.Color(255, 255, 255));
        txtQuantity.addActionListener(this::txtQuantityActionPerformed);

        Category.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        Category.setForeground(new java.awt.Color(46, 69, 121));
        Category.setText("Category:");

        btnUpdate.setBackground(new java.awt.Color(116, 105, 171));
        btnUpdate.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnUpdate.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdate.setText("Update");
        btnUpdate.addActionListener(this::btnUpdateActionPerformed);

        btnDelete.setBackground(new java.awt.Color(221, 71, 104));
        btnDelete.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnDelete.setForeground(new java.awt.Color(255, 255, 255));
        btnDelete.setText("Archive");
        btnDelete.addActionListener(this::btnDeleteActionPerformed);

        btnClear.setBackground(new java.awt.Color(236, 233, 248));
        btnClear.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnClear.setForeground(new java.awt.Color(46, 69, 121));
        btnClear.setText("Clear");
        btnClear.setBorder(null);
        btnClear.addActionListener(this::btnClearActionPerformed);

        cmbBouquet.setBackground(new java.awt.Color(255, 255, 255));
        cmbBouquet.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbBouquet.addActionListener(this::cmbBouquetActionPerformed);

        cmbCategory.setBackground(new java.awt.Color(255, 255, 255));
        cmbCategory.addActionListener(this::cmbCategoryActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(84, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(71, 71, 71))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lblDescription)
                                .addGap(56, 56, 56)
                                .addComponent(txtDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lblPrice)
                                .addGap(111, 111, 111)
                                .addComponent(txtPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblName)
                                    .addComponent(lblID))
                                .addGap(26, 26, 26)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtName)
                                    .addComponent(cmbBouquet, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(Category)
                                    .addGap(75, 75, 75)
                                    .addComponent(cmbCategory, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(lblCategory)
                                    .addGap(77, 77, 77)
                                    .addComponent(txtQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(79, 79, 79))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbBouquet, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblID))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblName)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblDescription)
                    .addComponent(txtDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblPrice)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(txtPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblCategory)
                    .addComponent(txtQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Category)
                    .addComponent(cmbCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(50, 50, 50))
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
            .addGap(0, 410, Short.MAX_VALUE)
        );
        pnlImage1Layout.setVerticalGroup(
            pnlImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 332, Short.MAX_VALUE)
        );

        btnUploadImage.setBackground(new java.awt.Color(116, 105, 171));
        btnUploadImage.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnUploadImage.setForeground(new java.awt.Color(255, 255, 255));
        btnUploadImage.setText("Upload Image");
        btnUploadImage.addActionListener(this::btnUploadImageActionPerformed);

        lblImagePreview.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        lblImagePreview.setForeground(new java.awt.Color(46, 69, 121));
        lblImagePreview.setText("Image Preview:");

        btnRemoveImage.setBackground(new java.awt.Color(221, 71, 104));
        btnRemoveImage.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnRemoveImage.setForeground(new java.awt.Color(255, 255, 255));
        btnRemoveImage.setText("Remove Image");
        btnRemoveImage.addActionListener(this::btnRemoveImageActionPerformed);

        javax.swing.GroupLayout pnlImageLayout = new javax.swing.GroupLayout(pnlImage);
        pnlImage.setLayout(pnlImageLayout);
        pnlImageLayout.setHorizontalGroup(
            pnlImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlImageLayout.createSequentialGroup()
                .addContainerGap(27, Short.MAX_VALUE)
                .addGroup(pnlImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlImageLayout.createSequentialGroup()
                        .addComponent(btnUploadImage, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnRemoveImage))
                    .addGroup(pnlImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblImagePreview)
                        .addComponent(pnlImage1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(27, 27, 27))
        );
        pnlImageLayout.setVerticalGroup(
            pnlImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlImageLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(lblImagePreview)
                .addGap(18, 18, 18)
                .addComponent(pnlImage1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addGroup(pnlImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnUploadImage, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRemoveImage, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(50, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlMainLayout = new javax.swing.GroupLayout(pnlMain);
        pnlMain.setLayout(pnlMainLayout);
        pnlMainLayout.setHorizontalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlMainLayout.createSequentialGroup()
                        .addGap(183, 183, 183)
                        .addComponent(pnlImage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlMainLayout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(lblTitle)))
                .addGap(225, 225, 225))
        );
        pnlMainLayout.setVerticalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(lblTitle)
                .addGap(72, 72, 72)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlImage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(41, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlMain, javax.swing.GroupLayout.DEFAULT_SIZE, 1475, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlMain, javax.swing.GroupLayout.DEFAULT_SIZE, 750, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtQuantityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtQuantityActionPerformed
        // TODO add your hantxtQuantityhere:
    }//GEN-LAST:event_txtQuantityActionPerformed

    private void txtPriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPriceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPriceActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
    int id = getSelectedBouquetId();

    if (id == -1) {
        JOptionPane.showMessageDialog(
                this, "Please select a bouquet.");
        return;
    }

    String bouquetName = cmbBouquet.getSelectedItem()
            .toString()
            .substring(cmbBouquet.getSelectedItem()
            .toString().indexOf(" - ") + 3);

    int choice = JOptionPane.showConfirmDialog(
            this,
            "Archive \"" + bouquetName
                    + "\"?\nIt will be removed from the active catalog.",
            "Confirm Archive",
            JOptionPane.YES_NO_OPTION
    );

    if (choice != JOptionPane.YES_OPTION) {
        return;
    }

    try {
        BouquetDAO dao = new BouquetDAO();
        dao.archiveBouquet(id);

        JOptionPane.showMessageDialog(
                this, "Bouquet archived successfully!");

        loadBouquetComboBox();
        clearBouquetFields();

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(
                this,
                "Failed to archive bouquet:\n" + e.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
    clearBouquetFields();
    }//GEN-LAST:event_btnClearActionPerformed

    private void txtDescriptionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescriptionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescriptionActionPerformed

    private void txtNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNameActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
    int id = getSelectedBouquetId();

    if (id == -1) {
        JOptionPane.showMessageDialog(
                this, "Please select a bouquet.");
        return;
    }

    String name = txtName.getText().trim();
    String description = txtDescription.getText().trim();
    String priceText = txtPrice.getText().trim();
    String quantityText = txtQuantity.getText().trim();
    String category = (String) cmbCategory.getSelectedItem();

    if (name.isEmpty() || description.isEmpty()
        || priceText.isEmpty() || quantityText.isEmpty()
        || category == null || category.isBlank()) {
        JOptionPane.showMessageDialog(
                this, "Please fill in all fields.");
        return;
    }

    double price;
    int quantity;

    try {
        price = Double.parseDouble(priceText);
        quantity = Integer.parseInt(quantityText);

        if (!Double.isFinite(price) || price <= 0) {
            JOptionPane.showMessageDialog(
                    this, "Price must be greater than 0.");
            return;
        }

        if (quantity < 0) {
            JOptionPane.showMessageDialog(
                    this, "Quantity cannot be negative.");
            return;
        }

    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(
                this, "Enter a valid price and quantity.");
        return;
    }

    Category selectedCategory = null;

try {
    for (Category c : new CategoryDAO().getAllCategories()) {
        if (c.getCategoryName().equalsIgnoreCase(category)) {
            selectedCategory = c;
            break;
        }
    }
} catch (SQLException e) {
    JOptionPane.showMessageDialog(
            this,
            "Failed to load categories:\n" + e.getMessage(),
            "Database Error",
            JOptionPane.ERROR_MESSAGE
    );
    return;
}

if (selectedCategory == null) {
    JOptionPane.showMessageDialog(
            this,
            "Category not found. Please enter an existing category."
    );
    return;
}

    Bouquet updatedBouquet = new Bouquet(
            id,
            name,
            description,
            price,
            selectedCategory.getCategoryId(),
            quantity
    );

    updatedBouquet.setImagePath(selectedImagePath);

    try {
        BouquetDAO dao = new BouquetDAO();
        boolean updated = dao.updateBouquet(updatedBouquet);

if (updated) {
    JOptionPane.showMessageDialog(
            this, "Bouquet updated successfully!");
} else {
    JOptionPane.showMessageDialog(
            this,
            "No bouquet was updated. It may have been archived or removed.",
            "Update Failed",
            JOptionPane.WARNING_MESSAGE
    );
    return;
}

        loadBouquetComboBox();
        clearBouquetFields();

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(
                this,
                "Failed to update bouquet:\n" + e.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnUploadImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUploadImageActionPerformed
    JFileChooser chooser = new JFileChooser();

    chooser.setDialogTitle("Select Bouquet Image");
    chooser.setFileFilter(new FileNameExtensionFilter(
            "Image Files", "jpg", "jpeg", "png", "gif", "webp"));

    int result = chooser.showOpenDialog(this);

    if (result == JFileChooser.APPROVE_OPTION) {
        java.io.File selectedFile = chooser.getSelectedFile();
        selectedImagePath = selectedFile.getAbsolutePath();

        showBouquetImage(selectedImagePath);
    }
    }//GEN-LAST:event_btnUploadImageActionPerformed

    private void cmbBouquetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbBouquetActionPerformed
        int id = getSelectedBouquetId();

    if (id == -1) {
        return;
    }

    try {
        Bouquet selectedBouquet = null;

        for (Bouquet b : new BouquetDAO().getAllBouquets()) {
            if (b.getId() == id) {
                selectedBouquet = b;
                break;
            }
        }

        if (selectedBouquet == null) {
            JOptionPane.showMessageDialog(
                    this, "Bouquet not found in the database.");
            return;
        }

        txtName.setText(selectedBouquet.getName());
        txtDescription.setText(selectedBouquet.getDescription());
        txtPrice.setText(String.valueOf(selectedBouquet.getPrice()));
        txtQuantity.setText(String.valueOf(selectedBouquet.getQuantity()));
        selectedImagePath = selectedBouquet.getImagePath();

        showBouquetImage(selectedImagePath);

        String categoryName = selectedBouquet.getCategoryName();

        if (categoryName != null && !categoryName.equalsIgnoreCase("Unknown")) {
            cmbCategory.setSelectedItem(categoryName);
        } else {
            cmbCategory.setSelectedIndex(-1);
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(
                this,
                "Failed to load bouquet details:\n" + e.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
        
    }//GEN-LAST:event_cmbBouquetActionPerformed

    private void btnRemoveImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveImageActionPerformed
        if (cmbBouquet.getSelectedItem() == null) {
        JOptionPane.showMessageDialog(
                this, "Please select a bouquet first.");
        return;
    }

    selectedImagePath = "";
    showBouquetImage("");

    JOptionPane.showMessageDialog(
            this,
            "Image removed from the preview. Click Update to save the change.");
    }//GEN-LAST:event_btnRemoveImageActionPerformed

    private void cmbCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbCategoryActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbCategoryActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new EditDeleteBouquetFrame().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Category;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnRemoveImage;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton btnUploadImage;
    private javax.swing.JComboBox<String> cmbBouquet;
    private javax.swing.JComboBox<String> cmbCategory;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblCategory;
    private javax.swing.JLabel lblDescription;
    private javax.swing.JLabel lblID;
    private javax.swing.JLabel lblImagePreview;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblPrice;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JPanel pnlImage;
    private javax.swing.JPanel pnlImage1;
    private javax.swing.JPanel pnlMain;
    private javax.swing.JTextField txtDescription;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtPrice;
    private javax.swing.JTextField txtQuantity;
    // End of variables declaration//GEN-END:variables
}
