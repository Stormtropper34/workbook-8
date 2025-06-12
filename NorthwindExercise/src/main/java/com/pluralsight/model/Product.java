package com.pluralsight.model;

public class Product {
    private int productId;
    private String productName;
    private double price;
    private int unitStock;

    public Product(int productId, String productName, double price, int unitStock){
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.unitStock = unitStock;
    }
    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public double getPrice() {
        return price;
    }

    public int getUnitStock() {
        return unitStock;
    }
}