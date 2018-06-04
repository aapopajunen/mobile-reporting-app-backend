package com.swp.Entity;

public class FieldAnswer {
    private int fieldID;
    private int reportID;
    private String answer;

    public FieldAnswer(int fieldID, int reportID, String answer) {
        this.fieldID = fieldID;
        this.reportID = reportID;
        this.answer = answer;
    }

    public FieldAnswer() {
    }

    public int getFieldID() {
        return fieldID;
    }

    public int getReportID() {
        return reportID;
    }

    public String getAnswer() {
        return answer;
    }
}
