package com.swp.DAO;

import com.swp.Entity.*;

import java.util.Collection;
import java.util.Map;

public interface ReportDAO {

    User checkLoginCredentials(LoginCredentials loginCredentials);

    // /users
    Collection<User> getAllUsers();

    // /users/{username}/reports
    Collection<Report> getReportsByUser(String username, Map<String, String> params);

    // /users/{username}/reports
    void createReport(String username, Report report);

    // /users/{username}/reports/{reportId}
    Report getUserReportById(String username, int reportId, Map<String, String> params);

    // /users/{username}/templates
    Collection<Template> getTemplatesByUser(String username, Map<String, String> params);

    // users/{username}/templates/{templateId}
    Template getUserTemplateById(String username, int templateId);

    // users/{username}/templates/{templateId}/empty
    Object getEmptyTemplate(String username, int templateId);

    // users/{username}/templates/{templateId}/reports
    Collection<Report> getUserReportsByTemplateId(String username, int templateId, Map<String, String> params);


}
