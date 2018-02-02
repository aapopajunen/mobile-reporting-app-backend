package com.swp.DAO;

import com.swp.Entity.*;

import java.util.Collection;
import java.util.Map;

public interface ReportDAO {

    User checkLoginCredentials(LoginCredentials loginCredentials);

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

    // /users/{username}/reports/{reportId}
    Report getUserReportById(String username, int reportId, Map<String, String> params);

    // /users/{username}/reports/{reportId}/fields
    Collection<Field> getUserReportFieldsById(String username, int reportId);

    // /users/{username}/reports/{reportId}/answers
    Collection<FieldAnswer> getUserAnswersByReportId(String username, int reportId);

    // /users/{username}/templates
    Collection<Template> getTemplatesByUser(String username, Map<String, String> params);

    // users/{username}/templates/{templateId}
    Template getUserTemplateById(String username, int templateId);

    // /users/{username}/templates/{templateId}/fields
    Collection<Field> getUserTemplateFieldsById(String username, int templateId);

    // users/{username}/templates/{templateId}/reports
    Collection<Report> getUserReportsByTemplateId(String username, int templateId, Map<String, String> params);


}
