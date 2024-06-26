package com.findjob.findjobbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.findjob.findjobbackend.dto.request.CvDTO;
import com.findjob.findjobbackend.dto.request.SkillDTO;
import com.findjob.findjobbackend.dto.request.WorkExpDTO;
import com.findjob.findjobbackend.enums.Status;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.ArrayList;

import java.util.List;
@Entity
@Table(name = "cv")
@Data
public class CV {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    private User user;
    private int expYear;
    private Double salaryExpectation;
    private String fileCV;
    @OneToMany(targetEntity = Skill.class, fetch = FetchType.EAGER, mappedBy = "cv")
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonIgnore
    private List<Skill> skills;
    @OneToMany(mappedBy = "cv", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<WorkExp> workExps;
    @Enumerated(EnumType.STRING)
    private Status status;

    public CvDTO toDto(CV cv) {
        CvDTO cvDTO = new CvDTO();
        cvDTO.setId(cv.id);
        cvDTO.setUsername(cv.user.getAccount().getUsername());
        cvDTO.setSalaryExpectation(cv.salaryExpectation);
        cvDTO.setExpYear(cv.expYear);
        cvDTO.setFileCV(cv.fileCV);
        cvDTO.setUserId(cv.user.getId());
        cvDTO.setFullName(cv.user.getName());
        cvDTO.setPhone(cv.getUser().getPhone());

        List<Skill> skills = cv.getSkills();
        List<SkillDTO> skillDTOS = new ArrayList<>();
        if (!skills.isEmpty()) {
            for (Skill s : skills
                 ) {
               SkillDTO skillDTO = s.toDto(s);
                skillDTOS.add(skillDTO);
            }
        }
        cvDTO.setSkills(skillDTOS);

        List<WorkExp> workExps = cv.getWorkExps();
        List<WorkExpDTO> workExpDTOS = new ArrayList<>();
        if (!workExps.isEmpty()) {
            for (WorkExp w : workExps) {
                WorkExpDTO workExpDTO = w.toDto(w);
                workExpDTOS.add(workExpDTO);
            }
        }
        cvDTO.setWorkExps(workExpDTOS);
        return cvDTO;
    }

    public CV toEntity(CvDTO cvDTO) {
        CV cv = new CV();
        cv.setSalaryExpectation(cvDTO.getSalaryExpectation());
        cv.setExpYear(cvDTO.getExpYear());
        cv.setFileCV(cvDTO.getFileCV());
        return cv;
    }
}
