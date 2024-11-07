package com.grad.gradgear.service;


import com.grad.gradgear.entity.Form;
import com.grad.gradgear.entity.Submissions;
import com.grad.gradgear.repository.FormRepo;
import com.grad.gradgear.repository.SubmissionsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FormService {
    @Autowired
    private FormRepo formRepo;


    public List<Form> getAllForms() {
        return formRepo.findAll();
    }

    public Optional<Form> getFormById(Long id) {
        return formRepo.findById(id);
    }

    public Form addForm(Form form) {
        return formRepo.save(form);
    }
}