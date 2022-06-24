package com.zarkaoui.digitalsou9.classes;

import android.net.Uri;

import com.zarkaoui.digitalsou9.enums.ProductCategory;

import java.util.Date;

public class Product {
    //    private Long id;
    private String productName;
    private String description;
    private String createdAt;
    private String location;
    private String price;
    private String category;
    private String quantity;
    private String imageUrl;
    private String userId;


    public Product() {
    }

    public Product(String productName, String description, String createdAt, String location, String price, String category, String quantity, String imageUrl, String userId) {
        this.productName = productName;
        this.description = description;
        this.createdAt = createdAt;
        this.location = location;
        this.price = price;
        this.category = category;
        this.quantity = quantity;
        this.imageUrl = imageUrl;
        this.userId = userId;

    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {

        this.quantity = quantity;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
