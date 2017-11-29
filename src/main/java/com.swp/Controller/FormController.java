package com.swp.Controller;

import com.swp.Entity.FormTemplate;
import com.swp.Service.FormService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.boot.json.BasicJsonParser;

import java.util.Collection;
import java.util.Map;

@RestController
@RequestMapping("/forms")
public class FormController {

    @Autowired
    private FormService formService;




    @RequestMapping(method = RequestMethod.GET)
    public Collection<FormTemplate> getAllFormTemplates() {
        return formService.getAllFormTemplates();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public FormTemplate getFormTemplateById(@PathVariable("id") int id) {
        return formService.getFormTemplateById(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void removeFormTemplateById(@PathVariable("id") int id) {
        formService.removeFormTemplateById(id);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void insertFormTemplate(@RequestBody FormTemplate formTemplate) {
        formService.insertFormTemplate(formTemplate);
    }

}
