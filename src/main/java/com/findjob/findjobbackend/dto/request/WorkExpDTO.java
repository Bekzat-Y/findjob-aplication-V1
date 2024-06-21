package com.findjob.findjobbackend.dto.request;

import com.findjob.findjobbackend.model.CV;
import com.findjob.findjobbackend.model.WorkExp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.AccessType;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WorkExpDTO {
    private Long id;
    private String content;
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    private CV cvId;

    public WorkExpDTO(WorkExp workExp) {
    }
}

