package com.swp.Entity;

public class FieldType {
    private int ID;
    private String name;

    public FieldType(int ID, String name) {
        this.ID = ID;
        this.name = name;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }
}
