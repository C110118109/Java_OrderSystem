package com.uni.plovdiv.hapnitopni.entities;

public class Locations {
    private int id;
    private int photo;
    private String name;
    private String address;
    private String tab;

    public Locations(int photo, String name, String address, String tab) {
        this.photo = photo;
        this.name = name;
        this.address = address;
        this.tab = tab;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTab() {
        return tab;
    }

    public void setTab(String tab) {
        this.tab = tab;
    }
}
