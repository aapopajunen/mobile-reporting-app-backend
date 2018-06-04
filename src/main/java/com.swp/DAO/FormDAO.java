package com.swp.DAO;

import com.swp.Entity.*;

import java.util.Collection;
import java.util.Map;

public interface FormDAO {

    User checkLoginCredentials(LoginCredentials loginCredentials);

    // /users
    Collection<User> getAllUsers();

    // /users/{username}/forms
    Collection<Form> getFormsByUser(String username, Map<String, String> params);

    // /users/{username}/forms
    void createForm(String username, Form form);

    // /users/{username}/forms/{formId}
    Form getUserFormById(String username, int formId, Map<String, String> params);

    // /users/{username}/templates
    Collection<Template> getTemplatesByUser(String username, Map<String, String> params);

    // users/{username}/templates/{templateId}
    Template getUserTemplateById(String username, int templateId);

    // users/{username}/templates/{templateId}/empty
    Object getEmptyTemplate(String username, int templateId);

    // users/{username}/templates/{templateId}/forms
    Collection<Form> getUserFormsByTemplateId(String username, int templateId, Map<String, String> params);


}
