package com.swp.Entity;

public class Field {
    private int ID;
    private int layoutID;
    private String title;
    private String defaultValue;
    private int typeID;
    private int orderNumber;
    private int required;

    public Field(int ID, int layoutID, String title, String defaultValue, int typeID, int orderNumber, int required) {
        this.ID = ID;
        this.layoutID = layoutID;
        this.title = title;
        this.defaultValue = defaultValue;
        this.typeID = typeID;
        this.orderNumber = orderNumber;
        this.required = required;
    }

    public int getID() {
        return ID;
    }

    public int getLayoutID() {
        return layoutID;
    }

    public String getTitle() {
        return title;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public int getTypeID() {
        return typeID;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public int getRequired() {
        return required;
    }
}
