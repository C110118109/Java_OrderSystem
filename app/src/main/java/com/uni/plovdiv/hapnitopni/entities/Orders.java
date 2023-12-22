package com.uni.plovdiv.hapnitopni.entities;

public class Orders {

    private int id;
    private int image ;
    private String name;
    private int quantity ;
    private String description;
    private int price ;

    public Orders( int image, String name,String description,int price, int quantity) {
        this.image = image;
        this.name = name;
        this.quantity = quantity;
        this.description = description;
        this.price = price;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

}
