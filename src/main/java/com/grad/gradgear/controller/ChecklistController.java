package com.grad.gradgear.controller;

import com.grad.gradgear.dto.ReqRes;
import com.grad.gradgear.entity.Checklist;
import com.grad.gradgear.service.ChecklistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/checklists")
public class ChecklistController {
    @Autowired
    private ChecklistService checklistService;

    @PostMapping("/")
    public ResponseEntity<ReqRes> addItem(@RequestBody @Validated ReqRes checklistItem){
        return ResponseEntity.ok(checklistService.addItem(checklistItem));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('USER')")
    public void completeChecklist(@PathVariable Long id) {
        checklistService.updateChecklist(id);
    }

    @GetMapping("/completed-count")
    public long getCompletedChecklistsCount() {
        return checklistService.getCompletedChecklistsCount();
    }
}