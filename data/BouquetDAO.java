/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.flowerbouquet.data;

import com.mycompany.flowerbouquet.model.Bouquet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BouquetDAO {

    // READ: Get active bouquets
    public List<Bouquet> getAllBouquets() throws SQLException {
        List<Bouquet> bouquets = new ArrayList<>();

        String sql = """
            SELECT bouquet_id, bouquet_name, description,
                   price, category_id, quantity, image_path
            FROM bouquets
            WHERE is_archived = 0
            ORDER BY bouquet_id
            """;

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Bouquet b = new Bouquet(
                    rs.getInt("bouquet_id"),
                    rs.getString("bouquet_name"),
                    rs.getString("description"),
                    rs.getDouble("price"),
                    rs.getInt("category_id"),
                    rs.getInt("quantity")
                );

                b.setImagePath(rs.getString("image_path"));
                bouquets.add(b);
            }
        }

        return bouquets;
    }

    // CREATE: Add a bouquet
    public boolean addBouquet(Bouquet b) throws SQLException {
        String sql = """
            INSERT INTO bouquets
            (bouquet_name, description, price, category_id,
             quantity, image_path, is_archived)
            VALUES (?, ?, ?, ?, ?, ?, 0)
            """;

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, b.getName());
            ps.setString(2, b.getDescription());
            ps.setDouble(3, b.getPrice());
            ps.setInt(4, b.getCategoryId());
            ps.setInt(5, b.getQuantity());
            ps.setString(6, b.getImagePath());

            return ps.executeUpdate() > 0;
        }
    }

    // UPDATE: Edit a bouquet
    public boolean updateBouquet(Bouquet b) throws SQLException {
        String sql = """
            UPDATE bouquets
            SET bouquet_name = ?, description = ?, price = ?,
                category_id = ?, quantity = ?, image_path = ?
            WHERE bouquet_id = ? AND is_archived = 0
            """;

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, b.getName());
            ps.setString(2, b.getDescription());
            ps.setDouble(3, b.getPrice());
            ps.setInt(4, b.getCategoryId());
            ps.setInt(5, b.getQuantity());
            ps.setString(6, b.getImagePath());
            ps.setInt(7, b.getId());

            return ps.executeUpdate() > 0;
        }
    }

    // ARCHIVE: Hide a bouquet without deleting it
    public boolean archiveBouquet(int id) throws SQLException {
        String sql = """
            UPDATE bouquets
            SET is_archived = 1
            WHERE bouquet_id = ? AND is_archived = 0
            """;

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    // READ: Get archived bouquets
    public List<Bouquet> getArchivedBouquets() throws SQLException {
        List<Bouquet> bouquets = new ArrayList<>();

        String sql = """
            SELECT bouquet_id, bouquet_name, description,
                   price, category_id, quantity, image_path
            FROM bouquets
            WHERE is_archived = 1
            ORDER BY bouquet_id
            """;

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Bouquet b = new Bouquet(
                    rs.getInt("bouquet_id"),
                    rs.getString("bouquet_name"),
                    rs.getString("description"),
                    rs.getDouble("price"),
                    rs.getInt("category_id"),
                    rs.getInt("quantity")
                );

                b.setImagePath(rs.getString("image_path"));
                bouquets.add(b);
            }
        }

        return bouquets;
    }

    // RESTORE: Return a bouquet to the active catalog
    public boolean restoreBouquet(int id) throws SQLException {
        String sql = """
            UPDATE bouquets
            SET is_archived = 0
            WHERE bouquet_id = ? AND is_archived = 1
            """;

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    // PERMANENT DELETE: Remove a bouquet from the database
    public boolean deleteBouquet(int id) throws SQLException {
        String sql = "DELETE FROM bouquets WHERE bouquet_id = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }
}