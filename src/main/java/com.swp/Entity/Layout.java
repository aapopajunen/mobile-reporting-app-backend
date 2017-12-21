package com.swp.Entity;

public class Layout {
    private int id;
    private String title;
    private String forms;
    private String fields;

    public Layout(int id, String title, String forms, String fields) {
        this.id = id;
        this.title = title;
        this.forms = forms;
        this.fields = fields;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getForms() {
        return forms;
    }

    public String getFields() {
        return fields;
    }
}

