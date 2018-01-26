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

    // /users/{username}/rights
    Collection<AccessRights> getUserAccessRights(String username);

    // /users/{username}/rights
    void grantUserAccessRights(String username, Map<String,String> params);

    // /users/{username}/rights
    void deleteUserAccessRights(String username, Map<String, String> params);

    // /users/{username}/reports
    Collection<Report> getReportsByUser(String username, Map<String,String> params);

    // /users/{username}/reports
    void createReport(String username, Report report);

    // /users/{username}/reports
    Report getUsersReportById(String username, int reportId);

    // /users/{username}/templates
    Collection<Template> getTemplatesByUser(String username, Map<String, String> params);

    // users/{username}/templates/{templateId}
    Template getUsersTemplateById(String username, int templateId);


}
