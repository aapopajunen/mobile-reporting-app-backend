package com.swp.Entity;

public class Layout {
    private int ID;
    private String title;
    private int formCount;

    public Layout(int ID, String title, int formCount) {
        this.ID = ID;
        this.title = title;
        this.formCount = formCount;
    }

    public Layout() {

    }

    public int getID() {
        return ID;
    }

    public String getTitle() {
        return title;
    }

    public int getFormCount() {
        return formCount;
    }
}

