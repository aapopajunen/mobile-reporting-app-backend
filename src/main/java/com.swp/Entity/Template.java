package com.swp.Entity;

public class Template {
    private int ID;
    private String title;
    private int reportCount;
    private int amountOfReports;

    public Template(int ID, String title, int reportCount, int amountOfReports) {
        this.ID = ID;
        this.title = title;
        this.reportCount = reportCount;
        this.amountOfReports = amountOfReports;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getReportCount() {
        return reportCount;
    }

    public void setReportCount(int reportCount) {
        this.reportCount = reportCount;
    }

    public int getAmountOfReports() {
        return amountOfReports;
    }

    public void setAmountOfReports(int amountOfReports) {
        this.amountOfReports = amountOfReports;
    }
}

