/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.flowerbouquet.data;

import com.mycompany.flowerbouquet.model.Bouquet;
import java.sql.SQLException;
import java.util.List;

public class TestBouquetDAO {

    public static void main(String[] args) {
        BouquetDAO dao = new BouquetDAO();

        try {
            List<Bouquet> bouquets = dao.getAllBouquets();

            System.out.println("Database connected!");
            System.out.println("Active bouquets found: " + bouquets.size());

            for (Bouquet b : bouquets) {
                System.out.println(
                    b.getId() + " | " +
                    b.getName() + " | " +
                    b.getPrice()
                );
            }

        } catch (SQLException e) {
            System.out.println("Database test failed:");
            e.printStackTrace();
        }
    }
}