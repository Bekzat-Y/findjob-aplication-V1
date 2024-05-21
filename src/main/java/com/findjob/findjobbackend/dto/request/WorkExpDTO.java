package com.findjob.findjobbackend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.AccessType;

import java.time.LocalDate;
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
    private Long cvId;

}
