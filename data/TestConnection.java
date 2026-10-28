/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.flowerbouquet.data;

public class TestConnection {

    public static void main(String[] args) {

        try (java.sql.Connection con =
                DatabaseConnection.getConnection()) {

            System.out.println("Database Connected!");

        } catch (java.sql.SQLException e) {

            System.out.println("Connection Failed!");
            e.printStackTrace();
        }
    }
}