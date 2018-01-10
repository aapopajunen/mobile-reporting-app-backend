package com.swp.DAO;

import com.swp.Entity.*;
import org.springframework.http.ResponseEntity;

import java.util.Collection;
import java.util.Map;

public interface FormDAO {

    // /forms
    Collection<Form> getReports(Map<String, String> params);

    // /forms/{formId}
    Form getReportsById(int formId);

    // /forms/{formId}
    void deleteReportById(int formId);

    // /forms/{formId}
    void acceptReportById(int formId);

    // /forms/{formId}/fields
    Collection<Field> getFieldsByReportId(int formId);

    // /forms/{formId}/answers
    Collection<FieldAnswer> getAnswersByReportId(int formId);

    // /layouts
    Collection<Layout> getTemplates(Map<String, String> params);

    // /layouts/{layoutid}
    Layout getTemplateById(int layoutid);

    // /layouts/{layoutid}/fields
    Collection<Field> getFieldsByTemplateId(int layoutId);

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
    Collection<Form> getReportsByUser(int id, Map<String,String> params);

    // /users/{id}/forms
    void createReport(int id, Form form);

    // /users/{id}/forms
    Collection<Layout> getTemplatesByUser(int id, Map<String, String> params);


}
