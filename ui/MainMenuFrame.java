/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.flowerbouquet.ui;

import javax.swing.JOptionPane;

/**
 *
 * @author Paul Adanza
 */
public class MainMenuFrame extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(MainMenuFrame.class.getName());

    private String username;
    private String userRole;

    public MainMenuFrame(String username) {
    this(username, "Guest");
    }

        public MainMenuFrame(String username, String userRole) {
        initComponents();

        this.username = username;
        this.userRole = userRole;

        setSize(1920, 1080);
        setLocationRelativeTo(null);
        
        jPanel2.setMinimumSize(new java.awt.Dimension(308, 809));
        jPanel2.setPreferredSize(new java.awt.Dimension(308, 809));
        jPanel2.setMaximumSize(new java.awt.Dimension(308, 809));
        
        pnlContent.setMinimumSize(new java.awt.Dimension(1475, 750));
        pnlContent.setPreferredSize(new java.awt.Dimension(1475, 750));
        pnlContent.setMaximumSize(new java.awt.Dimension(1475, 750));
        
        lblWelcome.setText(username);
        
        btnDashboard.setBorder(null);
        btnDashboard.setBorderPainted(false);
        btnDashboard.setFocusPainted(false);
        btnDashboard.setContentAreaFilled(true);
        btnDashboard.setOpaque(true);
        btnDashboard.setFocusable(false);
        btnDashboard.setBackground(new java.awt.Color(246,234,249));
        btnDashboard.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        
        btnCatalog.setBorder(null);
        btnCatalog.setBorderPainted(false);
        btnCatalog.setFocusPainted(false);
        btnCatalog.setContentAreaFilled(true);
        btnCatalog.setOpaque(true);
        btnCatalog.setFocusable(false);
        btnCatalog.setBackground(new java.awt.Color(246,234,249));
        btnCatalog.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        
        btnAddBouquet.setBorder(null);
        btnAddBouquet.setBorderPainted(false);
        btnAddBouquet.setFocusPainted(false);
        btnAddBouquet.setContentAreaFilled(true);
        btnAddBouquet.setOpaque(true);
        btnAddBouquet.setFocusable(false);
        btnAddBouquet.setBackground(new java.awt.Color(246,234,249));
        btnAddBouquet.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        
        btnEditDelete.setBorder(null);
        btnEditDelete.setBorderPainted(false);
        btnEditDelete.setFocusPainted(false);
        btnEditDelete.setContentAreaFilled(true);
        btnEditDelete.setOpaque(true);
        btnEditDelete.setFocusable(false);
        btnEditDelete.setBackground(new java.awt.Color(246,234,249));
        btnEditDelete.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        
        btnArchive.setBorder(null);
        btnArchive.setBorderPainted(false);
        btnArchive.setFocusPainted(false);
        btnArchive.setContentAreaFilled(true);
        btnArchive.setOpaque(true);
        btnArchive.setFocusable(false);
        btnArchive.setBackground(new java.awt.Color(246,234,249));
        btnArchive.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        
        
        btnCategory.setBorder(null);
        btnCategory.setBorderPainted(false);
        btnCategory.setFocusPainted(false);
        btnCategory.setContentAreaFilled(true);
        btnCategory.setOpaque(true);
        btnCategory.setFocusable(false);
        btnCategory.setBackground(new java.awt.Color(246,234,249));
        btnCategory.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        
        btnSales.setBorder(null);
        btnSales.setBorderPainted(false);
        btnSales.setFocusPainted(false);
        btnSales.setContentAreaFilled(true);
        btnSales.setOpaque(true);
        btnSales.setFocusable(false);
        btnSales.setBackground(new java.awt.Color(246,234,249));
        btnSales.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        
        btnReport.setBorder(null);
        btnReport.setBorderPainted(false);
        btnReport.setFocusPainted(false);
        btnReport.setContentAreaFilled(true);
        btnReport.setOpaque(true);
        btnReport.setFocusable(false);
        btnReport.setBackground(new java.awt.Color(246,234,249));
        btnReport.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        
        btnManageUsers.setBorder(null);
        btnManageUsers.setBorderPainted(false);
        btnManageUsers.setFocusPainted(false);
        btnManageUsers.setContentAreaFilled(true);
        btnManageUsers.setOpaque(true);
        btnManageUsers.setFocusable(false);
        btnManageUsers.setBackground(new java.awt.Color(246,234,249));
        btnManageUsers.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        
        btnInventory.setBorder(null);
        btnInventory.setBorderPainted(false);
        btnInventory.setFocusPainted(false);
        btnInventory.setContentAreaFilled(true);
        btnInventory.setOpaque(true);
        btnInventory.setFocusable(false);
        btnInventory.setBackground(new java.awt.Color(246,234,249));
        btnInventory.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        
        // Style sidebar buttons
    javax.swing.JButton[] buttons = {
        btnDashboard, btnCatalog, btnAddBouquet,
        btnEditDelete, btnArchive, btnCategory,
        btnSales, btnReport, btnManageUsers, btnInventory
    };

    for (javax.swing.JButton button : buttons) {
        button.setBorder(null);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(true);
        button.setOpaque(true);
        button.setFocusable(false);
        button.setBackground(new java.awt.Color(246, 234, 249));
        button.setUI(new javax.swing.plaf.basic.BasicButtonUI());
    }

    // Restrict sidebar options based on role
    if ("Staff".equalsIgnoreCase(userRole)) {
        setStaffMode();
    } else if ("Guest".equalsIgnoreCase(userRole)) {
        setStaffMode();
        btnAddBouquet.setVisible(false);
        btnEditDelete.setVisible(false);
        btnArchive.setVisible(false);
        btnSales.setVisible(false);
        btnReport.setVisible(false);
    }

    showDashboard();
    }
    
    public void openAddBouquet() {
    setActiveButton(btnAddBouquet);
    showFrameContent(new AddBouquetFrame());
}

