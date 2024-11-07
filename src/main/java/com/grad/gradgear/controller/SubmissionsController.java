package com.grad.gradgear.controller;

import com.grad.gradgear.entity.Submissions;
import com.grad.gradgear.service.SubmissionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class SubmissionsController {
        @Autowired
        private SubmissionsService submissionsService;

        @PostMapping("/submissions")
        public ResponseEntity<Submissions> addSubmission(@RequestBody Submissions submissions) {
            Submissions newSubmission = submissionsService.addSubmission(submissions);
            return new ResponseEntity<>(newSubmission, HttpStatus.CREATED);
        }

        @GetMapping("/submissions")
        public ResponseEntity<List<Submissions>> getAllSubmissions() {
            List<Submissions> submissions = submissionsService.getAllSubmissions();
            return new ResponseEntity<>(submissions, HttpStatus.OK);
        }

        @GetMapping("/submissions/{id}")
        public ResponseEntity<Submissions> getSubmissionById(@PathVariable Long id) {
            Optional<Submissions> submission = submissionsService.getSubmissionById(id);
            return submission.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }
}
