package com.findjob.findjobbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Table(name = "city")
@Entity
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column
    private String name;
    @OneToMany(targetEntity = Company.class, mappedBy = "city")
    @JsonIgnore
    private List<Company> companies;
    @OneToMany(targetEntity = RecruitmentNew.class, mappedBy = "city")
    @JsonIgnore
    private List<RecruitmentNew> recruitmentNews;

}
