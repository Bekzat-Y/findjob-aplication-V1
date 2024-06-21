package com.findjob.findjobbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.findjob.findjobbackend.dto.request.WorkExpDTO;
import com.findjob.findjobbackend.enums.Status;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "workexp")
@Data
public class WorkExp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "cv_id", nullable = false )
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private CV cv;
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    private String content;
    @Enumerated(EnumType.STRING)
    private Status status;

    public WorkExpDTO toDto(WorkExp workExp) {
        WorkExpDTO workExpDTO = new WorkExpDTO();
        workExpDTO.setId(workExp.getId());
        workExpDTO.setContent(workExp.getContent());
        workExpDTO.setStartDate(workExp.getStartDate());
        workExpDTO.setEndDate(workExp.getEndDate());
        workExpDTO.setTitle(workExp.getTitle());
        workExpDTO.setCvId(workExpDTO.getCvId());
        return workExpDTO;
    }

    public WorkExp toEntity(WorkExpDTO workExpDTO) {
        WorkExp workExp = new WorkExp();
        workExp.setId(workExpDTO.getId());
        workExp.setContent(workExpDTO.getContent());
        workExp.setTitle(workExpDTO.getTitle());
        workExp.setStartDate(workExpDTO.getStartDate());
        workExp.setEndDate(workExpDTO.getEndDate());
        CV cv = new CV();
        cv.setId(workExpDTO.getId());
        workExp.setCv(cv);
        return workExp;
    }
}
