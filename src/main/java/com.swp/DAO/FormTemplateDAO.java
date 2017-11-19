package com.swp.DAO;

import com.swp.Entity.FormTemplate;

import java.util.Collection;

public interface FormTemplateDAO {
    Collection<FormTemplate> getAllForms();

    FormTemplate getFormById(int id);
}
