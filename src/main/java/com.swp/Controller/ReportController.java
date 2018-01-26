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

    /**
     * Report Data Access Object.
     */
    @Autowired
    @Qualifier("mysql")
    private ReportDAO reportDAO;

    /**
     * This function is used for getting reports.
     *
     * Supported parameters:
     * templateid,
     * userid,
     * page,
     * pagesize,
     * sort,
     * search
     * 
     * @param params
     * @return Returns a collection of reports based on the given parameters.
     */
    @RequestMapping(value = "/reports", method = RequestMethod.GET)
    public Collection<Report> getReports(@RequestParam Map<String, String> params) {
        return reportDAO.getReports(params);
    }

    /**
     * This function is used for getting a specific report by id.
     *
     * @param id
     * @return Returns a form by the id specified in the path.
     */
    @RequestMapping(value = "/reports/{id}", method = RequestMethod.GET)
    public Report getReportById(@PathVariable int id) {
        return reportDAO.getReportsById(id);
    }


    /**
     * This function is used for deleting reports. When a report is deleted, all the answers
     * related to it will also get deleted.
     *
     * @param id
     */
    @RequestMapping(value = "/reports/{id}", method = RequestMethod.DELETE)
    public void deleteReportById(@PathVariable int id) {
        reportDAO.deleteReportById(id);
    }

    /**
     * This function is used for accepting a report by id. The reports acception date will be set
     * automatically set to current date.
     *
     * @param id
     */
    @RequestMapping(value = "/reports/{id}", method = RequestMethod.PUT)
    public void acceptReportById(@PathVariable int id) {
        reportDAO.acceptReportById(id);
    }

    /**
     * This funnction is used for getting the fields of a report specified by the report id.
     *
     * @param formId
     * @return Returns a collection of fields of a specific report.
     */
    @RequestMapping(value = "/reports/{id}/fields", method = RequestMethod.GET)
    public Collection<Field> getFieldsByReportId(@PathVariable("id") int formId) {
        return reportDAO.getFieldsByReportId(formId);
    }

    /**
     * This function is used for getting the answers of a report specified by the report id.
     *
     * @param formId
     * @return Returns a collection of FieldAnswers of a specific report.
     */
    @RequestMapping(value = "/reports/{id}/answers", method = RequestMethod.GET)
    public Collection<FieldAnswer> getAnswersByReportId(@PathVariable("id") int formId) {
        return reportDAO.getAnswersByReportId(formId);
    }

    /**
     * This function is used for getting templates.
     *
     * parameters:
     * page,
     * pagesize,
     * sort,
     * search
     *
     * @param params
     * @return Returns a collection of reports based on the given parameters.
     */
    @RequestMapping(value = "/templates", method = RequestMethod.GET)
    public Collection<Template> getTemplates(@RequestParam Map<String, String> params) {
       return reportDAO.getTemplates(params);
    }

    /**
     * This function is used for getting a template specified by the template id.
     *
     * @param templateId
     * @return Returns a Template specified by id path variable.
     */
    @RequestMapping(value = "/templates/{id}", method = RequestMethod.GET)
    public Template getTemplateById(@PathVariable("id") int templateId) {
        return reportDAO.getTemplateById(templateId);
    }

    /**
     * This function is used for getting Fields of a specific template.
     *
     * @param templateId
     * @return Returns a collection of Fields based on the id path variable.
     */
    @RequestMapping(value = "/templates/{id}/fields", method = RequestMethod.GET)
    public Collection<Field> getFieldsByTemplateId(@PathVariable("id") int templateId) {
        return reportDAO.getFieldsByTemplateId(templateId);
    }

    /**
     * This function is used for getting users.
     *
     * @return Returns a collection containing all Users.
     */
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public Collection<User> getAllUsers() {
        return reportDAO.getAllUsers();
    }

    /**
     * This function is used for creating a new user.
     *
     * @param username
     */
    @RequestMapping(value = "/users/{username}", method = RequestMethod.POST)
    public void createUser(@PathVariable String username) {
        reportDAO.createUser(username);
    }

    /**
     * This function is used for getting a specific user.
     *
     * @param id
     * @return Returns a user with the given id.
     */
    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public User getUserById(@PathVariable int id) {
        return reportDAO.getUserById(id);
    }

    /**
     * This function is used for deleting a user by username.
     *
     * @param username
     */
    @RequestMapping(value = "/users/{username}", method = RequestMethod.DELETE)
    public void deleteUserByUsername(@PathVariable("username") String username) {
        reportDAO.deleteUserByUsername(username);
    }

    /**
     * This function is used for deleting a user by id.
     *
     * @param id
     */
    @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
    public void deleteUserById(@PathVariable("id") int id) {
        reportDAO.deleteUserById(id);
    }

    /**
     * This function is used for getting a users access rights.
     *
     * @param username Parameter for the user's username.
     * @return Returns a collection of AccessRights of a specific user.
     */
    @RequestMapping(value = "/users/{username}/rights", method = RequestMethod.GET)
    public Collection<AccessRights> getUserAccessRights(@PathVariable String username) {
        return reportDAO.getUserAccessRights(username);
    }

    /**
     *This function is used for granting a specific user access rights to some template.
     *
     * Parameters:
     * layoutid
     *
     * @param username Parameter for the user's username.
     * @param params
     */
    @RequestMapping(value = "/users/{username}/rights", method = RequestMethod.POST)
    public void grantUserAccessRights(@PathVariable String username,
                                      @RequestParam Map<String, String> params) {
        reportDAO.grantUserAccessRights(username, params);
    }

    /**
     * This function is used for deleting a users access rights to some template.
     *
     * @param username  Parameter for the user's username.
     * @param params
     */
    @RequestMapping(value = "/users/{username}/rights", method = RequestMethod.DELETE)
    public void deleteUserAccessRights(@PathVariable String username,
                                       @RequestParam Map<String, String> params) {
        reportDAO.deleteUserAccessRights(username, params);
    }

    /**
     * This function is used for getting reports of a user.
     *
     * Parameters:
     * templateid,
     * page,
     * pagesize,
     * sort,
     * search
     *
     * @param username The parameter for the user's username.
     * @param params
     * @return Returns a collection of reports filled by a user.
     */
    @RequestMapping(value = "/users/{username}/reports", method = RequestMethod.GET)
    public Collection<Report> getReportsByUser(
            @PathVariable("username") String username,
            @RequestParam Map<String, String> params) {
        return reportDAO.getReportsByUser(username, params);
    }


    /**
     * This function is used for creating a new report. The report is created based on the
     * information provided in the json.
     *
     * @param username The parameter for the user's username.
     * @param report
     */
    @RequestMapping(value = "/users/{username}/reports", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createReport(@PathVariable String username, @RequestBody Report report) {
        reportDAO.createReport(username, report);
    }

    /**
     * This function is used for getting a report by id that the user has submitted.
     *
     * @param username
     * @param reportID
     */
    @RequestMapping(value = "users/{username}/reports/{reportId}", method = RequestMethod.GET)
    public Report getUsersReportById (
            @PathVariable("username") String username,
            @PathVariable("reportId") int reportID) {
        return reportDAO.getUsersReportById(username, reportID);
    }

    /**
     * This function is used for getting templates.
     *
     * @param username The parameter for the user's username.
     * @param params
     * @return Returns a collection of templates a user has access rights to.
     */
    @RequestMapping(value = "/users/{username}/templates", method = RequestMethod.GET)
    public Collection<Template> getTemplatesByUser(
            @PathVariable("username") String username,
            @RequestParam Map<String, String> params) {
        return reportDAO.getTemplatesByUser(username, params);
    }

    /**
     * This function is used for getting a template by id that the user has access to.
     *
     * @param username The parameter for the user's username
     * @param templateID The id specific to a template.
     * @return Returns a Template specified by id path variable.
     */
    @RequestMapping(value = "users/{username}/templates/{templateId}", method = RequestMethod.GET)
    public Template getUsersTemplateById (
            @PathVariable("username") String username,
            @PathVariable("templateId") int templateID) {
        return reportDAO.getUsersTemplateById(username, templateID);
    }

}
