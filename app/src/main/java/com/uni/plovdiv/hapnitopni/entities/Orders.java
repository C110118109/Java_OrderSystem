package com.uni.plovdiv.hapnitopni.entities;

public class Orders {

    private int id;
    private int user_id ;
    private int product_name;
    private int quantity ;
    private int number ;

    public Orders(int id, int user_id, int product_id, int quantity,int number) {
        this.id = id;
        this.user_id = user_id;
        this.product_name = product_name;
        this.quantity = quantity;
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getProduct_name() {
        return product_name;
    }

    public void setProduct_name(int product_name) {
        this.product_name = product_name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }


}
