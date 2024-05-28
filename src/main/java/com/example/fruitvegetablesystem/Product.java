package com.example.fruitvegetablesystem;

import javafx.scene.image.Image;

import java.io.Serializable;

public class Product implements Serializable {
    private static final long serialVersionUID = 1L;
    private String productId;
    private String productName;
    private String type;
    private int stock;
    private double price;
    private String status;
    private String date;
    private Image image;
    private int quantity; // New quantity variable

    public Product(String productId, String productName, String type, int stock, double price, String status, String date) {
        this.productId = productId;
        this.productName = productName;
        this.type = type;
        this.stock = stock;
        this.price = price;
        this.status = status;
        this.date = date;
    }
    public Product(String productId, String productName, String type, int stock, double price, String status, String date, Image image) {
        this.productId = productId;
        this.productName = productName;
        this.type = type;
        this.stock = stock;
        this.price = price;
        this.status = status;
        this.date = date;
        this.image = image;
    }

    // Getters and setters for quantity
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public void setImage(Image image) {
        this.image = image;
    }

    // New method to get the image
    public Image getImage() {
        return image;
    }

}
