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

    // /users
    void createUser(String username);

    // /users/{username}
    User getUserByUsername(String username);

    // /users/{username}
    void deleteUser(String username);

    // /users/{username}/rights
    Collection<AccessRights> getUserAccessRights(String username);

    // /users/{username}/rights
    void grantUserAccessRights(String username, Map<String,String> params);

    // /users/{username}/rights
    void deleteUserAccessRights(String username, Map<String, String> params);

    // /users/{username}/forms
    Collection<Form> getFormsByUser(String username, Map<String,String> params);

    // /users/{username}/forms
    void createForm(Form form);

    // /users/{username}/forms
    Collection<Layout> getLayoutsByUser(String username, Map<String, String> params);


}
