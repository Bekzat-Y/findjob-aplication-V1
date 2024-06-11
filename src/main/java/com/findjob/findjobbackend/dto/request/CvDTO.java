package com.findjob.findjobbackend.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class CvDTO {
    private Long id;
    private int expYear;
    private Double salaryExpectation;
    private String fileCV;
    private Long userId;
    private String fullName;
    private String phone;
    private String username;
    private List<SkillDTO> skills;
    private List<WorkExpDTO> workExps;


}
