package com.example.appcontact.model;

import android.graphics.Bitmap;

public class Unit {
    private int id;
    private String code;
    private String name;
    private String email;
    private String website;
    private Bitmap logo;
    private String address;
    private String phone;
    private int parentId;

    public Unit() {
    }

    public Unit(int id, String code, String name, String email, String website, Bitmap logo, String address, String phone, int parentId) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.email = email;
        this.website = website;
        this.logo = logo;
        this.address = address;
        this.phone = phone;
        this.parentId = parentId;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getWebsite() { return website; }
    public void setWebsite(String website) { this.website = website; }

    public Bitmap getLogo() { return logo; }
    public void setLogo(Bitmap logo) { this.logo = logo; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public int getParentId() { return parentId; }
    public void setParentId(int parentId) { this.parentId = parentId; }
}