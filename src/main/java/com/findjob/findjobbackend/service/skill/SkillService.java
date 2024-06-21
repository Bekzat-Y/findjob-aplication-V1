package com.findjob.findjobbackend.service.skill;

import com.findjob.findjobbackend.enums.Status;
import com.findjob.findjobbackend.model.Skill;
import com.findjob.findjobbackend.repository.ISkillRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class SkillService implements ISkillService {

    private static final Logger logger = LoggerFactory.getLogger(SkillService.class);

    @Autowired
    private final ISkillRepository skillRepository;

    @Override
    public Iterable<Skill> findAll() {
        return skillRepository.findAll();
    }

    @Override
    public Page<Skill> findAll(Pageable pageable) {
        if (pageable == null) {
            logger.error("Pageable parameter is null");
            throw new IllegalArgumentException("Pageable parameter cannot be null");
        }
        return skillRepository.findAll(pageable);
    }

    @Override
    public void deleteById(Long id) {
        if (id == null) {
            logger.error("ID parameter is null or empty");
            throw new IllegalArgumentException("ID parameter cannot be null");
        }
        skillRepository.findById(id).ifPresent(skill -> {
            skill.setStatus(Status.DELETE);
            skillRepository.save(skill);
            logger.info("Skill with id {} was deleted", id);
        });
    }

    public void delete(Skill skill) {
        skillRepository.delete(skill);
    }

    @Override
    public Skill save(Skill skill) {
        if (skill == null) {
            logger.error("Skill parameter is null");
            throw new IllegalArgumentException("Skill parameter cannot be null");
        }
        return skillRepository.save(skill);
    }

    @Override
    public Optional<Skill> findById(Long id) {
        if (id == null) {
            logger.error("ID parameter is null");
            throw new IllegalArgumentException("ID parameter cannot be null");
        }else {
            return skillRepository.findById(id);

        }
    }

    @Override
    public Boolean existsByCv_Id(Long id) {
        if (id == null) {
            logger.error("CV ID parameter is null ");
            throw new IllegalArgumentException("CV ID parameter cannot be null");
        }
        return skillRepository.existsByCv_Id(id);
    }



    @Override
    public boolean existsById(Long id) {
        if (id == null) {
            logger.error("ID parameter is null or empty ");
            throw new IllegalArgumentException("ID parameter cannot be null");
        }
        return skillRepository.existsById(id);
    }

    @Override
    public Optional<Skill> updateSkill(Skill skill) {
        if (skill != null) {
            Optional<Skill> optionalSkill = skillRepository.findById(skill.getId());
            if (optionalSkill.isPresent()) {
                skill.setName(skill.getName());
                skill.setProficiency(skill.getProficiency());
                skill.setCv(skill.getCv());
                logger.info("Updating skill");
            } else {
                logger.error("Skill not found");
                throw new IllegalArgumentException("Skill not found");

            }
        }
    return Optional.ofNullable(skill);}
}