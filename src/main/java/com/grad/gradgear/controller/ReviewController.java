package com.grad.gradgear.controller;

import com.grad.gradgear.entity.Review;
import com.grad.gradgear.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PreAuthorize("hasRole('ROLE_User')")
    @GetMapping("/{submissionId}")
    public ResponseEntity<Review> getReviewBySubmissionId(@PathVariable Long submissionId) {
        Optional<Review> review = reviewService.getReviewBySubmissionId(submissionId);
        return review.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @PreAuthorize("hasRole('ROLE_Admin')")
    @PostMapping("/{submissionId}")
    public ResponseEntity<Review> addOrUpdateReview(@PathVariable Long submissionId, @RequestBody Review review) {
        review.setSubmissionId(submissionId);
        Review savedReview = reviewService.addOrUpdateReview(review);
        return new ResponseEntity<>(savedReview, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_Admin')")
    @PutMapping("/{submissionId}")
    public ResponseEntity<Review> updateReview(
            @PathVariable Long submissionId,
            @RequestBody Review updatedReview) {

        Optional<Review> existingReview = reviewService.getReviewBySubmissionId(submissionId);
        if (existingReview.isPresent()) {
            Review reviewToUpdate = existingReview.get();


            reviewToUpdate.setFeedback(updatedReview.getFeedback());
            reviewToUpdate.setStatus(updatedReview.getStatus());

            Review savedReview = reviewService.addOrUpdateReview(reviewToUpdate);
            return new ResponseEntity<>(savedReview, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}