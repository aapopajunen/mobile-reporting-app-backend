package com.swp.Entity;

public class Layout {
    private int ID;
    private String title;
    private String forms;
    private String fields;

    public Layout(int ID, String title, String forms, String fields) {
        this.ID = ID;
        this.title = title;
        this.forms = forms;
        this.fields = fields;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getForms() {
        return forms;
    }

    public void setForms(String forms) {
        this.forms = forms;
    }

    public String getFields() {
        return fields;
    }

    public void setFields(String fields) {
        this.fields = fields;
    }
}

