package com.swp.Entity;

public class OptionAnswer {

    private Integer option_answer_id;
    private Integer report_id;
    private Integer field_option_id;
    private boolean selected = false; //USED FOR USER INPUT

    public OptionAnswer(Integer option_answer_id, Integer report_id, Integer field_option_id) {
        this.option_answer_id = option_answer_id;
        this.report_id = report_id;
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

    public Integer getReport_id() {
        return report_id;
    }

    public void setReport_id(Integer report_id) {
        this.report_id = report_id;
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
