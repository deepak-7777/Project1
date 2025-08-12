package com.example.project1.Model;

public class CategoryModel {
    private String imageUrl;
    private String title;
    private String description;
    private String price;
    private String oldPrice;

    public CategoryModel(String imageUrl, String title, String description, String price, String oldPrice) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.description = description;
        this.price = price;
        this.oldPrice = oldPrice;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public String getOldPrice() {
        return oldPrice;
    }
}
