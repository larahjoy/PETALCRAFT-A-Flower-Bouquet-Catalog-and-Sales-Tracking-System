package com.mycompany.flowerbouquet.model;

public class User {

    private static int nextId = 1;

    private int userId;
    private String fullName;
    private String username;
    private String password;
    private String role;

    // Use this constructor for registered staff accounts.
    public User(String fullName, String username, String password) {
        this(fullName, username, password, "Staff");
    }

    public User(String fullName, String username,
                String password, String role) {

        this.userId = nextId++;
        this.fullName = fullName;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public int getUserId() {
        return userId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}