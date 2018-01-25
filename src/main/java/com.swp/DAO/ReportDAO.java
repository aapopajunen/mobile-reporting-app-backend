package com.swp.DAO;

import com.swp.Entity.*;

import java.util.Collection;
import java.util.Map;

public interface ReportDAO {

    // /reports
    Collection<Report> getReports(Map<String, String> params);

    // /reports/{reportId}
    Report getReportsById(int reportId);

    // /reports/{reportId}
    void deleteReportById(int reportId);

    // /reports/{reportId}
    void acceptReportById(int reportId);

    // /reports/{reportId}/fields
    Collection<Field> getFieldsByReportId(int reportId);

    // /reports/{reportId}/answers
    Collection<FieldAnswer> getAnswersByReportId(int reportId);

    // /templates
    Collection<Template> getTemplates(Map<String, String> params);

    // /templates/{templateId}
    Template getTemplateById(int templateId);

    // /templates/{templateId}/fields
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

    // /users/{id}/reports
    Collection<Report> getReportsByUser(int id, Map<String,String> params);

    // /users/{id}/reports
    void createReport(int id, Report report);

    // /users/{id}/reports
    Collection<Template> getTemplatesByUser(int id, Map<String, String> params);


}
