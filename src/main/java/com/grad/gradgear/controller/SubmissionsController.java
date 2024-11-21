package com.grad.gradgear.controller;

import com.grad.gradgear.entity.Submissions;
import com.grad.gradgear.service.SubmissionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/submissions")
public class SubmissionsController {
        @Autowired
        private SubmissionsService submissionsService;

        @PreAuthorize("hasRole('ROLE_User')")
        @PostMapping("")
        public ResponseEntity<Submissions> addSubmission(@RequestBody Submissions submissions) {
            Submissions newSubmission = submissionsService.addSubmission(submissions);
            return new ResponseEntity<>(newSubmission, HttpStatus.CREATED);
        }

        @GetMapping("")
        @PreAuthorize("hasRole('ROLE_Admin')")
        public ResponseEntity<List<Submissions>> getAllSubmissions() {
            List<Submissions> submissions = submissionsService.getAllSubmissions();
            return new ResponseEntity<>(submissions, HttpStatus.OK);
        }

        @GetMapping("/{id}")
        @PreAuthorize("hasRole('ROLE_User')")
        public ResponseEntity<Submissions> getSubmissionById(@PathVariable Long id) {
            Optional<Submissions> submission = submissionsService.getSubmissionById(id);
            return submission.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }
}
