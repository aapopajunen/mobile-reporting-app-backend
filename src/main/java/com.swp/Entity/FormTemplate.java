package com.swp.Entity;

import java.util.List;
import java.util.SortedSet;

public class FormTemplate {

    private int id;
    private String name;
    private SortedSet<FormField> formFields;

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

    /**
     * Add a FormField to this FormTemplate.
     *
     * @param formField
     * @return true if FormField got successfully added,
     * false if formField was already in the formFields list.
     */
    public boolean addFormField(FormField formField) {
        if(formFields.contains(formField)){
            return false;
        } else {
            formFields.add(formField);
            return true;
        }
    }

    /**
     * Remove a FormField from this template
     *
     * @param formField form field to be removed.
     * @return true if remove was successful, false otherwise.
     */
    public boolean removeFormField(FormField formField) {
        if(formFields.contains(formField)){
            formFields.remove(formField);
            return true;
        } else {
            return false;
        }
    }
}