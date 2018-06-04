package com.swp.Entity;

import java.util.Collection;

public class Field {

    private int field_id;
    private int template_id;
    private int order_number;
    private String title;
    private boolean required;
    private FieldType type;
    private String default_value;
    private Collection<FieldOption> field_options;

    public Field(int field_id, int template_id, int order_number, String title, boolean required, FieldType type, String default_value) {
        this.field_id = field_id;
        this.template_id = template_id;
        this.order_number = order_number;
        this.title = title;
        this.required = required;
        this.type = type;
        this.default_value = default_value;
    }

    public Field() {

    }

    public int getField_id() {
        return field_id;
    }

    public void setField_id(int field_id) {
        this.field_id = field_id;
    }

    public int getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(int template_id) {
        this.template_id = template_id;
    }

    public int getOrder_number() {
        return order_number;
    }

    public void setOrder_number(int order_number) {
        this.order_number = order_number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public FieldType getType() {
        return type;
    }

    public void setType(FieldType type) {
        this.type = type;
    }

    public String getDefault_value() {
        return default_value;
    }

    public void setDefault_value(String default_value) {
        this.default_value = default_value;
    }

    public Collection<FieldOption> getField_options() {
        return field_options;
    }

    public void setField_options(Collection<FieldOption> field_options) {
        this.field_options = field_options;
    }
}
