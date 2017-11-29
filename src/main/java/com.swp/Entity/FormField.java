package com.swp.Entity;

import java.lang.reflect.Type;

/**
 * A FormField represent a single field contained in a FormTemplate.
 */
public class FormField {

    private int id;
    private FormFieldType type;
    private String title;
    private boolean required;
    private int orderNumber;

    public FormField(int id, FormFieldType type, String title, boolean required, int orderNumber) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.required = required;
        this.orderNumber = orderNumber;
    }
}

