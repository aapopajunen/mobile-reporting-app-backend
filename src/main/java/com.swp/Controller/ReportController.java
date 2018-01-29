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
     * @param params Query parameters from the url.
     * @return Returns a collection of reports based on the given parameters.
     */
    @RequestMapping(value = "/reports", method = RequestMethod.GET)
    public Collection<Report> getReports(@RequestParam Map<String, String> params) {
        return reportDAO.getReports(params);
    }

    /**
     * This function is used for getting a specific report by id.
     *
     * @param id The id of the wanted report.
     * @return Returns a report by the id specified in the path.
     */
    @RequestMapping(value = "/reports/{id}", method = RequestMethod.GET)
    public Report getReportById(@PathVariable int id) {
        return reportDAO.getReportsById(id);
    }


    /**
     * This function is used for deleting reports. When a report is deleted, all the answers
     * related to it will also get deleted.
     *
     * @param id The id of the report to be deleted.
     */
    @RequestMapping(value = "/reports/{id}", method = RequestMethod.DELETE)
    public void deleteReportById(@PathVariable int id) {
        reportDAO.deleteReportById(id);
    }

    /**
     * This function is used for accepting a report by id. The report's acceptance date will be set
     * automatically to the current date.
     *
     * @param id The of the report to be accepted.
     */
    @RequestMapping(value = "/reports/{id}", method = RequestMethod.PUT)
    public void acceptReportById(@PathVariable int id) {
        reportDAO.acceptReportById(id);
    }

    /**
     * This funnction is used for getting the fields of a report specified by the report id.
     *
     * @param reportId The id of the wanted report.
     * @return Returns a collection of fields of a specific report.
     */
    @RequestMapping(value = "/reports/{id}/fields", method = RequestMethod.GET)
    public Collection<Field> getFieldsByReportId(@PathVariable("id") int reportId) {
        return reportDAO.getFieldsByReportId(reportId);
    }

    /**
     * This function is used for getting the answers of a report specified by the report id.
     *
     * @param reportId The id of the wanted report.
     * @return Returns a collection of FieldAnswers of a specific report.
     */
    @RequestMapping(value = "/reports/{id}/answers", method = RequestMethod.GET)
    public Collection<FieldAnswer> getAnswersByReportId(@PathVariable("id") int reportId) {
        return reportDAO.getAnswersByReportId(reportId);
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
     * @param params Query parameters from the url.
     * @return Returns a collection of reports based on the given parameters.
     */
    @RequestMapping(value = "/templates", method = RequestMethod.GET)
    public Collection<Template> getTemplates(@RequestParam Map<String, String> params) {
       return reportDAO.getTemplates(params);
    }

    /**
     * This function is used for getting a template specified by the template id.
     *
     * @param templateId The id of the template.
     * @return Returns a Template specified by id path variable.
     */
    @RequestMapping(value = "/templates/{id}", method = RequestMethod.GET)
    public Template getTemplateById(@PathVariable("id") int templateId) {
        return reportDAO.getTemplateById(templateId);
    }

    /**
     * This function is used for getting Fields of a specific template.
     *
     * @param templateId The id of the template.
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
     * @param username The username unique to the user.
     */
    @RequestMapping(value = "/users/{username}", method = RequestMethod.POST)
    public void createUser(@PathVariable String username) {
        reportDAO.createUser(username);
    }

    /**
     * This function is used for getting a specific user.
     *
     * @param id The id specific to the user.
     * @return Returns a user with the given id.
     */
    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public User getUserById(@PathVariable int id) {
        return reportDAO.getUserById(id);
    }

    /**
     * This function is used for deleting a user by username.
     *
     * @param username The username of the user to be deleted.
     */
    @RequestMapping(value = "/users/{username}", method = RequestMethod.DELETE)
    public void deleteUserByUsername(@PathVariable("username") String username) {
        reportDAO.deleteUserByUsername(username);
    }

    /**
     * This function is used for deleting a user by id.
     *
     * @param id The id of the user to be deleted.
     */
    @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
    public void deleteUserById(@PathVariable("id") int id) {
        reportDAO.deleteUserById(id);
    }

    /**
     * This function is used for getting a users access rights.
     *
     * @param id The id specific to the user.
     * @return Returns a collection of AccessRights of a specific user.
     */
    @RequestMapping(value = "/users/{id}/rights", method = RequestMethod.GET)
    public Collection<AccessRights> getUserAccessRights(@PathVariable int id) {
        return reportDAO.getUserAccessRights(id);
    }

    /**
     *This function is used for granting a specific user access rights to some template.
     *
     * Parameters:
     * templateid
     *
     * @param id The id of the template.
     * @param params Query parameters from the url.
     */
    @RequestMapping(value = "/users/{id}/rights", method = RequestMethod.POST)
    public void grantUserAccessRights(@PathVariable int id,
                                      @RequestParam Map<String, String> params) {
        reportDAO.grantUserAccessRights(id, params);
    }

    /**
     * This function is used for deleting a user's access rights to some template.
     *
     * @param id The id specific to the user.
     * @param params Query parameters from the url.
     */
    @RequestMapping(value = "/users/{id}/rights", method = RequestMethod.DELETE)
    public void deleteUserAccessRights(@PathVariable int id,
                                       @RequestParam Map<String, String> params) {
        reportDAO.deleteUserAccessRights(id, params);
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
     * @param id The id specific to the user.
     * @param params Query parameters from the url.
     * @return Returns a collection of reports filled by a user.
     */
    @RequestMapping(value = "/users/{id}/reports", method = RequestMethod.GET)
    public Collection<Report> getReportsByUser(
            @PathVariable("id") int id,
            @RequestParam Map<String, String> params) {
        return reportDAO.getReportsByUser(id, params);
    }


    /**
     * This function is used for creating a new report. The report is created based on the
     * information provided in the json.
     *
     * @param id The id specific to the user.
     * @param report The report to be added to the database.
     */
    @RequestMapping(value = "/users/{id}/reports", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createReport(@PathVariable int id, @RequestBody Report report) {
        reportDAO.createReport(id, report);
    }

    /**
     * This function is used for getting templates.
     *
     * @param id The id specific to the user.
     * @param params Query parameters from the url.
     * @return Returns a collection of templates a user has access rights to.
     */
    @RequestMapping(value = "/users/{id}/templates", method = RequestMethod.GET)
    public Collection<Template> getTemplatesByUser(
            @PathVariable("id") int id,
            @RequestParam Map<String, String> params) {
        return reportDAO.getTemplatesByUser(id, params);
    }

}
