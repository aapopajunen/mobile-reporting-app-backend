package com.swp.Entity;

public class OptionAnswer {

    private Integer option_answer_id;
    private Integer form_id;
    private Integer field_option_id;
    private boolean selected = false; //USED FOR USER INPUT

    public OptionAnswer(Integer option_answer_id, Integer form_id, Integer field_option_id) {
        this.option_answer_id = option_answer_id;
        this.form_id = form_id;
        this.field_option_id = field_option_id;
    }

    public OptionAnswer() {
    }

    public Integer getOption_answer_id() {
        return option_answer_id;
    }

    public void setOption_answer_id(Integer option_answer_id) {
        this.option_answer_id = option_answer_id;
    }

    public Integer getForm_id() {
        return form_id;
    }

    public void setForm_id(Integer form_id) {
        this.form_id = form_id;
    }

    public Integer getField_option_id() {
        return field_option_id;
    }

    public void setField_option_id(Integer field_option_id) {
        this.field_option_id = field_option_id;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
