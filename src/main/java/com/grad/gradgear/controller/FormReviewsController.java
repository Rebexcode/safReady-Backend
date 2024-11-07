package com.grad.gradgear.controller;

import com.grad.gradgear.entity.FormReviews;
import com.grad.gradgear.entity.Review;
import com.grad.gradgear.service.FormReviewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class FormReviewsController {
    @Autowired
    private FormReviewsService formreviewsService;

    // Get review by submission ID (for fetching the review of a specific submission)
    @GetMapping("/form-reviews/{formId}")
    public ResponseEntity<FormReviews> getReviewByFormId(@PathVariable Long formId) {
        Optional<FormReviews> review = formreviewsService.getReviewByFormId(formId);
        return review.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/form-reviews")
    public ResponseEntity<FormReviews> addOrUpdateContactReview(@RequestBody FormReviews formReviews) {
        // Ensure that formId is set from the incoming formReviews object
        FormReviews savedReview = formreviewsService.addOrUpdateContactReview(formReviews);
        return new ResponseEntity<>(savedReview, HttpStatus.CREATED);
    }

    @PostMapping("/form-reviews/{formId}")
    public ResponseEntity<FormReviews> addOrUpdateContactReview(@PathVariable Long formId, @RequestBody FormReviews formReviews) {
        formReviews.setFormId(formId);
        FormReviews savedReview = formreviewsService.addOrUpdateContactReview(formReviews);
        return new ResponseEntity<>(savedReview, HttpStatus.CREATED);
    }

    @PatchMapping("/form-reviews/{formId}")
    public ResponseEntity<FormReviews> updateReview(
            @PathVariable Long formId,
            @RequestBody FormReviews updatedReview) {

        Optional<FormReviews> existingReview = formreviewsService.getReviewByFormId(formId);
        if (existingReview.isPresent()) {
            FormReviews reviewToUpdate = existingReview.get();


            reviewToUpdate.setFeedback(updatedReview.getFeedback());
            reviewToUpdate.setStatus(updatedReview.getStatus());

            FormReviews savedReview = formreviewsService.addOrUpdateContactReview(reviewToUpdate);
            return new ResponseEntity<>(savedReview, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
