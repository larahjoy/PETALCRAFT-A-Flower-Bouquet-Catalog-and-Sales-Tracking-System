package com.mycompany.flowerbouquet.model;

import java.io.Serializable;

public class SaleDetail implements Serializable {

    private int detailId;
    private int saleId;
    private int bouquetId;
    private int quantity;
    private double priceAtSale;
    private double subtotal;

    public SaleDetail(
            int detailId,
            int saleId,
            int bouquetId,
            int quantity,
            double priceAtSale) {

        this.detailId = detailId;
        this.saleId = saleId;
        this.bouquetId = bouquetId;
        this.quantity = quantity;
        this.priceAtSale = priceAtSale;
        this.subtotal = quantity * priceAtSale;
    }

    public int getDetailId() {
        return detailId;
    }

    public int getSaleId() {
        return saleId;
    }

    public int getBouquetId() {
        return bouquetId;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPriceAtSale() {
        return priceAtSale;
    }

    public double getSubtotal() {
        return subtotal;
    }
}