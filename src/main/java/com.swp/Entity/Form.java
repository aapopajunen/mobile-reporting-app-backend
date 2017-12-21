package com.swp.Entity;

import java.sql.Date;

/**
 * Example JSON
 *
 * {
 *     id: 1
 *     layoutId: 1
 *     userId: 1
 *     orderNo: 1
 *     title: "Form"
 *     dateCreated: "2017-1-1"
 *     dateAccepted: null
 *     answers: /forms/1/answers
 * }
 */
public class Form {
    private int id;
    private int layoutId;
    private int userId;
    private int orderNo;
    private String title;
    private java.sql.Date dateCreated;
    private java.sql.Date dateAccepted;
    private String answers;

    public Form(int id, int layoutId, int userId, int orderNo, String title, Date dateCreated, Date dateAccepted, String answers) {
        this.id = id;
        this.layoutId = layoutId;
        this.userId = userId;
        this.orderNo = orderNo;
        this.title = title;
        this.dateCreated = dateCreated;
        this.dateAccepted = dateAccepted;
        this.answers = answers;
    }

    public int getId() {
        return id;
    }

    public int getLayoutId() {
        return layoutId;
    }

    public int getUserId() {
        return userId;
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

    public String getAnswers() {
        return answers;
    }
}
