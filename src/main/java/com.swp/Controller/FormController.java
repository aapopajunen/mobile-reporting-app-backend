package com.swp.Controller;

import com.swp.DAO.FormDAO;
import com.swp.Entity.*;
import com.swp.Security.JwtGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;


@RestController
public class FormController {

    /**
     * Form Data Access Object.
     */
    @Autowired
    @Qualifier("mysqlnew")
    private FormDAO formDAO;

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public SimpleToken login(@RequestBody LoginCredentials loginCredentials) {
        User user = formDAO.checkLoginCredentials(loginCredentials);
        return new SimpleToken(new JwtGenerator().generate(user));
    }


    @GetMapping("/users")
    public Collection<User> getAllUsers() {
        return formDAO.getAllUsers();
    }

    /**
     * This function is used for getting forms of a user.
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
     * @return Returns a collection of forms filled by a user.
     */
    @GetMapping("/users/{username}/forms")
    @PreAuthorize("#username == authentication.name")
    public Collection<Form> getFormsByUser(
            @PathVariable("username") String username,
            @RequestParam Map<String, String> params) {
        return formDAO.getFormsByUser(username, params);
    }


    /**
     * This function is used for creating a new form. The form is created based on the
     * information provided in the json.
     *
     * @param username The username specific to the user.
     * @param form The form to be added to the database.
     */
    @PostMapping(value = "/users/{username}/forms", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("#username == authentication.name")
    public void createForm(@PathVariable String username, @RequestBody Form form) {
        formDAO.createForm(username, form);
    }

    /**
     * This function is used for getting a form by id that the user has submitted.
     *
     * @param username The username specific to the user.
     * @param formID The id of the form.
     */
    @GetMapping("/users/{username}/forms/{formId}")
    @PreAuthorize("#username == authentication.name")
    public Form getUserFormById (
            @PathVariable("username") String username,
            @PathVariable("formId") int formID,
            @RequestParam Map<String, String> params) {
        return formDAO.getUserFormById(username, formID, params);
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
        return formDAO.getTemplatesByUser(username, params);
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
        return formDAO.getUserTemplateById(username, templateId);
    }

    @GetMapping("/users/{username}/templates/{templateId}/empty")
    @PreAuthorize("#username == authentication.name")
    public Object getEmptyTemplate(
            @PathVariable("username") String username,
            @PathVariable("templateId") int templateId) {
        return formDAO.getEmptyTemplate(username, templateId);
    }

    /**
     * This function is used for getting all the forms of a certain template made by the user.
     *
     * @param username The username unique to the user.
     * @param templateId The id of the template
     * @return Returns a collection of the forms.
     */
    @GetMapping("/users/{username}/templates/{templateId}/forms")
    @PreAuthorize("#username == authentication.name")
    public Collection<Form> getUserFormsByTemplateId (
            @PathVariable("username") String username,
            @PathVariable("templateId") int templateId,
            @RequestParam Map<String, String> params) {
        return formDAO.getUserFormsByTemplateId(username, templateId, params);
    }


}
