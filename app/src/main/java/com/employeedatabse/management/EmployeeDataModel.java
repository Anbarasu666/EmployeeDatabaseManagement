package com.employeedatabse.management;

public class EmployeeDataModel {
    private int id;
    private String name;
    private String address;
    private String email;
    private String phno;

    public EmployeeDataModel(int id, String name, String address, String email, String phno) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.email = email;
        this.phno = phno;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getPhno() {
        return phno;
    }
}

