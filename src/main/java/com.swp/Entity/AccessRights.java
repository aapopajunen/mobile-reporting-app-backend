package com.swp.Entity;

public class AccessRights {
    private int userId;
    private int layoutId;

    public AccessRights(int userId, int layoutId) {
        this.userId = userId;
        this.layoutId = layoutId;
    }

    public int getUserId() {
        return userId;
    }

    public int getLayoutId() {
        return layoutId;
    }
}
