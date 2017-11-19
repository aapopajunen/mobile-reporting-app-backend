package com.swp.Controller;

import com.swp.Entity.FormTemplate;
import com.swp.Service.FormService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/forms")
public class FormController {

    @Autowired
    private FormService formService;


    @RequestMapping(method = RequestMethod.GET)
    public Collection<FormTemplate> getAllForms() {
        return formService.getAllForms();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public FormTemplate getFormById(@PathVariable("id") int id) {
        return formService.getFormById(id);
    }

}
