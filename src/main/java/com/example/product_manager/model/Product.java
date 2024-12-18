package com.example.product_manager.model;

public class Product {
    private int id;
    private String name;
    private double price;
    private String description;
    private String imgLink;

    public Product(int id , String name, double price, String description, String imgLink) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.imgLink = imgLink;
    }

    public Product() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }
}
