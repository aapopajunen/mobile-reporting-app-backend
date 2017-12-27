package com.swp.DAO;

import com.swp.Entity.*;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public interface FormDAO {

    // /forms
    Collection<Form> getAllForms(Map<String, String> params);

    // /forms/{formId}
    Form getFormById(int formId);

    // /forms/{formId}
    void deleteFormById(int formId);

    // /forms/{formId}/fields
    Collection<Field> getFieldsByFormId(int formId);

    // /forms/{formId}/answers
    Collection<FieldAnswer> getAnswersByFormId(int formId);

    // /layouts
    Collection<Layout> getAllLayouts();

    // /layouts/{layoutid}
    Layout getLayoutById(int layoutid);

    // /layouts/{layoutid}/fields
    Collection<Field> getFieldsByLayoutId(int layoutId);

    // /users
    Collection<User> getAllUsers();

    // /users/{username}
    void createUser(String username);

    // /users/{id}
    User getUserById(int id);

    // /users/{id}
    void deleteUser(int id);

    // /users/{id}/rights
    Collection<AccessRights> getUserAccessRights(int id);

    // /users/{id}/rights
    void grantUserAccessRights(int id, Map<String,String> params);

    // /users/{id}/rights
    void deleteUserAccessRights(int id, Map<String, String> params);

    // /users/{id}/forms
    Collection<Form> getFormsByUser(int id, Map<String,String> params);

    // /users/{id}/forms
    void createForm(Form form);

    // /users/{id}/forms
    Collection<Layout> getLayoutsByUser(int id, Map<String, String> params);


}
