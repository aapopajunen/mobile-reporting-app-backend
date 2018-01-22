package com.swp.DAO;

import com.swp.Entity.*;

import java.util.Collection;
import java.util.Map;

public interface ReportDAO {

    // /forms
    Collection<Report> getReports(Map<String, String> params);

    // /forms/{reportId}
    Report getReportsById(int reportId);

    // /forms/{reportId}
    void deleteReportById(int reportId);

    // /forms/{reportId}
    void acceptReportById(int reportId);

    // /forms/{reportId}/fields
    Collection<Field> getFieldsByReportId(int reportId);

    // /forms/{reportId}/answers
    Collection<FieldAnswer> getAnswersByReportId(int reportId);

    // /layouts
    Collection<Template> getTemplates(Map<String, String> params);

    // /layouts/{templateId}
    Template getTemplateById(int templateId);

    // /layouts/{layoutid}/fields
    Collection<Field> getFieldsByTemplateId(int templateId);

    // /users
    Collection<User> getAllUsers();

    // /users/{username}
    void createUser(String username);

    // /users/{id}
    User getUserById(int id);

    // /users/{id}
    void deleteUserById(int id);

    // /users/{username}
    void deleteUserByUsername(String username);

    // /users/{id}/rights
    Collection<AccessRights> getUserAccessRights(int id);

    // /users/{id}/rights
    void grantUserAccessRights(int id, Map<String,String> params);

    // /users/{id}/rights
    void deleteUserAccessRights(int id, Map<String, String> params);

    // /users/{id}/forms
    Collection<Report> getReportsByUser(int id, Map<String,String> params);

    // /users/{id}/forms
    void createReport(int id, Report report);

    // /users/{id}/forms
    Collection<Template> getTemplatesByUser(int id, Map<String, String> params);


}
