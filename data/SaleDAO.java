package com.mycompany.flowerbouquet.data;

import com.mycompany.flowerbouquet.model.Sale;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class SaleDAO {

    public List<Sale> getRecentSales(int limit) throws SQLException {
        List<Sale> sales = new ArrayList<>();

        String sql = """
            SELECT sale_id, user_id, customer_name,
                   total_amount, sale_date
            FROM sales
            ORDER BY sale_date DESC
            LIMIT ?
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, Math.max(1, limit));

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int userId = rs.getInt("user_id");

                    if (rs.wasNull()) {
                        userId = 0;
                    }

                    Timestamp saleDate = rs.getTimestamp("sale_date");

                    if (saleDate == null) {
                        continue;
                    }

                    Sale sale = new Sale(
                        rs.getInt("sale_id"),
                        userId,
                        rs.getString("customer_name"),
                        rs.getDouble("total_amount"),
                        saleDate
                    );

                    sales.add(sale);
                }
            }
        }

        return sales;
    }
}