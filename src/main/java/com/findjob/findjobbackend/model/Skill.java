package com.findjob.findjobbackend.model;

import com.findjob.findjobbackend.dto.request.SkillDTO;
import com.findjob.findjobbackend.enums.Status;
import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "skill")
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String proficiency;
    @ManyToOne(targetEntity = CV.class)
    private CV cv;
    @Enumerated(EnumType.STRING)
    private Status status;

    public SkillDTO toDto(Skill skill) {
        SkillDTO skillDTO = new SkillDTO();
        skillDTO.setId(skill.getId());
        skillDTO.setName(skill.name);
        skillDTO.setProficiency(skill.proficiency);
        skillDTO.setCvId(skill.getCv().getId());
        return skillDTO;
    }

    public Skill toEntity(SkillDTO skillDTO) {
        Skill skill = new Skill();
        skill.setId(skillDTO.getId());
        skill.setName(skillDTO.getName());
        skill.setProficiency(skillDTO.getProficiency());
        CV cv = new CV();
        cv.setId(skillDTO.getId());
        skill.setCv(cv);
        return skill;
    }
}
