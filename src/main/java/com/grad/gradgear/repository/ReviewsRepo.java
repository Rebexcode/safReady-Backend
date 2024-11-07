package com.grad.gradgear.repository;

import com.grad.gradgear.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewsRepo extends JpaRepository<Review, Long> {
    Optional<Review> findBySubmissionId(Long submissionId);
}
