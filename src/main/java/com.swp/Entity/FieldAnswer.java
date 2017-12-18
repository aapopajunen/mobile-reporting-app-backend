package com.swp.Entity;

public class FieldAnswer {
    public int fieldID;
    public int formID;
    public String answer;

    public FieldAnswer(int fieldID, int formID, String answer) {
        this.fieldID = fieldID;
        this.formID = formID;
        this.answer = answer;
    }

    public int getFieldID() {
        return fieldID;
    }

    public void setFieldID(int fieldID) {
        this.fieldID = fieldID;
    }

    public int getFormID() {
        return formID;
    }

    public void setFormID(int formID) {
        this.formID = formID;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
