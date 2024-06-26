package com.findjob.findjobbackend.service.skill;

import com.findjob.findjobbackend.model.Skill;
import com.findjob.findjobbackend.service.IGeneralService;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ISkillService extends IGeneralService<Skill> {
    Boolean existsByCv_Id(Long id);
    boolean existsById(Long id);
    Optional<Skill> updateSkill(Skill skill);
}
