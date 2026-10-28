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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.DayOfWeek;
import java.time.temporal.TemporalAdjusters;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Paul Adanza
 */
public class SalesReportFrame extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(SalesReportFrame.class.getName());

    public SalesReportFrame() {
    initComponents();

    setupTableStyle();

    cmbReportPeriod.setSelectedItem("All Time");

    loadSalesReport();

    setSize(1475, 780);
    setLocationRelativeTo(null);

    lblTotal.setPreferredSize(new java.awt.Dimension(482, 107));
    jPanel3.setMinimumSize(new java.awt.Dimension(482, 107));
    jPanel3.setPreferredSize(new java.awt.Dimension(482, 107));
    jPanel3.setMaximumSize(new java.awt.Dimension(482, 107));

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
                component.setForeground(textBlue);
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
    
    private boolean isWithinSelectedPeriod(LocalDateTime saleDate) {
    if (saleDate == null) {
        return false;
    }

    String period = cmbReportPeriod.getSelectedItem().toString();

    // All Time includes every recorded sale.
    if ("All Time".equals(period)) {
        return true;
    }

    LocalDate today = LocalDate.now();
    LocalDate saleDay = saleDate.toLocalDate();

    if ("Daily".equals(period)) {
        return saleDay.equals(today);
    }

    if ("Weekly".equals(period)) {
        LocalDate monday = today.with(
                TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)
        );

        LocalDate nextMonday = monday.plusWeeks(1);

        return !saleDay.isBefore(monday)
                && saleDay.isBefore(nextMonday);
    }

    if ("Monthly".equals(period)) {
        LocalDate firstDay = today.withDayOfMonth(1);
        LocalDate firstDayNextMonth = firstDay.plusMonths(1);

        return !saleDay.isBefore(firstDay)
                && saleDay.isBefore(firstDayNextMonth);
    }

    return false;
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
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        lblTotal = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        lblTotalSales = new javax.swing.JLabel();
        lblItemsSold2 = new javax.swing.JLabel();
        btnRefresh = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblBouquets = new javax.swing.JTable();
        cmbReportPeriod = new javax.swing.JComboBox<>();
        btnGenerateReport = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(915, 780));
        setSize(new java.awt.Dimension(915, 780));

        pnlHeader.setBackground(new java.awt.Color(246, 234, 249));
        pnlHeader.setMaximumSize(new java.awt.Dimension(915, 780));
        pnlHeader.setMinimumSize(new java.awt.Dimension(915, 780));
        pnlHeader.setPreferredSize(new java.awt.Dimension(915, 780));

        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(91, 58, 115));
        lblTitle.setText("Sales Report");

        jPanel1.setBackground(new java.awt.Color(249, 239, 252));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 232, 247), 4));
        jPanel1.setToolTipText("");
        jPanel1.setMaximumSize(new java.awt.Dimension(943, 546));
        jPanel1.setMinimumSize(new java.awt.Dimension(943, 546));

        jPanel2.setBackground(new java.awt.Color(252, 245, 253));
        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(239, 232, 247), 4, true));

        lblTotal.setFont(new java.awt.Font("Segoe UI Semibold", 0, 24)); // NOI18N
        lblTotal.setForeground(new java.awt.Color(46, 69, 121));
        lblTotal.setText("Total Revenue:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(lblTotal)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(lblTotal)
                .addGap(37, 37, 37))
        );

        jPanel3.setBackground(new java.awt.Color(252, 245, 253));
        jPanel3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(239, 232, 247), 4, true));
        jPanel3.setMaximumSize(new java.awt.Dimension(397, 107));

        lblTotalSales.setFont(new java.awt.Font("Segoe UI Semibold", 1, 24)); // NOI18N
        lblTotalSales.setForeground(new java.awt.Color(46, 69, 121));
        lblTotalSales.setText("Total Sales:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(lblTotalSales)
                .addContainerGap(329, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(lblTotalSales)
                .addContainerGap(34, Short.MAX_VALUE))
        );

        lblItemsSold2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 24)); // NOI18N
        lblItemsSold2.setForeground(new java.awt.Color(46, 69, 121));
        lblItemsSold2.setText("Sales Summary:");

        btnRefresh.setBackground(new java.awt.Color(116, 105, 171));
        btnRefresh.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnRefresh.setForeground(new java.awt.Color(255, 255, 255));
        btnRefresh.setText("Refresh");
        btnRefresh.addActionListener(this::btnRefreshActionPerformed);

        tblBouquets.setBackground(new java.awt.Color(252, 245, 253));
        tblBouquets.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        tblBouquets.setForeground(new java.awt.Color(46, 69, 121));
        tblBouquets.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Sale ID", "Bouquet Name", "Customer", "Quantity", "Price", "Total", "Date"
            }
        ));
        jScrollPane2.setViewportView(tblBouquets);

        cmbReportPeriod.setBackground(new java.awt.Color(255, 255, 255));
        cmbReportPeriod.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All Time", "Daily", "Weekly", "Monthly" }));
        cmbReportPeriod.addActionListener(this::cmbReportPeriodActionPerformed);

        btnGenerateReport.setBackground(new java.awt.Color(116, 105, 171));
        btnGenerateReport.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnGenerateReport.setForeground(new java.awt.Color(255, 255, 255));
        btnGenerateReport.setText("Generate Report");
        btnGenerateReport.addActionListener(this::btnGenerateReportActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(196, 196, 196)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(209, 209, 209))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 953, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(1019, 1019, 1019)
                            .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(196, 196, 196)
                            .addComponent(lblItemsSold2)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnGenerateReport)
                            .addGap(18, 18, 18)
                            .addComponent(cmbReportPeriod, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(211, 211, 211))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblItemsSold2)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cmbReportPeriod, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnGenerateReport, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlHeaderLayout = new javax.swing.GroupLayout(pnlHeader);
        pnlHeader.setLayout(pnlHeaderLayout);
        pnlHeaderLayout.setHorizontalGroup(
            pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHeaderLayout.createSequentialGroup()
                .addGroup(pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlHeaderLayout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(lblTitle))
                    .addGroup(pnlHeaderLayout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(54, 54, 54))
        );
        pnlHeaderLayout.setVerticalGroup(
            pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHeaderLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(lblTitle)
                .addGap(36, 36, 36)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(52, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlHeader, javax.swing.GroupLayout.DEFAULT_SIZE, 1475, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
    loadSalesReport();
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void cmbReportPeriodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbReportPeriodActionPerformed
    loadSalesReport();
    }//GEN-LAST:event_cmbReportPeriodActionPerformed

    private void btnGenerateReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerateReportActionPerformed
    loadSalesReport();
    }//GEN-LAST:event_btnGenerateReportActionPerformed

    private void loadSalesReport() {

    DefaultTableModel model =
            (DefaultTableModel) tblBouquets.getModel();

    model.setRowCount(0);

    String period = cmbReportPeriod.getSelectedItem().toString();

    LocalDate today = LocalDate.now();
    LocalDate startDate;
    LocalDate endDate;

    switch (period) {
        case "Daily":
            startDate = today;
            endDate = today.plusDays(1);
            break;

        case "Weekly":
            startDate = today.with(
                    TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)
            );
            endDate = startDate.plusWeeks(1);
            break;

        case "Monthly":
            startDate = today.withDayOfMonth(1);
            endDate = startDate.plusMonths(1);
            break;

        default:
            startDate = null;
            endDate = null;
            break;
    }

    String sql = """
        SELECT
            s.sale_id,
            s.customer_name,
            s.sale_date,
            sd.quantity,
            sd.unit_price,
            (sd.quantity * sd.unit_price) AS subtotal,
            b.bouquet_name
        FROM sales s
        INNER JOIN sale_details sd
            ON s.sale_id = sd.sale_id
        LEFT JOIN bouquets b
            ON sd.bouquet_id = b.bouquet_id
        WHERE (? IS NULL OR s.sale_date >= ?)
          AND (? IS NULL OR s.sale_date < ?)
        ORDER BY s.sale_date DESC, s.sale_id DESC
        """;

    double totalRevenue = 0;
    int itemsSold = 0;
    int totalSales = 0;

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement pst = conn.prepareStatement(sql)) {

        if (startDate == null) {
            pst.setTimestamp(1, null);
            pst.setTimestamp(2, null);
            pst.setTimestamp(3, null);
            pst.setTimestamp(4, null);
        } else {
            Timestamp startTimestamp =
                    Timestamp.valueOf(startDate.atStartOfDay());

            Timestamp endTimestamp =
                    Timestamp.valueOf(endDate.atStartOfDay());

            pst.setTimestamp(1, startTimestamp);
            pst.setTimestamp(2, startTimestamp);
            pst.setTimestamp(3, endTimestamp);
            pst.setTimestamp(4, endTimestamp);
        }

        try (ResultSet rs = pst.executeQuery()) {

            DateTimeFormatter formatter =
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

            while (rs.next()) {

                int saleId = rs.getInt("sale_id");
                String bouquetName = rs.getString("bouquet_name");

                if (bouquetName == null) {
                    bouquetName = "Unknown Bouquet";
                }

                String customerName =
                        rs.getString("customer_name");

                if (customerName == null ||
                        customerName.trim().isEmpty()) {
                    customerName = "Walk-in Customer";
                }

                int quantity = rs.getInt("quantity");
                double price = rs.getDouble("unit_price");
                double subtotal = rs.getDouble("subtotal");

                Timestamp saleTimestamp =
                        rs.getTimestamp("sale_date");

                String formattedDate = "";

                if (saleTimestamp != null) {
                    formattedDate = saleTimestamp.toLocalDateTime()
                            .format(formatter);
                }

                model.addRow(new Object[]{
                    saleId,
                    bouquetName,
                    customerName,
                    quantity,
                    String.format(Locale.US, "%.2f", price),
                    String.format(Locale.US, "%.2f", subtotal),
                    formattedDate
                });

                totalRevenue += subtotal;
                itemsSold += quantity;
            }
        }

        // Count distinct sales, not individual bouquet rows.
        String countSql = """
            SELECT COUNT(*) AS total_sales
            FROM sales s
            WHERE (? IS NULL OR s.sale_date >= ?)
              AND (? IS NULL OR s.sale_date < ?)
            """;

        try (PreparedStatement countPst =
                     conn.prepareStatement(countSql)) {

            if (startDate == null) {
                countPst.setTimestamp(1, null);
                countPst.setTimestamp(2, null);
                countPst.setTimestamp(3, null);
                countPst.setTimestamp(4, null);
            } else {
                Timestamp startTimestamp =
                        Timestamp.valueOf(startDate.atStartOfDay());

                Timestamp endTimestamp =
                        Timestamp.valueOf(endDate.atStartOfDay());

                countPst.setTimestamp(1, startTimestamp);
                countPst.setTimestamp(2, startTimestamp);
                countPst.setTimestamp(3, endTimestamp);
                countPst.setTimestamp(4, endTimestamp);
            }

            try (ResultSet countRs = countPst.executeQuery()) {
                if (countRs.next()) {
                    totalSales = countRs.getInt("total_sales");
                }
            }
        }

        lblTotal.setText(
                "Total Revenue: ₱"
                + String.format(Locale.US, "%.2f", totalRevenue)
        );

        lblTotalSales.setText("Total Sales: " + totalSales);
        lblItemsSold2.setText("Items Sold: " + itemsSold);

    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(
                this,
                "Failed to load sales report:\n" + ex.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
}
    
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
        java.awt.EventQueue.invokeLater(() -> new SalesReportFrame().setVisible(true));
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGenerateReport;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JComboBox<String> cmbReportPeriod;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblItemsSold2;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JLabel lblTotalSales;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JTable tblBouquets;
    // End of variables declaration//GEN-END:variables
}
