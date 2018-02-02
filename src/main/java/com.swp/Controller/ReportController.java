package com.swp.Controller;

import com.swp.Entity.*;

import com.swp.DAO.ReportDAO;

import com.swp.Security.JwtGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public SimpleToken login(@RequestBody LoginCredentials loginCredentials) {
        User user = reportDAO.checkLoginCredentials(loginCredentials);
        return new SimpleToken(new JwtGenerator().generate(user));
    }

    /**
     * This function is used for getting a users access rights.
     *
     * @param username Parameter for the user's username.
     * @return Returns a collection of AccessRights of a specific user.
     */
    @GetMapping("/users/{username}/rights")
    public Collection<AccessRights> getUserAccessRights(@PathVariable String username) {
        return reportDAO.getUserAccessRights(username);
    }

    /**
     *This function is used for granting a specific user access rights to some template.
     *
     * Parameters:
     * templateid
     *
     * @param username The username specific to the user.
     * @param params Query parameters from the url.
     */
    @PostMapping("/users/{username}/rights")
    public void grantUserAccessRights(@PathVariable String username,
                                      @RequestParam Map<String, String> params) {
        reportDAO.grantUserAccessRights(username, params);
    }

    /**
     * This function is used for deleting a user's access rights to some template.
     *
     * @param username  The username specific to the user.
     * @param params Query parameters from the url.
     */
    @DeleteMapping("/users/{username}/rights")
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
     * @param username The username specific to the user.
     * @param params Query parameters from the url
     * @return Returns a collection of reports filled by a user.
     */
    @GetMapping("/users/{username}/reports")
    @PreAuthorize("#username == authentication.name")
    public Collection<Report> getReportsByUser(
            @PathVariable("username") String username,
            @RequestParam Map<String, String> params) {
        return reportDAO.getReportsByUser(username, params);
    }


    /**
     * This function is used for creating a new report. The report is created based on the
     * information provided in the json.
     *
     * @param username The username specific to the user.
     * @param report The report to be added to the database.
     */
    @PostMapping(value = "/users/{username}/reports", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("#username == authentication.name")
    public void createReport(@PathVariable String username, @RequestBody Report report) {
        reportDAO.createReport(username, report);
    }

    /**
     * This function is used for getting a report by id that the user has submitted.
     *
     * @param username The username specific to the user.
     * @param reportID The id of the report.
     */
    @GetMapping("/users/{username}/reports/{reportId}")
    @PreAuthorize("#username == authentication.name")
    public Report getUserReportById (
            @PathVariable("username") String username,
            @PathVariable("reportId") int reportID,
            @RequestParam Map<String, String> params) {
        return reportDAO.getUserReportById(username, reportID, params);
    }

    /**
     * This function is used for getting the fields of a report by the user.
     *
     * @param username  The user's username
     * @param reportId  The id of the report.
     * @return Returns a collection of the fields included in the report.
     */
    @GetMapping("/users/{username}/reports/{reportId}/fields")
    @PreAuthorize("#username == authentication.name")
    public Collection<Field> getUserReportFieldsById(
            @PathVariable("username") String username,
            @PathVariable("reportId") int reportId) {
        return reportDAO.getUserReportFieldsById(username, reportId);
    }

    /**
     * This function is used for getting the users answers of a report specified by the report id.
     *
     * @param username The username unique to the user.
     * @param reportId The report id that specifies which report's answers are returned.
     * @return Returns a collection of the FieldAnswers.
     */
    @GetMapping("users/{username}/reports/{reportId}/answers")
    @PreAuthorize("#username == authentication.name")
    public Collection<FieldAnswer> getUserAnswersByReportId (
            @PathVariable("username") String username,
            @PathVariable("reportId") int reportId) {
        return reportDAO.getUserAnswersByReportId(username, reportId);
    }

    /**
     * This function is used for getting templates.
     *
     * @param username The username specific to the user.
     * @param params Query parameters from the url.
     * @return Returns a collection of templates a user has access rights to.
     */
    @GetMapping("/users/{username}/templates")
    @PreAuthorize("#username == authentication.name")
    public Collection<Template> getTemplatesByUser (
            @PathVariable("username") String username,
            @RequestParam Map<String, String> params) {
        return reportDAO.getTemplatesByUser(username, params);
    }

    /**
     * This function is used for getting a template by id that the user has access to.
     *
     * @param username The parameter for the user's username
     * @param templateId The id specific to a template.
     * @return Returns a Template specified by id path variable.
     */
    @GetMapping("/users/{username}/templates/{templateId}")
    @PreAuthorize("#username == authentication.name")
    public Template getUserTemplateById (
            @PathVariable("username") String username,
            @PathVariable("templateId") int templateId) {
        return reportDAO.getUserTemplateById(username, templateId);
    }

    /**
     * This function is used for getting the fields included in a template that the user has access to
     *
     * @param username The username unique to the user.
     * @param templateId The id of the template
     * @return Returns a collection of fields included in the template.
     */
    @GetMapping("/users/{username}/templates/{templateId}/fields")
    @PreAuthorize("#username == authentication.name")
    public Collection<Field> getUserTemplateFieldsById (
            @PathVariable("username") String username,
            @PathVariable("templateId") int templateId) {
        return reportDAO.getUserTemplateFieldsById(username, templateId);
    }

    /**
     * This function is used for getting all the reports of a certain template made by the user.
     *
     * @param username The username unique to the user.
     * @param templateId The id of the template
     * @return Returns a collection of the reports.
     */
    @GetMapping("/users/{username}/templates/{templateId}/reports")
    @PreAuthorize("#username == authentication.name")
    public Collection<Report> getUserReportsByTemplateId (
            @PathVariable("username") String username,
            @PathVariable("templateId") int templateId,
            @RequestParam Map<String, String> params) {
        return reportDAO.getUserReportsByTemplateId(username, templateId, params);
    }


}
