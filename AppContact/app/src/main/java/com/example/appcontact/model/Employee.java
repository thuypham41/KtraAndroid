package com.example.appcontact.model;

import android.graphics.Bitmap;

public class Employee {
    private int id;
    private String code;
    private String name;
    private String position;
    private String email;
    private String phone;
    private Bitmap avatar;
    private int unitId;

    public Employee() {
    }

    public Employee(int id, String code, String name, String position, String email, String phone, Bitmap avatar, int unitId) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.position = position;
        this.email = email;
        this.phone = phone;
        this.avatar = avatar;
        this.unitId = unitId;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public Bitmap getAvatar() { return avatar; }
    public void setAvatar(Bitmap avatar) { this.avatar = avatar; }

    public int getUnitId() { return unitId; }
    public void setUnitId(int unitId) { this.unitId = unitId; }
}
