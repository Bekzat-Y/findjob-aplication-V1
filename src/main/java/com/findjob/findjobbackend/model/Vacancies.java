package com.findjob.findjobbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;


@Data
@Entity
@Table(name = "vacancies")
public class Vacancies {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(targetEntity = RecruitmentNew.class, mappedBy = "vacancies")
    @JsonIgnore
    private List<RecruitmentNew> recruitmentNews;
}
