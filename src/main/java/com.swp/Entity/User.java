package com.swp.Entity;

public class User {
    private int ID;
    private String username;

    public User() {
    }

    public User(int ID, String username) {
        this.ID = ID;
        this.username = username;
    }

    public int getID() {
        return ID;
    }

    public String getUsername() {
        return username;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
