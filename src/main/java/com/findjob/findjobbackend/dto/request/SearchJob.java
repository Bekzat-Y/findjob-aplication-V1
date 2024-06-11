package com.findjob.findjobbackend.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchJob {
    private String title;
    private Long cityId;
    private Long fieldId;
    private Long companyId;
    private Long vacancies;
    private Long workingTimeId;
    private Integer start;
    private Integer pageSize;
    private Integer salary;


    public SearchJob(String title, Long cityId, Long fieldId, Long companyId, Long vacancies, Long workingTimeId, Integer start, Integer pageSize, Integer salary) {
        this.title = title;
        this.cityId = cityId;
        this.fieldId = fieldId;
        this.companyId = companyId;
        this.vacancies = vacancies;
        this.workingTimeId = workingTimeId;
        this.start = start;
        this.pageSize = pageSize;
        this.salary = salary;
    }


}
