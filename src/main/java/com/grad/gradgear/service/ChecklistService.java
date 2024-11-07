package com.grad.gradgear.service;

import com.grad.gradgear.dto.ReqRes;
import com.grad.gradgear.entity.Checklist;
import com.grad.gradgear.repository.ChecklistRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ChecklistService {

    @Autowired
    private ChecklistRepository checklistRepository;

    public Checklist completeChecklist(Long id) {
        Checklist checklist = checklistRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Checklist not found with id: " + id));
        checklist.setCompleted(true);
        return checklistRepository.save(checklist);
    }

    public long getCompletedChecklistsCount() {
        return checklistRepository.countByCompleted(true);  // Assuming you have a 'completed' field in the Checklist entity
    }

    public ReqRes addItem(ReqRes checklistItem) {
        ReqRes resp = new ReqRes();
        try {
            Checklist checklist = new Checklist();
            checklist.setTitle(checklistItem.getTitle());
            checklist.setDescription(checklistItem.getDescription());
            Checklist checklistResult = checklistRepository.save(checklist);
            if (checklistResult != null && checklistResult.getId() > 0) {
                resp.setChecklist(checklistResult);
                resp.setMessage("Checklist Saved Successfully");
                resp.setStatusCode(200);

                checklistRepository.updateChecklist(checklistResult.getId());
            }
        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;
    }

    public void updateChecklist(Long id) {
        checklistRepository.updateChecklist(id);
    }
}
