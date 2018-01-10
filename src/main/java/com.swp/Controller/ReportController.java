package com.swp.Controller;

import com.swp.Entity.*;

import com.swp.DAO.ReportDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class ReportController {

    @Autowired
    @Qualifier("mysql")
    private ReportDAO reportDAO;

    @RequestMapping(value = "/reports", method = RequestMethod.GET)
    public Collection<Report> getAllReports(@RequestParam Map<String, String> params) {
        return reportDAO.getReports(params);
    }

    @RequestMapping(value = "/reports/{id}", method = RequestMethod.GET)
    public Report getReportById(@PathVariable int id) {
        return reportDAO.getReportsById(id);
    }

    @RequestMapping(value = "/reports/{id}", method = RequestMethod.DELETE)
    public void deleteReportById(@PathVariable int id) {
        reportDAO.deleteReportById(id);
    }

    @RequestMapping(value = "/reports/{id}", method = RequestMethod.PUT)
    public void acceptReportById(@PathVariable int id) {
        reportDAO.acceptReportById(id);
    }

    @RequestMapping(value = "/reports/{id}/fields", method = RequestMethod.GET)
    public Collection<Field> getFieldsByReportId(@PathVariable("id") int formId) {
        return reportDAO.getFieldsByReportId(formId);
    }

    @RequestMapping(value = "/reports/{id}/answers", method = RequestMethod.GET)
    public Collection<FieldAnswer> getAnswersByReportId(@PathVariable("id") int formId) {
        return reportDAO.getAnswersByReportId(formId);
    }

    @RequestMapping(value = "/templates", method = RequestMethod.GET)
    public Collection<Template> getAllTemplates(@RequestParam Map<String, String> params) {
       return reportDAO.getTemplates(params);
    }

    @RequestMapping(value = "/templates/{id}", method = RequestMethod.GET)
    public Template getTemplateById(@PathVariable("id") int templateId) {
        return reportDAO.getTemplateById(templateId);
    }

    @RequestMapping(value = "/templates/{id}/fields", method = RequestMethod.GET)
    public Collection<Field> getFieldsByTemplateId(@PathVariable("id") int templateId) {
        return reportDAO.getFieldsByTemplateId(templateId);
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public Collection<User> getAllUsers() {
        return reportDAO.getAllUsers();
    }

    @RequestMapping(value = "/users/{username}", method = RequestMethod.POST)
    public void createUser(@PathVariable String username) {
        reportDAO.createUser(username);
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public User getUserById(@PathVariable int id) {
        return reportDAO.getUserById(id);
    }

    @RequestMapping(value = "/users/{username}", method = RequestMethod.DELETE)
    public void deleteUserByUsername(@PathVariable("username") String username) {
        reportDAO.deleteUserByUsername(username);
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
    public void deleteUserById(@PathVariable("id") int id) {
        reportDAO.deleteUserById(id);
    }

    @RequestMapping(value = "/users/{id}/rights", method = RequestMethod.GET)
    public Collection<AccessRights> getUserAccessRights(@PathVariable int id) {
        return reportDAO.getUserAccessRights(id);
    }

    @RequestMapping(value = "/users/{id}/rights", method = RequestMethod.POST)
    public void grantUserAccessRights(@PathVariable int id,
                                      @RequestParam Map<String, String> params) {
        reportDAO.grantUserAccessRights(id, params);
    }

    @RequestMapping(value = "/users/{id}/rights", method = RequestMethod.DELETE)
    public void deleteUserAccessRights(@PathVariable int id,
                                       @RequestParam Map<String, String> params) {
        reportDAO.deleteUserAccessRights(id, params);
    }

    @RequestMapping(value = "/users/{id}/reports", method = RequestMethod.GET)
    public Collection<Report> getReportsByUser(
            @PathVariable("id") int id,
            @RequestParam Map<String, String> params) {
        return reportDAO.getReportsByUser(id, params);
    }

    @RequestMapping(value = "/users/{id}/reports", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createReport(@PathVariable int id, @RequestBody Report report) {
        reportDAO.createReport(id, report);
    }

    @RequestMapping(value = "/users/{id}/templates", method = RequestMethod.GET)
    public Collection<Template> getTemplatesByUser(
            @PathVariable("id") int id,
            @RequestParam Map<String, String> params) {
        return reportDAO.getTemplatesByUser(id, params);
    }

}
