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
import com.mycompany.flowerbouquet.data.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Paul Adanza
 */
public class GuestFrame extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(GuestFrame.class.getName());

    /**
     * Creates new form GuestFramee
     */
    public GuestFrame() {
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

    setSize(1920, 1080);

    setupTableStyle();
    loadBouquets();

    jScrollPane5.setBorder(null);
    jScrollPane5.setFocusable(false);

    setLocationRelativeTo(null);

    tblBouquets3.setSelectionMode(
        ListSelectionModel.SINGLE_SELECTION
    );

    tblBouquets3.getSelectionModel().addListSelectionListener(e -> {
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
    int selectedRow = tblBouquets3.getSelectedRow();

    if (selectedRow < 0) {
        lblBouquetImage.setIcon(null);
        lblBouquetImage.setText("Select a bouquet");
        lblBouquetDetails.setText("");
        lblBouquetDetails.setVisible(false);
        return;
    }

    int modelRow = tblBouquets3.convertRowIndexToModel(selectedRow);

    int bouquetId = Integer.parseInt(
            tblBouquets3.getModel()
                    .getValueAt(modelRow, 0).toString()
    );

    String sql = """
        SELECT b.bouquet_name,
               b.price,
               b.quantity,
               b.image_path,
               c.category_name
        FROM bouquets b
        LEFT JOIN categories c
            ON b.category_id = c.category_id
        WHERE b.bouquet_id = ?
          AND b.is_archived = 0
        """;

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, bouquetId);

        try (ResultSet rs = stmt.executeQuery()) {
            if (!rs.next()) {
                lblBouquetImage.setIcon(null);
                lblBouquetImage.setText("Bouquet not found");
                lblBouquetDetails.setText("");
                lblBouquetDetails.setVisible(false);
                return;
            }

            String name = rs.getString("bouquet_name");
            String category = rs.getString("category_name");
            double price = rs.getDouble("price");
            int quantity = rs.getInt("quantity");
            String path = rs.getString("image_path");

            lblBouquetImage.setIcon(null);
            lblBouquetImage.setText("");

            if (path != null && !path.trim().isEmpty()) {
                java.net.URL imageURL = getClass().getResource(path);

                if (imageURL != null) {
                    ImageIcon originalIcon = new ImageIcon(imageURL);

                    int width = Math.max(1, pnlImage1.getWidth() - 8);
                    int height = Math.max(1, pnlImage1.getHeight() - 8);

                    Image scaledImage = originalIcon.getImage()
                            .getScaledInstance(
                                    width,
                                    height,
                                    Image.SCALE_SMOOTH
                            );

                    lblBouquetImage.setIcon(new ImageIcon(scaledImage));
                } else {
                    lblBouquetImage.setText("Image not found");
                    System.out.println(
                            "Image resource not found: " + path
                    );
                }
            } else {
                lblBouquetImage.setText("No image available");
            }

            String safeName = name == null ? "" : name;
            String safeCategory = category == null
                    ? "Uncategorized" : category;

            lblBouquetDetails.setText(
                    "<html><div style='text-align:left;'>"
                    + "<b>" + safeName + "</b><br>"
                    + safeCategory + "<br>"
                    + String.format(
                            java.util.Locale.US,
                            "₱%.2f", price
                    )
                    + "<br>Stock: " + quantity
                    + "</div></html>"
            );

            lblBouquetDetails.setVisible(true);

            pnlImage1.revalidate();
            pnlImage1.repaint();
            pnlImage.revalidate();
            pnlImage.repaint();
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(
                this,
                "Unable to load bouquet details:\n" + e.getMessage(),
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
    tblBouquets3.setBackground(lightPink);
    tblBouquets3.setForeground(textBlue);
    tblBouquets3.setFont(
            new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14)
    );
    tblBouquets3.setRowHeight(48);
    tblBouquets3.setShowGrid(false);
    tblBouquets3.setIntercellSpacing(new java.awt.Dimension(0, 0));
    tblBouquets3.setSelectionBackground(selectedPurple);
    tblBouquets3.setSelectionForeground(textBlue);
    tblBouquets3.setFillsViewportHeight(true);

    // Background behind empty rows
    jScrollPane5.setBackground(lightPink);
    jScrollPane5.getViewport().setBackground(lightPink);
    jScrollPane5.setOpaque(true);
    jScrollPane5.getViewport().setOpaque(true);

    // Header renderer: explicitly paint purple with white text
    javax.swing.table.JTableHeader header = tblBouquets3.getTableHeader();

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

    for (int i = 0; i < tblBouquets3.getColumnCount(); i++) {
        tblBouquets3.getColumnModel()
                .getColumn(i)
                .setCellRenderer(cellRenderer);
    }
}

private void loadBouquets() {
    DefaultTableModel model =
            (DefaultTableModel) tblBouquets3.getModel();

    model.setRowCount(0);

    model.setColumnIdentifiers(new String[]{
        "ID", "Bouquet Name", "Description",
        "Price", "Category", "Quantity"
    });

    String sql = """
        SELECT b.bouquet_id,
               b.bouquet_name,
               b.description,
               b.price,
               c.category_name,
               b.quantity
        FROM bouquets b
        LEFT JOIN categories c
            ON b.category_id = c.category_id
        WHERE b.is_archived = 0
        ORDER BY b.bouquet_name
        """;

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getInt("bouquet_id"),
                rs.getString("bouquet_name"),
                rs.getString("description"),
                String.format(
                    java.util.Locale.US,
                    "%.2f",
                    rs.getDouble("price")
                ),
                rs.getString("category_name") == null
                    ? "Uncategorized"
                    : rs.getString("category_name"),
                rs.getInt("quantity")
            });
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(
                this,
                "Unable to load bouquets from the database:\n"
                        + e.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE
        );
        return;
    }

    if (tblBouquets3.getRowCount() > 0) {
        tblBouquets3.setRowSelectionInterval(0, 0);
    } else {
        showSelectedBouquetImage();
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
        jPanel2 = new javax.swing.JPanel();
        lblLogo = new javax.swing.JLabel();
        lblTitle2 = new javax.swing.JLabel();
        btnExit = new javax.swing.JButton();
        lblLogged = new javax.swing.JLabel();
        lblWelcome = new javax.swing.JLabel();
        pnlContent = new javax.swing.JPanel();
        pnlHeader3 = new javax.swing.JPanel();
        btnRefresh3 = new javax.swing.JButton();
        btnSearch3 = new javax.swing.JButton();
        txtSearch3 = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblBouquets3 = new javax.swing.JTable();
        pnlImage = new javax.swing.JPanel();
        pnlImage1 = new javax.swing.JPanel();
        lblBouquetImage = new javax.swing.JLabel();
        lblBouquetDetails = new javax.swing.JLabel();
        lblSearch1 = new javax.swing.JLabel();
        lblTitle3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(252, 245, 253));

        jPanel2.setBackground(new java.awt.Color(246, 234, 249));
        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(239, 232, 247), 4, true));
        jPanel2.setMaximumSize(new java.awt.Dimension(1920, 1920));

        lblLogo.setBackground(new java.awt.Color(91, 58, 115));
        lblLogo.setFont(new java.awt.Font("Serif", 1, 50)); // NOI18N
        lblLogo.setForeground(new java.awt.Color(91, 58, 115));
        lblLogo.setText("PETALCRAFT");

        lblTitle2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        lblTitle2.setForeground(new java.awt.Color(46, 69, 121));
        lblTitle2.setText("A FLOWER BOUQUET CATALOG SYSTEM");

        btnExit.setBackground(new java.awt.Color(116, 105, 171));
        btnExit.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnExit.setForeground(new java.awt.Color(255, 255, 255));
        btnExit.setText("Exit");
        btnExit.addActionListener(this::btnExitActionPerformed);

        lblLogged.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        lblLogged.setForeground(new java.awt.Color(46, 69, 121));
        lblLogged.setText("Logged in as:");

        lblWelcome.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        lblWelcome.setForeground(new java.awt.Color(46, 69, 121));
        lblWelcome.setText("Username");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(130, 130, 130)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblLogo)
                    .addComponent(lblTitle2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblLogged)
                    .addComponent(lblWelcome)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(btnExit, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(187, 187, 187))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(lblLogged)
                .addGap(6, 6, 6)
                .addComponent(lblWelcome)
                .addGap(16, 16, 16)
                .addComponent(btnExit, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(lblLogo)
                .addGap(6, 6, 6)
                .addComponent(lblTitle2)
                .addContainerGap())
        );

        pnlContent.setBackground(new java.awt.Color(246, 234, 249));

        pnlHeader3.setBackground(new java.awt.Color(246, 234, 249));
        pnlHeader3.setMaximumSize(new java.awt.Dimension(1920, 1920));
        pnlHeader3.setMinimumSize(new java.awt.Dimension(935, 660));
        pnlHeader3.setPreferredSize(new java.awt.Dimension(1920, 1920));

        btnRefresh3.setBackground(new java.awt.Color(116, 105, 171));
        btnRefresh3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnRefresh3.setForeground(new java.awt.Color(255, 255, 255));
        btnRefresh3.setText("Refresh");
        btnRefresh3.addActionListener(this::btnRefreshActionPerformed);

        btnSearch3.setBackground(new java.awt.Color(116, 105, 171));
        btnSearch3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnSearch3.setForeground(new java.awt.Color(255, 255, 255));
        btnSearch3.setText("Search");
        btnSearch3.addActionListener(this::btnSearchActionPerformed);

        txtSearch3.setBackground(new java.awt.Color(255, 255, 255));
        txtSearch3.addActionListener(this::txtSearchActionPerformed);

        jPanel6.setBackground(new java.awt.Color(252, 245, 253));
        jPanel6.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(239, 232, 247), 4, true));
        jPanel6.setMaximumSize(new java.awt.Dimension(8, 8));
        jPanel6.setMinimumSize(new java.awt.Dimension(8, 8));

        tblBouquets3.setBackground(new java.awt.Color(255, 255, 255));
        tblBouquets3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        tblBouquets3.setForeground(new java.awt.Color(46, 69, 121));
        tblBouquets3.setModel(new javax.swing.table.DefaultTableModel(
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
        tblBouquets3.setMaximumSize(new java.awt.Dimension(375, 80));
        tblBouquets3.setMinimumSize(new java.awt.Dimension(375, 80));
        jScrollPane5.setViewportView(tblBouquets3);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 1266, Short.MAX_VALUE)
                .addGap(4, 4, 4))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 624, Short.MAX_VALUE)
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

        javax.swing.GroupLayout pnlHeader3Layout = new javax.swing.GroupLayout(pnlHeader3);
        pnlHeader3.setLayout(pnlHeader3Layout);
        pnlHeader3Layout.setHorizontalGroup(
            pnlHeader3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHeader3Layout.createSequentialGroup()
                .addGroup(pnlHeader3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlHeader3Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(pnlImage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlHeader3Layout.createSequentialGroup()
                        .addGap(61, 61, 61)
                        .addComponent(lblSearch1)
                        .addGap(18, 18, 18)
                        .addComponent(txtSearch3, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSearch3, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRefresh3, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        pnlHeader3Layout.setVerticalGroup(
            pnlHeader3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHeader3Layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addGroup(pnlHeader3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSearch3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearch3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRefresh3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSearch1))
                .addGap(18, 18, 18)
                .addGroup(pnlHeader3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlImage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout pnlContentLayout = new javax.swing.GroupLayout(pnlContent);
        pnlContent.setLayout(pnlContentLayout);
        pnlContentLayout.setHorizontalGroup(
            pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlHeader3, javax.swing.GroupLayout.DEFAULT_SIZE, 1769, Short.MAX_VALUE)
        );
        pnlContentLayout.setVerticalGroup(
            pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlContentLayout.createSequentialGroup()
                .addComponent(pnlHeader3, javax.swing.GroupLayout.DEFAULT_SIZE, 756, Short.MAX_VALUE)
                .addContainerGap())
        );

        lblTitle3.setFont(new java.awt.Font("Gabriola", 2, 30)); // NOI18N
        lblTitle3.setForeground(new java.awt.Color(46, 69, 121));
        lblTitle3.setText("Fresh Flowers       Special Moments       Petal Craft ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(74, 74, 74)
                .addComponent(pnlContent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(77, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblTitle3)
                .addGap(720, 720, 720))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pnlContent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblTitle3)
                .addContainerGap(66, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
    int choice = javax.swing.JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to exit Guest Mode?",
            "Exit Guest Mode",
            javax.swing.JOptionPane.YES_NO_OPTION
    );

    if (choice == javax.swing.JOptionPane.YES_OPTION) {
        new LoginFrame().setVisible(true);
        this.dispose();
    }
    }//GEN-LAST:event_btnExitActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        txtSearch3.setText("");

    tblBouquets3.setRowSorter(null);

    loadBouquets();
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        String keyword = txtSearch3.getText().trim().toLowerCase();
        
        DefaultTableModel model =
        (DefaultTableModel) tblBouquets3.getModel();

    javax.swing.table.TableRowSorter<DefaultTableModel> sorter =
        new javax.swing.table.TableRowSorter<>(model);

    tblBouquets3.setRowSorter(sorter);

    if (keyword.isEmpty()) {
        sorter.setRowFilter(null);
    } else {
        sorter.setRowFilter(
            javax.swing.RowFilter.regexFilter(
                "(?i)" + java.util.regex.Pattern.quote(keyword)
            )
        );
    }

    if (tblBouquets3.getRowCount() > 0) {
        tblBouquets3.setRowSelectionInterval(0, 0);
    } else {
        tblBouquets3.clearSelection();
        showSelectedBouquetImage();
    }
    }//GEN-LAST:event_btnSearchActionPerformed

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        btnSearchActionPerformed(evt);
    }//GEN-LAST:event_txtSearchActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new GuestFrame().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnRefresh3;
    private javax.swing.JButton btnSearch3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JLabel lblBouquetDetails;
    private javax.swing.JLabel lblBouquetImage;
    private javax.swing.JLabel lblLogged;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblSearch1;
    private javax.swing.JLabel lblTitle2;
    private javax.swing.JLabel lblTitle3;
    private javax.swing.JLabel lblWelcome;
    private javax.swing.JPanel pnlContent;
    private javax.swing.JPanel pnlHeader3;
    private javax.swing.JPanel pnlImage;
    private javax.swing.JPanel pnlImage1;
    private javax.swing.JTable tblBouquets3;
    private javax.swing.JTextField txtSearch3;
    // End of variables declaration//GEN-END:variables
}