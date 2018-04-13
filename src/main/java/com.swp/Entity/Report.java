package com.swp.Entity;

import java.sql.Date;
import java.util.Collection;

public class Report {

    private Integer report_id;
    private Integer user_id;
    private Integer template_id;
    private String title;
    private Date date_created;
    private Date date_accepted;
    private Collection<StringAnswer> string_answers;
    private Collection<OptionAnswer> option_answers;

    public Report(Integer report_id, Integer user_id, Integer template_id, String title, Date date_created, Date date_accepted) {
        this.report_id = report_id;
        this.user_id = user_id;
        this.template_id = template_id;
        this.title = title;
        this.date_created = date_created;
        this.date_accepted = date_accepted;
    }

    public Report() {
    }

    public Integer getReport_id() {
        return report_id;
    }

    public void setReport_id(Integer report_id) {
        this.report_id = report_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(Integer template_id) {
        this.template_id = template_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate_created() {
        return date_created;
    }

    public void setDate_created(Date date_created) {
        this.date_created = date_created;
    }

    public Date getDate_accepted() {
        return date_accepted;
    }

    public void setDate_accepted(Date date_accepted) {
        this.date_accepted = date_accepted;
    }

    public Collection<StringAnswer> getString_answers() {
        return string_answers;
    }

    public void setString_answers(Collection<StringAnswer> string_answers) {
        this.string_answers = string_answers;
    }

    public Collection<OptionAnswer> getOption_answers() {
        return option_answers;
    }

    public void setOption_answers(Collection<OptionAnswer> option_answers) {
        this.option_answers = option_answers;
    }
}
