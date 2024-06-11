package com.findjob.findjobbackend.dto.response;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecruitmentNewDTO {
    private Long id;
    private String title;
    private String description;
    private String status;
    private Long cityId;
    private String cityName;
    private Long companyId;
    private String companyName;
    private Long fieldId;
    private String fieldName;
    private Long vacanciesId;
    private String vacanciesName;
    private Long workingTimeId;
    private String workingTimeName;
    private String avatar;
    private Integer salary;
    private String expDate;

}
