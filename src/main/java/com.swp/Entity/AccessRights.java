package com.swp.Entity;

public class AccessRights {
    private int userID;
    private String username;
    private int templateID;

    public AccessRights(int userID, String username, int templateID) {
        this.userID = userID;
        this.username = username;
        this.templateID = templateID;
    }

    public int getUserID() { return userID; }

    public String getUsername() { return username; }

    public int getTemplateID() {
        return templateID;
    }
}
