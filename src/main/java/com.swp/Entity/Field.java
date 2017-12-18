package com.swp.Entity;

public class Field {
    public int ID;
    public int layoutID;
    public String title;
    public String defaultValue;
    public int typeID;
    public int orderNumber;
    public int required;

    public Field(int ID, int layoutID, String title, String defaultValue, int typeID, int orderNumber, int required) {
        this.ID = ID;
        this.layoutID = layoutID;
        this.title = title;
        this.defaultValue = defaultValue;
        this.typeID = typeID;
        this.orderNumber = orderNumber;
        this.required = required;
    }
}
