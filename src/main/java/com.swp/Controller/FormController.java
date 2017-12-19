package com.swp.Controller;

import com.swp.DAO.FormDAO;
import com.swp.Entity.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class FormController {

    @Autowired
    @Qualifier("mysql")
    private FormDAO formDAO;

    @RequestMapping(value = "/forms", method = RequestMethod.GET)
    public Collection<Form> getAllForms(@RequestParam Map<String, String> params) {
        return formDAO.getAllForms(params);
    }

    @RequestMapping(value = "/forms/{id}", method = RequestMethod.GET)
    public Form getFormById(@PathVariable("id") int formId) {
        return formDAO.getFormById(formId);
    }

    @RequestMapping(value = "/forms/{id}", method = RequestMethod.DELETE)
    public void deleteFormById(@PathVariable("id") int formId) {
        formDAO.deleteFormById(formId);
    }

    @RequestMapping(value = "forms/{id}/fields", method = RequestMethod.GET)
    public Collection<Field> getFieldsByFormId(@PathVariable("id") int formId) {
        return formDAO.getFieldsByFormId(formId);
    }

    @RequestMapping(value = "forms/{id}/answers", method = RequestMethod.GET)
    public Collection<FieldAnswer> getAnswersByFormId(@PathVariable("id") int formId) {
        return formDAO.getAnswersByFormId(formId);
    }

    @RequestMapping(value = "/layouts", method = RequestMethod.GET)
    public Collection<Layout> getAllLayouts() {
       return formDAO.getAllLayouts();
    }

    @RequestMapping(value = "layouts/{id}", method = RequestMethod.GET)
    public Layout getLayoutById(@PathVariable("id") int formId) {
        return formDAO.getLayoutById(formId);
    }

    @RequestMapping(value = "layouts/{id}/fields", method = RequestMethod.GET)
    public Collection<Field> getFieldsByLayoutId(@PathVariable("id") int layoutId) {
        return formDAO.getFieldsByLayoutId(layoutId);
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public Collection<User> getAllUsers() {
        return formDAO.getAllUsers();
    }

    @RequestMapping(value = "/users/{username}", method = RequestMethod.POST)
    public void createUser(@PathVariable("username") String username) {
        formDAO.createUser(username);
    }

    @RequestMapping(value = "/users/{username}", method = RequestMethod.GET)
    public User getUserByUsername(@PathVariable String username) {
        return formDAO.getUserByUsername(username);
    }

    @RequestMapping(value = "/users/{username}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable String username) {
        formDAO.deleteUser(username);
    }

    @RequestMapping(value = "/users/{username}/rights", method = RequestMethod.GET)
    public Collection<AccessRights> getUserAccessRights(@PathVariable String username) {
        return formDAO.getUserAccessRights(username);
    }

    @RequestMapping(value = "/users/{username}/rights", method = RequestMethod.POST)
    public void grantUserAccessRights(@PathVariable String username,
                                      @RequestParam Map<String, String> params) {
        formDAO.grantUserAccessRights(username, params);
    }


    @RequestMapping(value = "/users/{username}/rights", method = RequestMethod.DELETE)
    public void deleteUserAccessRights(@PathVariable String username,
                                       @RequestParam Map<String, String> params) {
        formDAO.deleteUserAccessRights(username, params);
    }

    @RequestMapping(value = "/users/{username}/forms", method = RequestMethod.GET)
    public Collection<Form> getFormsByUser(
            @PathVariable("username") String username,
            @RequestParam Map<String, String> params) {
        return formDAO.getFormsByUser(username, params);
    }

    @RequestMapping(value = "/users/{username}/forms", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createForm(@RequestBody Form form) {
        formDAO.createForm(form);
    }

    //TODO replace userId with username
    @RequestMapping(value = "/users/{username}/layouts", method = RequestMethod.GET)
    public Collection<Layout> getLayoutsByUser(
            @PathVariable("username") String username,
            @RequestParam Map<String, String> params) {

        return formDAO.getLayoutsByUser(username, params);
    }

}
