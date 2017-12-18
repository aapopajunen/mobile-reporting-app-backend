package com.swp.Entity;

import java.sql.Date;

/**
 * Example JSON
 *
 * {
 *     ID: 1
 *     layoutID: 1
 *     userID: 1
 *     orderNo: 1
 *     title: "Form"
 *     dateCreated: "2017-1-1"
 *     dateAccepted: null
 *     answers: /forms/1/answers
 * }
 */
public class Form {
    public int ID;
    public int layoutID;
    public int userID;
    public int orderNo;
    public String title;
    public java.sql.Date dateCreated;
    public java.sql.Date dateAccepted;
    public String answers;

    public Form(int ID, int layoutID, int userID, int orderNo, String title, Date dateCreated, Date dateAccepted, String answers) {
        this.ID = ID;
        this.layoutID = layoutID;
        this.userID = userID;
        this.orderNo = orderNo;
        this.title = title;
        this.dateCreated = dateCreated;
        this.dateAccepted = dateAccepted;
        this.answers = answers;
    }

    public Form() {

    }

}
