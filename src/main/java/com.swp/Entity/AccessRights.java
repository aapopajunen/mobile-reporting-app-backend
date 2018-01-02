package com.swp.Entity;

public class AccessRights {
    private int userID;
    private int layoutID;

    public AccessRights(int userID, int layoutID) {
        this.userID = userID;
        this.layoutID = layoutID;
    }

    public int getUserID() {
        return userID;
    }

    public int getLayoutID() {
        return layoutID;
    }
}
