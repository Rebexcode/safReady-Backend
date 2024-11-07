package com.grad.gradgear.service;

import com.grad.gradgear.entity.Submissions;
import com.grad.gradgear.repository.SubmissionsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubmissionsService {

        @Autowired
        private SubmissionsRepo submissionsRepo;


        public List<Submissions> getAllSubmissions() {
            return submissionsRepo.findAll();
        }

        public Optional<Submissions> getSubmissionById(Long id) {
            return submissionsRepo.findById(id);
        }

        public Submissions addSubmission(Submissions submissions) {
            return submissionsRepo.save(submissions);
        }

}
