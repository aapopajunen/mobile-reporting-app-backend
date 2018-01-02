package com.swp.Entity;

public class FieldAnswer {
    private int fieldID;
    private int formID;
    private String answer;

    public FieldAnswer(int fieldID, int formID, String answer) {
        this.fieldID = fieldID;
        this.formID = formID;
        this.answer = answer;
    }

    public FieldAnswer() {
    }

    public int getFieldID() {
        return fieldID;
    }

    public int getFormID() {
        return formID;
    }

    public String getAnswer() {
        return answer;
    }
}
