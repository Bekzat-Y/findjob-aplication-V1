package com.findjob.findjobbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.findjob.findjobbackend.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Table(name = "company", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "name"
        }),
})
@NoArgsConstructor
@AllArgsConstructor
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String codeCompany;
    private String name;
    private String avatar;
    private String description;
    private String address;
    private int employeeQuantity;
    @ManyToOne(targetEntity = City.class)
    private City city;
    private String linkMap;
    private String phone;
    private Status statusCompany;
    @OneToOne
    private Account account;
    @OneToMany(targetEntity = RecruitmentNew.class, mappedBy = "company")
    @JsonIgnore
    private List<RecruitmentNew> recruitmentNews;

}
