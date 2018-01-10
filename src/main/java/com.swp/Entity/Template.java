package com.swp.Entity;

public class Template {
    private int ID;
    private String title;
    private int reportCount;

    public Template(int ID, String title, int reportCount) {
        this.ID = ID;
        this.title = title;
        this.reportCount = reportCount;
    }

    public Template() {

    }

    public int getID() {
        return ID;
    }

    public String getTitle() {
        return title;
    }

    public int getReportCount() {
        return reportCount;
    }
}

