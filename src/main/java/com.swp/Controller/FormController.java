package com.swp.Controller;

import com.swp.Entity.*;

import com.swp.DAO.FormDAO;

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

    @RequestMapping(value = "/reports", method = RequestMethod.GET)
    public Collection<Form> getAllReports(@RequestParam Map<String, String> params) {
        return formDAO.getReports(params);
    }

    @RequestMapping(value = "/reports/{id}", method = RequestMethod.GET)
    public Form getReportById(@PathVariable int id) {
        return formDAO.getReportsById(id);
    }

    @RequestMapping(value = "/reports/{id}", method = RequestMethod.DELETE)
    public void deleteReportById(@PathVariable int id) {
        formDAO.deleteReportById(id);
    }

    @RequestMapping(value = "/reports/{id}", method = RequestMethod.PUT)
    public void acceptReportById(@PathVariable int id) {
        formDAO.acceptReportById(id);
    }

    @RequestMapping(value = "/reports/{id}/fields", method = RequestMethod.GET)
    public Collection<Field> getFieldsByReportId(@PathVariable("id") int formId) {
        return formDAO.getFieldsByReportId(formId);
    }

    @RequestMapping(value = "/reports/{id}/answers", method = RequestMethod.GET)
    public Collection<FieldAnswer> getAnswersByReportId(@PathVariable("id") int formId) {
        return formDAO.getAnswersByReportId(formId);
    }

    @RequestMapping(value = "/templates", method = RequestMethod.GET)
    public Collection<Layout> getAllTemplates(@RequestParam Map<String, String> params) {
       return formDAO.getTemplates(params);
    }

    @RequestMapping(value = "/templates/{id}", method = RequestMethod.GET)
    public Layout getTemplateById(@PathVariable("id") int formId) {
        return formDAO.getTemplateById(formId);
    }

    @RequestMapping(value = "/templates/{id}/fields", method = RequestMethod.GET)
    public Collection<Field> getFieldsByTemplateId(@PathVariable("id") int layoutId) {
        return formDAO.getFieldsByTemplateId(layoutId);
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

    @RequestMapping(value = "/users/{username}", method = RequestMethod.DELETE)
    public void deleteUserByUsername(@PathVariable("username") String username) {
        formDAO.deleteUserByUsername(username);
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
    public void deleteUserById(@PathVariable("id") int id) {
        formDAO.deleteUserById(id);
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

    @RequestMapping(value = "/users/{id}/reports", method = RequestMethod.GET)
    public Collection<Form> getReportsByUser(
            @PathVariable("id") int id,
            @RequestParam Map<String, String> params) {
        return formDAO.getReportsByUser(id, params);
    }

    @RequestMapping(value = "/users/{id}/reports", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createReport(@PathVariable int id, @RequestBody Form form) {
        formDAO.createReport(id, form);
    }

    @RequestMapping(value = "/users/{id}/templates", method = RequestMethod.GET)
    public Collection<Layout> getTemplatesByUser(
            @PathVariable("id") int id,
            @RequestParam Map<String, String> params) {
        return formDAO.getTemplatesByUser(id, params);
    }

}
