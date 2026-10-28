/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.flowerbouquet.ui;
import com.mycompany.flowerbouquet.data.DataStore;
import com.mycompany.flowerbouquet.model.Sale;
import com.mycompany.flowerbouquet.model.Bouquet;
import com.mycompany.flowerbouquet.model.SaleDetail;
import javax.swing.table.DefaultTableModel;
import com.mycompany.flowerbouquet.data.BouquetDAO;
import java.sql.SQLException;
import com.mycompany.flowerbouquet.data.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


/**
 *
 * @author Paul Adanza
 */
public class DashboardFrame extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(DashboardFrame.class.getName());

    /**
     * Creates new form DashboardFrame
     */
    public DashboardFrame(MainMenuFrame mainMenu, String userRole) {
    initComponents();

    this.mainMenu = mainMenu;
    this.userRole = userRole;

    // Show Registered Users only for Admin
    jPanel6.setVisible("Admin".equalsIgnoreCase(userRole));

    setupTableStyle();
    setSize(1475, 750);

    setDefaultCloseOperation(
            javax.swing.WindowConstants.DISPOSE_ON_CLOSE
    );

    loadDashboardData();
    
        jPanel3.setMinimumSize(new java.awt.Dimension(278, 107));
        jPanel3.setPreferredSize(new java.awt.Dimension(278, 107));
        jPanel3.setMaximumSize(new java.awt.Dimension(278, 107));
        
        
        jPanel4.setMinimumSize(new java.awt.Dimension(278, 107));
        jPanel4.setPreferredSize(new java.awt.Dimension(278, 107));
        jPanel4.setMaximumSize(new java.awt.Dimension(278, 107));
        
        jPanel2.setMinimumSize(new java.awt.Dimension(278, 107));
        jPanel2.setPreferredSize(new java.awt.Dimension(278, 107));
        jPanel2.setMaximumSize(new java.awt.Dimension(278, 107));
        
        jPanel6.setMinimumSize(new java.awt.Dimension(278, 107));
        jPanel6.setPreferredSize(new java.awt.Dimension(278, 107));
        jPanel6.setMaximumSize(new java.awt.Dimension(278, 107));
    }
    
    private String userRole;
    
    private void setupTableStyle() {

    java.awt.Color lightPink = new java.awt.Color(252, 245, 253);
    java.awt.Color alternatePink = new java.awt.Color(246, 234, 249);
    java.awt.Color purple = new java.awt.Color(116, 105, 171);
    java.awt.Color textBlue = new java.awt.Color(46, 69, 121);
    java.awt.Color selectedPurple = new java.awt.Color(226, 213, 248);

    // Table background and selection
    tblRecentSales.setBackground(lightPink);
    tblRecentSales.setForeground(textBlue);
    tblRecentSales.setFont(
            new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14)
    );
    tblRecentSales.setRowHeight(48);
    tblRecentSales.setShowGrid(false);
    tblRecentSales.setIntercellSpacing(new java.awt.Dimension(0, 0));
    tblRecentSales.setSelectionBackground(selectedPurple);
    tblRecentSales.setSelectionForeground(textBlue);
    tblRecentSales.setFillsViewportHeight(true);
    
    // Background behind empty rows
    jScrollPane2.setBackground(lightPink);
    jScrollPane2.getViewport().setBackground(lightPink);
    jScrollPane2.setOpaque(true);
    jScrollPane2.getViewport().setOpaque(true);

    // Header renderer: explicitly paint purple with white text
    javax.swing.table.JTableHeader header = tblRecentSales.getTableHeader();

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

    for (int i = 0; i < tblRecentSales.getColumnCount(); i++) {
        tblRecentSales.getColumnModel()
                .getColumn(i)
                .setCellRenderer(cellRenderer);
    }
}
    
    private MainMenuFrame mainMenu;
    
    private void loadDashboardData() {

    // 1. Total Bouquets
    try {
        BouquetDAO bouquetDAO = new BouquetDAO();

        lblTotalBouquets.setText(
            "Total Bouquets: " + bouquetDAO.getAllBouquets().size()
        );

    } catch (SQLException e) {
        lblTotalBouquets.setText("Total Bouquets: Error");
        System.err.println("Bouquet error: " + e.getMessage());
    }

    // 2. Total Sales
    String salesSql =
        "SELECT COALESCE(SUM(total_amount), 0) AS total FROM sales";

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(salesSql);
         ResultSet rs = stmt.executeQuery()) {

        if (rs.next()) {
            lblTotalSales.setText(
                "Total Sales: ₱" +
                String.format("%,.2f", rs.getDouble("total"))
            );
        }

    } catch (SQLException e) {
        lblTotalSales.setText("Total Sales: Error");
        System.err.println("Sales error: " + e.getMessage());
    }

    // 3. Items Sold
    String itemsSql =
        "SELECT COALESCE(SUM(quantity), 0) AS total FROM sale_details";

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(itemsSql);
         ResultSet rs = stmt.executeQuery()) {

        if (rs.next()) {
            lblItemsSold.setText(
                "Items Sold: " + rs.getInt("total")
            );
        }

    } catch (SQLException e) {
        lblItemsSold.setText("Items Sold: Error");
        System.err.println("Items sold error: " + e.getMessage());
    }

    // 4. Registered Users: Admin only
    if ("Admin".equalsIgnoreCase(userRole)) {

        jPanel6.setVisible(true);

        String usersSql = "SELECT COUNT(*) AS total FROM users";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(usersSql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                lblRegisteredUsers.setText(
                    "Registered Users: " + rs.getInt("total")
                );
            }

        } catch (SQLException e) {
            lblRegisteredUsers.setText("Registered Users: Error");
            System.err.println("Users error: " + e.getMessage());
        }

    } else {
        jPanel6.setVisible(false);
    }

    // 5. Recent Sales from MySQL
    loadRecentSalesFromDatabase();
}

