package com.findjob.findjobbackend.service.skill;

import com.findjob.findjobbackend.model.Skill;
import com.findjob.findjobbackend.service.IGeneralService;
import org.springframework.stereotype.Repository;

@Repository
public interface ISkillService extends IGeneralService<Skill> {
    Iterable<Skill> findAllSkillsByCvId(Long id);
    Boolean existsByCv_Id(Long id);
    boolean existsById(Long id);
}
