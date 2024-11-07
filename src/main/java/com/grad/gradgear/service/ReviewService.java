package com.grad.gradgear.service;

import com.grad.gradgear.entity.Review;
import com.grad.gradgear.repository.ReviewsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    @Autowired
    private ReviewsRepo reviewsRepo;

    public Optional<Review> getReviewBySubmissionId(Long submissionId) {
        return reviewsRepo.findBySubmissionId(submissionId);
    }

    public Review addOrUpdateReview(Review review) {
        Optional<Review> existingReview = reviewsRepo.findBySubmissionId(review.getSubmissionId());

        if (existingReview.isPresent()) {
            Review reviewToUpdate = existingReview.get();
            reviewToUpdate.setFeedback(review.getFeedback());
            reviewToUpdate.setStatus(review.getStatus());
            return reviewsRepo.save(reviewToUpdate);
        } else {
            return reviewsRepo.save(review);
        }
    }
}

