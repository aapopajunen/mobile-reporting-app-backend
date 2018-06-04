package com.swp.Entity;

public class FieldOption {

    private int field_option_id;
    private int field_id;
    private String value;
    private boolean default_value;

    public FieldOption(int field_option_id, int field_id, String value, boolean default_value) {
        this.field_option_id = field_option_id;
        this.field_id = field_id;
        this.value = value;
        this.default_value = default_value;
    }

    public FieldOption() {

    }

    public int getField_option_id() {
        return field_option_id;
    }

    public void setField_option_id(int field_option_id) {
        this.field_option_id = field_option_id;
    }

    public int getField_id() {
        return field_id;
    }

    public void setField_id(int field_id) {
        this.field_id = field_id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean getDefault_value() {
        return default_value;
    }

    public void setDefault_value(boolean default_value) {
        this.default_value = default_value;
    }
}
