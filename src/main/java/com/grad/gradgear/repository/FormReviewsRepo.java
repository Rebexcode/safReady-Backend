package com.grad.gradgear.repository;

import com.grad.gradgear.entity.FormReviews;
import com.grad.gradgear.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FormReviewsRepo extends JpaRepository<FormReviews, Long> {
    Optional<FormReviews> findByFormId(Long formId);
}
