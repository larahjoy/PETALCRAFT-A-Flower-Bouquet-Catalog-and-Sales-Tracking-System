package com.mycompany.flowerbouquet.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.time.ZoneId;
import java.sql.Timestamp;

public class Sale implements Serializable {

    private int saleId;
    private int userId;
    private String customer;
    private double total;
    private LocalDateTime date;

    private List<SaleDetail> details;

    public Sale(
        int saleId,
        int userId,
        String customer,
        double total,
        Timestamp saleDate) {

    this.saleId = saleId;
    this.userId = userId;
    this.customer = customer;
    this.total = total;

    this.date = saleDate.toLocalDateTime();

    this.details = new ArrayList<>();
    }

    public int getSaleId() {
        return saleId;
    }

    public int getUserId() {
        return userId;
    }

    public String getCustomer() {
        return customer;
    }

    public double getTotal() {
        return total;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public List<SaleDetail> getDetails() {
        return details;
    }

    public void addDetail(SaleDetail detail) {

        details.add(detail);

        total += detail.getSubtotal();
    }

    public String getFormattedDate() {

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        return date.format(formatter);
    }
}