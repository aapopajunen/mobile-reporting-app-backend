package com.swp.DAO;

import com.swp.Entity.FormTemplate;

import java.util.Collection;

public interface FormTemplateDAO {

    Collection<FormTemplate> getAllFormTemplates();

    FormTemplate getFormTemplateById(int id);

    void insertFormTemplate(FormTemplate formTemplate);

    void removeFormTemplateById(int id);

}
