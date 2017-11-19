package com.swp.Entity;

public class FormTemplate {

    private int id;
    private String name;

    public FormTemplate(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public FormTemplate() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}