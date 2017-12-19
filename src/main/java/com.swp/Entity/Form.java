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
    public int id;
    public int layoutId;
    public int userId;
    public int orderNo;
    public String title;
    public java.sql.Date dateCreated;
    public java.sql.Date dateAccepted;
    public String answers;

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

    public Form() {

    }

}
