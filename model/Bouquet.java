package com.mycompany.flowerbouquet.model;

import com.mycompany.flowerbouquet.data.DataStore;

public class Bouquet {

    private int id;
    private String name;
    private String description;
    private double price;
    private int categoryId;
    private int quantity;
    private String imagePath = "";

    public Bouquet(
            int id,
            String name,
            String description,
            double price,
            int categoryId,
            int quantity) {

        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.categoryId = categoryId;
        this.quantity = quantity;
        
    }
    

    // Existing constructor remains compatible with DataStore.

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
    
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public int getQuantity() {
        return quantity;
    }


    public String getCategoryName() {
        for (Category c : DataStore.categories) {
            if (c.getCategoryId() == categoryId) {
                return c.getCategoryName();
            }
        }
        return "Unknown";
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

