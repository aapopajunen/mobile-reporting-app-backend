package com.swp.Entity;

import java.sql.Date;
import java.util.Collection;

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
    private int ID;
    private int layoutID;
    private int userID;
    private int orderNo;
    private String title;
    private java.sql.Date dateCreated;
    private java.sql.Date dateAccepted;
    private Collection<FieldAnswer> answers;

    public Form(int ID, int layoutID, int userID, int orderNo, String title, Date dateCreated, Date dateAccepted, Collection<FieldAnswer> answers) {
        this.ID = ID;
        this.layoutID = layoutID;
        this.userID = userID;
        this.orderNo = orderNo;
        this.title = title;
        this.dateCreated = dateCreated;
        this.dateAccepted = dateAccepted;
        this.answers = answers;
    }

    public Form(int ID, int layoutID, int userID, int orderNo, String title, Date dateCreated, Date dateAccepted) {
        this.ID = ID;
        this.layoutID = layoutID;
        this.userID = userID;
        this.orderNo = orderNo;
        this.title = title;
        this.dateCreated = dateCreated;
        this.dateAccepted = dateAccepted;
    }

    public Form() {

    }

    public int getID() {
        return ID;
    }

    public int getLayoutID() {
        return layoutID;
    }

    public int getUserID() {
        return userID;
    }

    public int getOrderNo() {
        return orderNo;
    }

    public String getTitle() {
        return title;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public Date getDateAccepted() {
        return dateAccepted;
    }

    public Collection<FieldAnswer> getAnswers() {
        return answers;
    }
}
