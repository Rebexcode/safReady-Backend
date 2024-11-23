package com.grad.gradgear.controller;

import com.grad.gradgear.entity.Form;
import com.grad.gradgear.entity.Submissions;
import com.grad.gradgear.service.FormService;
import com.grad.gradgear.service.SubmissionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/form")
public class FormController {

    @Autowired
    private FormService formService;

    @PostMapping("")
    @PreAuthorize("hasRole('ROLE_User')")
    public ResponseEntity<Form> addForm(@RequestBody Form form) {
        Form newForm = formService.addForm(form);
        return new ResponseEntity<>(newForm, HttpStatus.CREATED);
    }


    @GetMapping("")
    @PreAuthorize("hasAnyRole('ROLE_Admin', 'ROLE_User')")
    public ResponseEntity<List<Form>> getAllForms() {
        List<Form> form = formService.getAllForms();
        return new ResponseEntity<>(form, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_Admin')")
    public ResponseEntity<Form> getFormById(@PathVariable Long id) {
        Optional<Form> form = formService.getFormById(id);
        return form.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
