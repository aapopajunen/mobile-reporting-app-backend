package com.swp.Entity;

public class Field {
    private int ID;
    private int templateID;
    private String title;
    private String defaultValue;
    private int typeID;
    private int orderNumber;
    private int required;

    public Field(int ID, int templateID, String title, String defaultValue, int typeID, int orderNumber, int required) {
        this.ID = ID;
        this.templateID = templateID;
        this.title = title;
        this.defaultValue = defaultValue;
        this.typeID = typeID;
        this.orderNumber = orderNumber;
        this.required = required;
    }

    public int getID() {
        return ID;
    }

    public int getTemplateID() {
        return templateID;
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
