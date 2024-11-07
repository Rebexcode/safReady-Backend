package com.grad.gradgear.service;

import com.grad.gradgear.entity.FormReviews;
import com.grad.gradgear.repository.FormReviewsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FormReviewsService {
    @Autowired
    private FormReviewsRepo formreviewsRepo;

    public Optional<FormReviews> getReviewByFormId(Long formId) {
        return formreviewsRepo.findByFormId(formId);
    }

    // Add or update a review
    public FormReviews addOrUpdateContactReview(FormReviews formReviews) {
        // Check if a review exists for the given submission ID
        Optional<FormReviews> existingReview = formreviewsRepo.findByFormId(formReviews.getFormId());

        if (existingReview.isPresent()) {
            // If a review exists, update it
            FormReviews reviewToUpdate = existingReview.get();
            reviewToUpdate.setFeedback(formReviews.getFeedback());
            reviewToUpdate.setStatus(formReviews.getStatus());
            return formreviewsRepo.save(reviewToUpdate);
        } else {
            // If no review exists, create a new one
            return formreviewsRepo.save(formReviews);
        }
    }
}
