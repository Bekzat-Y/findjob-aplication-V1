package com.findjob.findjobbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.findjob.findjobbackend.enums.Status;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Table(name = "recruitmentnew")
public class RecruitmentNew {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @ManyToOne(targetEntity = WorkingTime.class)
    private WorkingTime workingTime;
    @ManyToOne(targetEntity = Field.class)
    private Field field;
    @ManyToOne(targetEntity = Company.class)
    private Company company;
    @ManyToOne(targetEntity = Vacancies.class)
    private Vacancies vacancies;
    private LocalDate expDate;
    private String description;
    private Integer quantity;
    private Integer salary;
    private int gender;
    @ManyToOne(targetEntity = City.class)
    private City city;
    private Boolean status;
    @OneToMany(targetEntity = Apply.class, mappedBy = "recruitmentNew")
    @JsonIgnore
    private List<Apply> applies;
    @Enumerated(EnumType.STRING)
    private Status recruitStatus;
}
