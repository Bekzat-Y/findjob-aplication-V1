package com.findjob.findjobbackend.model;

import com.findjob.findjobbackend.enums.Status;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@Table(name = "apply")
public class Apply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(targetEntity = User.class)
    private User user;
    @ManyToOne(targetEntity = RecruitmentNew.class)
    private RecruitmentNew recruitmentNew;
    @Enumerated(EnumType.STRING)
    private Status status;
    private LocalDate date;
}
