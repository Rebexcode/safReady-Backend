package com.grad.gradgear.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "checklist")
public class Checklist {
    @Id
    private Long id;
    private String title;
    private String description;
    private boolean completed;
}
