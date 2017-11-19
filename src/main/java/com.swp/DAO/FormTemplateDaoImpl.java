package com.swp.DAO;

import com.swp.Entity.FormTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository("fakeData")
class FormTemplateDaoImpl implements FormTemplateDAO {

    private static Map<Integer, FormTemplate> forms;

    static {
        forms = new HashMap<Integer, FormTemplate>() {

            {
                put(1, new FormTemplate(1, "Raportti 1"));
                put(2, new FormTemplate(2, "Raportti 2"));
                put(3, new FormTemplate(3, "Raportti 3"));
            }

        };
    }

    @Override
    public Collection<FormTemplate> getAllFormTemplates() {
        return forms.values();
    }

    @Override
    public FormTemplate getFormTemplateById(int id) {
        return this.forms.get(id);
    }

}
