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
    public void createUser(@PathVariable String username) {
        formDAO.createUser(username);
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public User getUserById(@PathVariable int id) {
        return formDAO.getUserById(id);
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable int id) {
        formDAO.deleteUser(id);
    }

    @RequestMapping(value = "/users/{id}/rights", method = RequestMethod.GET)
    public Collection<AccessRights> getUserAccessRights(@PathVariable int id) {
        return formDAO.getUserAccessRights(id);
    }

    @RequestMapping(value = "/users/{id}/rights", method = RequestMethod.POST)
    public void grantUserAccessRights(@PathVariable int id,
                                      @RequestParam Map<String, String> params) {
        formDAO.grantUserAccessRights(id, params);
    }

    @RequestMapping(value = "/users/{id}/rights", method = RequestMethod.DELETE)
    public void deleteUserAccessRights(@PathVariable int id,
                                       @RequestParam Map<String, String> params) {
        formDAO.deleteUserAccessRights(id, params);
    }

    @RequestMapping(value = "/users/{id}/forms", method = RequestMethod.GET)
    public Collection<Form> getFormsByUser(
            @PathVariable("id") int id,
            @RequestParam Map<String, String> params) {
        return formDAO.getFormsByUser(id, params);
    }

    @RequestMapping(value = "/users/{id}/forms", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createForm(@RequestBody Form form) {
        //TODO add id support
        formDAO.createForm(form);
    }

    @RequestMapping(value = "/users/{id}/layouts", method = RequestMethod.GET)
    public Collection<Layout> getLayoutsByUser(
            @PathVariable("id") int id,
            @RequestParam Map<String, String> params) {

        return formDAO.getLayoutsByUser(id, params);
    }

}
