package com.grad.gradgear.controller;

import com.grad.gradgear.entity.Form;
import com.grad.gradgear.entity.Submissions;
import com.grad.gradgear.service.FormService;
import com.grad.gradgear.service.SubmissionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class FormController {

    @Autowired
    private FormService formService;

    @PostMapping("/form")
    public ResponseEntity<Form> addForm(@RequestBody Form form) {
        Form newForm = formService.addForm(form);
        return new ResponseEntity<>(newForm, HttpStatus.CREATED);
    }

    @GetMapping("/form")
    public ResponseEntity<List<Form>> getAllForms() {
        List<Form> form = formService.getAllForms();
        return new ResponseEntity<>(form, HttpStatus.OK);
    }

    @GetMapping("/form/{id}")
    public ResponseEntity<Form> getFormById(@PathVariable Long id) {
        Optional<Form> form = formService.getFormById(id);
        return form.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
