package com.swp.Entity;

public class StringAnswer {

    private Integer string_answer_id;
    private Integer form_id;
    private Integer field_id;
    private String value;

    public StringAnswer(Integer string_answer_id, Integer form_id, Integer field_id, String value) {
        this.string_answer_id = string_answer_id;
        this.form_id = form_id;
        this.field_id = field_id;
        this.value = value;
    }

    public StringAnswer() {
    }

    public Integer getString_answer_id() {
        return string_answer_id;
    }

    public void setString_answer_id(Integer string_answer_id) {
        this.string_answer_id = string_answer_id;
    }

    public Integer getForm_id() {
        return form_id;
    }

    public void setForm_id(Integer form_id) {
        this.form_id = form_id;
    }

    public Integer getField_id() {
        return field_id;
    }

    public void setField_id(Integer field_id) {
        this.field_id = field_id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