public void openEditBouquet() {
    setActiveButton(btnEditDelete);
    showFrameContent(new EditDeleteBouquetFrame());
}

public void openCategories() {
    setActiveButton(btnCategory);
    showFrameContent(new CategoryFrame());
}

public void openSalesReport() {
    setActiveButton(btnReport);
    showFrameContent(new SalesReportFrame());
}

public void openSales() {
    setActiveButton(btnSales);
    showFrameContent(new SoldBouquetsFrame());
}

    private void showDashboard() {
    setActiveButton(btnDashboard);
    showFrameContent(new DashboardFrame(this, userRole));
}    

    public void setStaffMode() {

    // Staff cannot manage users or inventory
    btnManageUsers.setVisible(false);
    btnInventory.setVisible(false);

    // Staff can access these features
    btnCategory.setVisible(true);
    btnCatalog.setVisible(true);
    btnAddBouquet.setVisible(true);
    btnEditDelete.setVisible(true);
    btnArchive.setVisible(true);
    btnSales.setVisible(true);
    btnReport.setVisible(true);
}
    
    
    private void showFrameContent(javax.swing.JFrame frame) {

    pnlContent.removeAll();

    java.awt.Container content =
            frame.getContentPane();

    frame.setVisible(false);

    pnlContent.setLayout(
            new java.awt.BorderLayout()
    );

    pnlContent.add(
            content,
            java.awt.BorderLayout.CENTER
    );

    pnlContent.revalidate();
    pnlContent.repaint();
    
} 
    
    private void setActiveButton(javax.swing.JButton activeButton) {

    java.awt.Color defaultBackground =
            new java.awt.Color(246, 234, 249);

    java.awt.Color defaultText =
            new java.awt.Color(46, 69, 121);

    java.awt.Color activeBackground =
            new java.awt.Color(116, 105, 171);

    // Reset all sidebar buttons to their default colors
    javax.swing.JButton[] buttons = {
        btnDashboard,
        btnCatalog,
        btnAddBouquet,
        btnEditDelete,
        btnArchive,
        btnCategory,
        btnSales,
        btnReport,
        btnManageUsers,
        btnInventory
    };

    for (javax.swing.JButton button : buttons) {
        button.setBackground(defaultBackground);
        button.setForeground(defaultText);
    }

    // Highlight only the selected button
    activeButton.setBackground(activeBackground);
    activeButton.setForeground(java.awt.Color.WHITE);
}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlMain = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        lblLogo = new javax.swing.JLabel();
        lblTitle2 = new javax.swing.JLabel();
        btnLogout = new javax.swing.JButton();
        lblLogged = new javax.swing.JLabel();
        lblWelcome = new javax.swing.JLabel();
        lblTitle3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnCatalog = new javax.swing.JButton();
        btnAddBouquet = new javax.swing.JButton();
        btnEditDelete = new javax.swing.JButton();
        btnCategory = new javax.swing.JButton();
        btnSales = new javax.swing.JButton();
        btnReport = new javax.swing.JButton();
        btnDashboard = new javax.swing.JButton();
        btnManageUsers = new javax.swing.JButton();
        btnInventory = new javax.swing.JButton();
        btnArchive = new javax.swing.JButton();
        pnlContent = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(252, 245, 253));
        setMinimumSize(new java.awt.Dimension(900, 700));
        setSize(new java.awt.Dimension(900, 700));

        pnlMain.setBackground(new java.awt.Color(252, 245, 253));
        pnlMain.setMaximumSize(new java.awt.Dimension(1920, 1080));
        pnlMain.setMinimumSize(new java.awt.Dimension(900, 700));

        jPanel1.setBackground(new java.awt.Color(246, 234, 249));
        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(239, 232, 247), 4, true));
        jPanel1.setMaximumSize(new java.awt.Dimension(1920, 1920));
        jPanel1.setMinimumSize(new java.awt.Dimension(0, 0));

        lblLogo.setBackground(new java.awt.Color(91, 58, 115));
        lblLogo.setFont(new java.awt.Font("Serif", 1, 50)); // NOI18N
        lblLogo.setForeground(new java.awt.Color(91, 58, 115));
        lblLogo.setText("PETALCRAFT");

        lblTitle2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        lblTitle2.setForeground(new java.awt.Color(46, 69, 121));
        lblTitle2.setText("A FLOWER BOUQUET CATALOG SYSTEM");

        btnLogout.setBackground(new java.awt.Color(116, 105, 171));
        btnLogout.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnLogout.setForeground(new java.awt.Color(255, 255, 255));
        btnLogout.setText("Logout");
        btnLogout.addActionListener(this::btnLogoutActionPerformed);

        lblLogged.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        lblLogged.setForeground(new java.awt.Color(46, 69, 121));
        lblLogged.setText("Logged in as:");

        lblWelcome.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        lblWelcome.setForeground(new java.awt.Color(46, 69, 121));
        lblWelcome.setText("Username");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(130, 130, 130)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblLogo)
                    .addComponent(lblTitle2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblLogged)
                    .addComponent(lblWelcome)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(187, 187, 187))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(lblLogged)
                .addGap(6, 6, 6)
                .addComponent(lblWelcome)
                .addGap(16, 16, 16)
                .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(lblLogo)
                .addGap(6, 6, 6)
                .addComponent(lblTitle2)
                .addContainerGap())
        );

        lblTitle3.setFont(new java.awt.Font("Gabriola", 2, 30)); // NOI18N
        lblTitle3.setForeground(new java.awt.Color(46, 69, 121));
        lblTitle3.setText("Fresh Flowers       Special Moments       Petal Craft ");

        jPanel2.setBackground(new java.awt.Color(246, 234, 249));
        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(239, 232, 247), 4, true));

        btnCatalog.setBackground(new java.awt.Color(246, 234, 249));
        btnCatalog.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        btnCatalog.setForeground(new java.awt.Color(46, 69, 121));
        btnCatalog.setText("Catalog");
        btnCatalog.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(224, 219, 243), 4, true));
        btnCatalog.setBorderPainted(false);
        btnCatalog.setFocusPainted(false);
        btnCatalog.addActionListener(this::btnCatalogActionPerformed);

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

        btnSales.setBackground(new java.awt.Color(245, 239, 250));
        btnSales.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        btnSales.setForeground(new java.awt.Color(46, 69, 121));
        btnSales.setText("Sales");
        btnSales.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(224, 219, 243), 2, true));
        btnSales.addActionListener(this::btnSalesActionPerformed);

        btnReport.setBackground(new java.awt.Color(245, 239, 250));
        btnReport.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        btnReport.setForeground(new java.awt.Color(46, 69, 121));
        btnReport.setText("Sales Report");
        btnReport.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(224, 219, 243), 2, true));
        btnReport.addActionListener(this::btnReportActionPerformed);

        btnDashboard.setBackground(new java.awt.Color(246, 234, 249));
        btnDashboard.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        btnDashboard.setForeground(new java.awt.Color(46, 69, 121));
        btnDashboard.setText("Dashboard");
        btnDashboard.setBorder(null);
        btnDashboard.setBorderPainted(false);
        btnDashboard.setFocusPainted(false);
        btnDashboard.addActionListener(this::btnDashboardActionPerformed);

        btnManageUsers.setBackground(new java.awt.Color(245, 239, 250));
        btnManageUsers.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        btnManageUsers.setForeground(new java.awt.Color(46, 69, 121));
        btnManageUsers.setText("Manage Users");
        btnManageUsers.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(224, 219, 243), 2, true));
        btnManageUsers.addActionListener(this::btnManageUsersActionPerformed);

        btnInventory.setBackground(new java.awt.Color(245, 239, 250));
        btnInventory.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        btnInventory.setForeground(new java.awt.Color(46, 69, 121));
        btnInventory.setText("Manage Inventory");
        btnInventory.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(224, 219, 243), 2, true));
        btnInventory.addActionListener(this::btnInventoryActionPerformed);

        btnArchive.setBackground(new java.awt.Color(245, 239, 250));
        btnArchive.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        btnArchive.setForeground(new java.awt.Color(46, 69, 121));
        btnArchive.setText("Archive");
        btnArchive.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(224, 219, 243), 2, true));
        btnArchive.addActionListener(this::btnArchiveActionPerformed);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnCatalog, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnAddBouquet, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
            .addComponent(btnCategory, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnSales, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnReport, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnDashboard, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnManageUsers, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
            .addComponent(btnInventory, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
            .addComponent(btnEditDelete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnArchive, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnDashboard, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCatalog, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAddBouquet, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEditDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnArchive, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSales, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnReport, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnManageUsers, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnInventory, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(141, Short.MAX_VALUE))
        );

        pnlContent.setBackground(new java.awt.Color(246, 234, 249));

        javax.swing.GroupLayout pnlContentLayout = new javax.swing.GroupLayout(pnlContent);
        pnlContent.setLayout(pnlContentLayout);
        pnlContentLayout.setHorizontalGroup(
            pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1475, Short.MAX_VALUE)
        );
        pnlContentLayout.setVerticalGroup(
            pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 750, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout pnlMainLayout = new javax.swing.GroupLayout(pnlMain);
        pnlMain.setLayout(pnlMainLayout);
        pnlMainLayout.setHorizontalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlMainLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(pnlContent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlMainLayout.createSequentialGroup()
                        .addGap(508, 508, 508)
                        .addComponent(lblTitle3)))
                .addContainerGap(60, Short.MAX_VALUE))
        );
        pnlMainLayout.setVerticalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlMainLayout.createSequentialGroup()
                        .addComponent(pnlContent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21)
                        .addComponent(lblTitle3))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(87, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddBouquetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddBouquetActionPerformed
        setActiveButton(btnAddBouquet);

    AddBouquetFrame addBouquet =
            new AddBouquetFrame();

    showFrameContent(addBouquet);
    }//GEN-LAST:event_btnAddBouquetActionPerformed

    private void btnCatalogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCatalogActionPerformed
        setActiveButton(btnCatalog);

    CatalogFrame catalog =
            new CatalogFrame();

    showFrameContent(catalog);
    }//GEN-LAST:event_btnCatalogActionPerformed

    private void btnSalesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalesActionPerformed
        setActiveButton(btnSales);

    SoldBouquetsFrame sales =
            new SoldBouquetsFrame();

    showFrameContent(sales);
    }//GEN-LAST:event_btnSalesActionPerformed

    private void btnEditDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditDeleteActionPerformed
        setActiveButton(btnEditDelete);

    EditDeleteBouquetFrame editDelete =
            new EditDeleteBouquetFrame();

    showFrameContent(editDelete);
    }//GEN-LAST:event_btnEditDeleteActionPerformed

    private void btnReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReportActionPerformed
        setActiveButton(btnReport);

    SalesReportFrame report =
            new SalesReportFrame();

    showFrameContent(report);
    }//GEN-LAST:event_btnReportActionPerformed

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        int choice = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to logout?",
            "Logout",
            JOptionPane.YES_NO_OPTION
    );

    if (choice == JOptionPane.YES_OPTION) {

        new LoginFrame().setVisible(true);
        this.dispose();
    }
    }//GEN-LAST:event_btnLogoutActionPerformed

    private void btnCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCategoryActionPerformed
        setActiveButton(btnCategory);

    CategoryFrame category =
            new CategoryFrame();

    showFrameContent(category);
    }//GEN-LAST:event_btnCategoryActionPerformed

    private void btnDashboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDashboardActionPerformed
        setActiveButton(btnDashboard);
        
        showDashboard();
    }//GEN-LAST:event_btnDashboardActionPerformed

    private void btnManageUsersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnManageUsersActionPerformed
        setActiveButton(btnManageUsers);
        ManageUsersFrame users =
            new ManageUsersFrame();
        showFrameContent(new ManageUsersFrame());
    }//GEN-LAST:event_btnManageUsersActionPerformed

    private void btnInventoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInventoryActionPerformed
        setActiveButton(btnInventory);
        ManageInventoryFrame inventory =
            new ManageInventoryFrame();
        showFrameContent(new ManageInventoryFrame());
    }//GEN-LAST:event_btnInventoryActionPerformed

    private void btnArchiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnArchiveActionPerformed
        setActiveButton(btnArchive);
        ArchiveFrame archiveFrame = (new ArchiveFrame());

        pnlContent.removeAll();
        pnlContent.setLayout(new java.awt.BorderLayout());
        pnlContent.add(archiveFrame.getContentPane(), java.awt.BorderLayout.CENTER);
        pnlContent.revalidate();
        pnlContent.repaint();
    }//GEN-LAST:event_btnArchiveActionPerformed

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
        java.awt.EventQueue.invokeLater(() ->
    new MainMenuFrame("Admin", "Admin").setVisible(true));
        
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddBouquet;
    private javax.swing.JButton btnArchive;
    private javax.swing.JButton btnCatalog;
    private javax.swing.JButton btnCategory;
    private javax.swing.JButton btnDashboard;
    private javax.swing.JButton btnEditDelete;
    private javax.swing.JButton btnInventory;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnManageUsers;
    private javax.swing.JButton btnReport;
    private javax.swing.JButton btnSales;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblLogged;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblTitle2;
    private javax.swing.JLabel lblTitle3;
    private javax.swing.JLabel lblWelcome;
    private javax.swing.JPanel pnlContent;
    private javax.swing.JPanel pnlMain;
    // End of variables declaration//GEN-END:variables
}


