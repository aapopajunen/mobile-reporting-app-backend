package com.swp.Service;



import com.swp.DAO.FormTemplateDAO;
import com.swp.Entity.FormTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class FormService {

    @Autowired
    @Qualifier("mysql")
    private FormTemplateDAO formTemplateDAO;

    public Collection<FormTemplate> getAllForms() {
        return formTemplateDAO.getAllForms();
    }

    public FormTemplate getFormById(int id) {
        return this.formTemplateDAO.getFormById(id);
    }

}
