package com.swp.Entity;

import java.util.Collection;

public class Template {

    private int template_id;
    private String title;
    private Collection<Field> fields;

    public Template(int template_id, String title) {
        this.template_id = template_id;
        this.title = title;
    }

    public Template() {
    }

    public int getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(int template_id) {
        this.template_id = template_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Collection<Field> getFields() {
        return fields;
    }

    public void setFields(Collection<Field> fields) {
        this.fields = fields;
    }
}
