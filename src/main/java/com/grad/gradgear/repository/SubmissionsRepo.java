package com.grad.gradgear.repository;

import com.grad.gradgear.entity.Submissions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubmissionsRepo extends JpaRepository<Submissions, Long> {
}
