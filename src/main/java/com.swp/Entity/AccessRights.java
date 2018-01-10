package com.swp.Entity;

public class AccessRights {
    private int userID;
    private int templateID;

    public AccessRights(int userID, int templateID) {
        this.userID = userID;
        this.templateID = templateID;
    }

    public int getUserID() {
        return userID;
    }

    public int getTemplateID() {
        return templateID;
    }
}
