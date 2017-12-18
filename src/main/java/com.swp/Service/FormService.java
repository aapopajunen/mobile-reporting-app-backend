package com.swp.Service;



import org.springframework.stereotype.Service;

@Service
public class FormService {

    /*

    @Autowired
    @Qualifier("mysql")
    private FormDAO formTemplateDAO;

    public Collection<FormTemplate> getAllFormTemplates() {
        return formTemplateDAO.getAllFormTemplates();
    }

    public FormTemplate getFormTemplateById(int id) {
        return this.formTemplateDAO.getFormTemplateById(id);
    }

    public void insertFormTemplate(FormTemplate formTemplate) {
        formTemplateDAO.insertFormTemplate(formTemplate);
    }

    public void removeFormTemplateById(int id) {
        formTemplateDAO.removeFormTemplateById(id);
    }

    public Collection<Form> filter(
            Optional<String> id,
            Optional<String> layoutID,
            Optional<String> userID,
            Optional<String> title,
            Optional<String> dateCreated,
            Optional<String> dateAccepted) {
        formTemplateDAO.filter(id, layoutID, userID, title, dateCreated, dateAccepted);
    }
    */


    /*
    public Collection<Form> getFormsByUser(
            String username,
            Optional<String> sortParams,
            Optional<String> queryParams,
            Optional<String> fieldParams) {
       return formTemplateDAO.getForms(username, sortParams, queryParams, fieldParams);
    }

    public Collection<Layout> getLayoutsByUser(
            String username,
            Optional<String> sortParams,
            Optional<String> queryParams,
            Optional<String> fieldParams) {
        return formTemplateDAO.getLayoutsByUser(username, sortParams, queryParams, fieldParams);
    }

    public void createUser(String username) {
        formTemplateDAO.createUser(username);
    }

    public void deleteUser(String username) {
        formTemplateDAO.deleteUser(username);
    }

    public Collection<User> getUsers() {
        return formTemplateDAO.getUsers();
    }

    public Collection<Layout> getAllLayouts() {
        return formTemplateDAO.getAllLayouts();
    }

    public Collection<Form> getAllForms(Optional<Integer> layoutId) {
        return null;
    }



    */
}