private void loadRecentSalesFromDatabase() {

    DefaultTableModel model =
        (DefaultTableModel) tblRecentSales.getModel();

    model.setRowCount(0);

    String sql =
    "SELECT s.sale_id, b.bouquet_name, s.customer_name, " +
    "sd.quantity, (sd.quantity * sd.unit_price) AS item_total, " +
    "s.sale_date " +
    "FROM sales s " +
    "JOIN sale_details sd ON s.sale_id = sd.sale_id " +
    "JOIN bouquets b ON sd.bouquet_id = b.bouquet_id " +
    "ORDER BY s.sale_date DESC " +
    "LIMIT 5";

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            model.addRow(new Object[] {
                rs.getInt("sale_id"),
                rs.getString("bouquet_name"),
                rs.getString("customer_name"),
                rs.getInt("quantity"),
                String.format("₱%,.2f", rs.getDouble("item_total")),
                rs.getTimestamp("sale_date")
            });
        }

    } catch (SQLException e) {
        System.err.println("Recent sales error: " + e.getMessage());
        javax.swing.JOptionPane.showMessageDialog(
            this,
            "Could not load recent sales. Check your SQL table and column names.\n"
                + e.getMessage(),
            "Database Error",
            javax.swing.JOptionPane.ERROR_MESSAGE
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
        lblTitle = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        lblTotalBouquets = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        lblTotalSales = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        lblItemsSold = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        lblRegisteredUsers = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        lblItemsSold3 = new javax.swing.JLabel();
        btnViewAllSales = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblRecentSales = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        lblTotal2 = new javax.swing.JLabel();
        btnReport = new javax.swing.JButton();
        btnAddBouquet = new javax.swing.JButton();
        btnEditDelete = new javax.swing.JButton();
        btnCategory = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(246, 234, 249));

        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(91, 58, 115));
        lblTitle.setText("Dashboard");

        jPanel14.setBackground(new java.awt.Color(249, 239, 252));
        jPanel14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 232, 247), 4));
        jPanel14.setToolTipText("");
        jPanel14.setMaximumSize(new java.awt.Dimension(943, 546));
        jPanel14.setMinimumSize(new java.awt.Dimension(943, 546));

        jPanel3.setBackground(new java.awt.Color(252, 245, 253));
        jPanel3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(239, 232, 247), 4, true));
        jPanel3.setMaximumSize(new java.awt.Dimension(397, 107));

        lblTotalBouquets.setFont(new java.awt.Font("Segoe UI Semibold", 1, 24)); // NOI18N
        lblTotalBouquets.setForeground(new java.awt.Color(46, 69, 121));
        lblTotalBouquets.setText("Total Bouquets:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(lblTotalBouquets)
                .addContainerGap(85, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(lblTotalBouquets)
                .addContainerGap(35, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(252, 245, 253));
        jPanel4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(239, 232, 247), 4, true));
        jPanel4.setMaximumSize(new java.awt.Dimension(397, 107));

        lblTotalSales.setFont(new java.awt.Font("Segoe UI Semibold", 1, 24)); // NOI18N
        lblTotalSales.setForeground(new java.awt.Color(46, 69, 121));
        lblTotalSales.setText("Total Sales:");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(lblTotalSales)
                .addGap(132, 132, 132))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(lblTotalSales)
                .addContainerGap(36, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(252, 245, 253));
        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(239, 232, 247), 4, true));

        lblItemsSold.setFont(new java.awt.Font("Segoe UI Semibold", 0, 24)); // NOI18N
        lblItemsSold.setForeground(new java.awt.Color(46, 69, 121));
        lblItemsSold.setText("Item Sold:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(lblItemsSold)
                .addGap(145, 145, 145))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(lblItemsSold)
                .addContainerGap(35, Short.MAX_VALUE))
        );

        jPanel6.setBackground(new java.awt.Color(252, 245, 253));
        jPanel6.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(239, 232, 247), 4, true));

        lblRegisteredUsers.setFont(new java.awt.Font("Segoe UI Semibold", 0, 24)); // NOI18N
        lblRegisteredUsers.setForeground(new java.awt.Color(46, 69, 121));
        lblRegisteredUsers.setText("Registered Users:");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(lblRegisteredUsers)
                .addGap(67, 67, 67))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(lblRegisteredUsers)
                .addGap(35, 35, 35))
        );

        jPanel7.setBackground(new java.awt.Color(252, 245, 253));
        jPanel7.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(239, 232, 247), 4, true));
        jPanel7.setMaximumSize(new java.awt.Dimension(397, 107));

        lblItemsSold3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 24)); // NOI18N
        lblItemsSold3.setForeground(new java.awt.Color(46, 69, 121));
        lblItemsSold3.setText("Recent Sales");

        btnViewAllSales.setBackground(new java.awt.Color(116, 105, 171));
        btnViewAllSales.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnViewAllSales.setForeground(new java.awt.Color(255, 255, 255));
        btnViewAllSales.setText("View All");
        btnViewAllSales.addActionListener(this::btnViewAllSalesActionPerformed);

        tblRecentSales.setBackground(new java.awt.Color(252, 245, 253));
        tblRecentSales.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        tblRecentSales.setForeground(new java.awt.Color(46, 69, 121));
        tblRecentSales.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tblRecentSales);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(lblItemsSold3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnViewAllSales)
                .addGap(58, 58, 58))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 791, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblItemsSold3)
                    .addComponent(btnViewAllSales))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel5.setBackground(new java.awt.Color(252, 245, 253));
        jPanel5.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(239, 232, 247), 4, true));

        lblTotal2.setFont(new java.awt.Font("Segoe UI Semibold", 1, 24)); // NOI18N
        lblTotal2.setForeground(new java.awt.Color(46, 69, 121));
        lblTotal2.setText("Quick Actions:");

        btnReport.setBackground(new java.awt.Color(245, 239, 250));
        btnReport.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        btnReport.setForeground(new java.awt.Color(46, 69, 121));
        btnReport.setText("Sales Report");
        btnReport.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(224, 219, 243), 2, true));
        btnReport.addActionListener(this::btnReportActionPerformed);

        btnAddBouquet.setBackground(new java.awt.Color(245, 239, 250));
        btnAddBouquet.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        btnAddBouquet.setForeground(new java.awt.Color(46, 69, 121));
        btnAddBouquet.setText("Add Bouquet");
        btnAddBouquet.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(224, 219, 243), 2, true));
        btnAddBouquet.addActionListener(this::btnAddBouquetActionPerformed);

        btnEditDelete.setBackground(new java.awt.Color(245, 239, 250));
        btnEditDelete.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        btnEditDelete.setForeground(new java.awt.Color(46, 69, 121));
        btnEditDelete.setText("Edit/Delete");
        btnEditDelete.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(224, 219, 243), 2, true));
        btnEditDelete.addActionListener(this::btnEditDeleteActionPerformed);

        btnCategory.setBackground(new java.awt.Color(245, 239, 250));
        btnCategory.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        btnCategory.setForeground(new java.awt.Color(46, 69, 121));
        btnCategory.setText("Categories");
        btnCategory.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(224, 219, 243), 2, true));
        btnCategory.addActionListener(this::btnCategoryActionPerformed);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(lblTotal2))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnReport, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnCategory, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnEditDelete, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnAddBouquet, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(17, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(lblTotal2)
                .addGap(29, 29, 29)
                .addComponent(btnAddBouquet, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnEditDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnReport, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(44, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addContainerGap(107, Short.MAX_VALUE)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(87, 87, 87))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTitle))
                .addGap(56, 56, 56))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(lblTitle)
                .addGap(30, 30, 30)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(45, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddBouquetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddBouquetActionPerformed
            if (mainMenu != null) {
        mainMenu.openAddBouquet();
    }
    }//GEN-LAST:event_btnAddBouquetActionPerformed

    private void btnEditDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditDeleteActionPerformed
        if (mainMenu != null) {
        mainMenu.openEditBouquet();
    }
    }//GEN-LAST:event_btnEditDeleteActionPerformed

    private void btnCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCategoryActionPerformed
        if (mainMenu != null) {
        mainMenu.openCategories();
        }
    }//GEN-LAST:event_btnCategoryActionPerformed

    private void btnReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReportActionPerformed
        if (mainMenu != null) {
        mainMenu.openSalesReport();
    }
    }//GEN-LAST:event_btnReportActionPerformed

    private void btnViewAllSalesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewAllSalesActionPerformed
     if (mainMenu != null) {
        mainMenu.openSales();
    }
    }//GEN-LAST:event_btnViewAllSalesActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> {
    MainMenuFrame menu = new MainMenuFrame("Admin");
    menu.setVisible(true);
});
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddBouquet;
    private javax.swing.JButton btnCategory;
    private javax.swing.JButton btnEditDelete;
    private javax.swing.JButton btnReport;
    private javax.swing.JButton btnViewAllSales;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblItemsSold;
    private javax.swing.JLabel lblItemsSold3;
    private javax.swing.JLabel lblRegisteredUsers;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblTotal2;
    private javax.swing.JLabel lblTotalBouquets;
    private javax.swing.JLabel lblTotalSales;
    private javax.swing.JTable tblRecentSales;
    // End of variables declaration//GEN-END:variables
}
