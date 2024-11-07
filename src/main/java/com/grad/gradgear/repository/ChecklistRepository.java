package com.grad.gradgear.repository;

import com.grad.gradgear.entity.Checklist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChecklistRepository extends JpaRepository<Checklist, Long> {
    long countByCompleted(boolean completed);

    @Query(value = "SELECT COUNT(*) FROM checklist WHERE completed = true", nativeQuery = true)
    int getCompletedChecklistsCount();

    @Modifying
    @Query(value = "UPDATE checklist SET completed = true WHERE id = :id", nativeQuery = true)
    void updateChecklist(@Param("id") Long id);
}
