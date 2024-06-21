package com.findjob.findjobbackend.service.workExp;

import com.findjob.findjobbackend.model.WorkExp;
import com.findjob.findjobbackend.repository.IWorkExpRepository;
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
public class WorkExpService implements IWorkExpService {

    private static final Logger logger = LoggerFactory.getLogger(WorkExpService.class);

    private final IWorkExpRepository workExpRepository;

    @Override
    public Iterable<WorkExp> findAll() {
        return workExpRepository.findAll();
    }

    @Override
    public Page<WorkExp> findAll(Pageable pageable) {
        if (pageable == null) {
            logger.error("Pageable parameter is null");
            throw new IllegalArgumentException("Pageable parameter cannot be null");
        }
        return workExpRepository.findAll(pageable);
    }

    public void delete(WorkExp workExp) {
        workExpRepository.delete(workExp);
    }

    @Override
    public void deleteById(Long id) {
        if (id == null) {
            logger.error("ID parameter is null");
            throw new IllegalArgumentException("ID parameter cannot be null");
        }
        workExpRepository.deleteById(id);
    }

    @Override
    public WorkExp save(WorkExp workExp) {
        if (workExp == null) {
            logger.error("WorkExp parameter is null");
            throw new IllegalArgumentException("WorkExp parameter cannot be null");
        }
        return workExpRepository.save(workExp);
    }

    @Override
    public Optional<WorkExp> findById(Long id) {
        if (id == null) {
            logger.error("ID parameter is null or empty");
            throw new IllegalArgumentException("ID parameter cannot be null");
        }
        return workExpRepository.findById(id);
    }

    @Override
    public Iterable<WorkExp> findAllByCv_Id(Long id) {
        if (id == null) {
            logger.error("CV ID parameter is null ");
            throw new IllegalArgumentException("CV ID parameter cannot be null");
        }
        return workExpRepository.findAllByCv_Id(id);
    }

    @Override
    public Boolean existsByCv_Id(Long id) {
        if (id == null) {
            logger.error("CV ID parameter is null");
            throw new IllegalArgumentException("CV ID parameter cannot be null");
        }
        return workExpRepository.existsByCv_Id(id);
    }
}
