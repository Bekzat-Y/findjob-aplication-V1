package com.findjob.findjobbackend.service.skill;

import com.findjob.findjobbackend.model.Skill;
import com.findjob.findjobbackend.repository.ISkillRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        skillRepository.deleteById(id);
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
        }
        return skillRepository.findById(id);
    }

    @Override
    public Iterable<Skill> findAllSkillsByCvId(Long id) {
        if (id == null) {
            logger.error("CV ID parameter is null");
            throw new IllegalArgumentException("CV ID parameter cannot be null");
        }
        return skillRepository.findAllByCv_Id(id);
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
}