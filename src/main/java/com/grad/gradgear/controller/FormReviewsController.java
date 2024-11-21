package com.grad.gradgear.controller;

import com.grad.gradgear.entity.FormReviews;
import com.grad.gradgear.entity.Review;
import com.grad.gradgear.service.FormReviewsService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/form-reviews")
@PreAuthorize("hasRole('ROLE_Admin')")
public class FormReviewsController {
    @Autowired
    private FormReviewsService formreviewsService;

    // Get review by submission ID (for fetching the review of a specific submission)
    @GetMapping("/{formId}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<FormReviews> getReviewByFormId(@PathVariable Long formId) {
        Optional<FormReviews> review = formreviewsService.getReviewByFormId(formId);
        return review.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<FormReviews> addOrUpdateContactReview(@RequestBody FormReviews formReviews) {
        FormReviews savedReview = formreviewsService.addOrUpdateContactReview(formReviews);
        return new ResponseEntity<>(savedReview, HttpStatus.CREATED);
    }

    @PostMapping("/{formId}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<FormReviews> addOrUpdateContactReview(@PathVariable Long formId, @RequestBody FormReviews formReviews) {
        formReviews.setFormId(formId);
        FormReviews savedReview = formreviewsService.addOrUpdateContactReview(formReviews);
        return new ResponseEntity<>(savedReview, HttpStatus.CREATED);
    }

    @PatchMapping("/{formId}")
    @SecurityRequirement(name = "bearerAuth")
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
