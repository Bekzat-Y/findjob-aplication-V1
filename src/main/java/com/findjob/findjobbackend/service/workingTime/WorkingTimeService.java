package com.findjob.findjobbackend.service.workingTime;

import com.findjob.findjobbackend.model.WorkingTime;
import com.findjob.findjobbackend.repository.IWorkingTimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class WorkingTimeService implements IWorkingTimeService {

    private static final Logger logger = LoggerFactory.getLogger(WorkingTimeService.class);

    private final IWorkingTimeRepository workingTimeRepository;

    @Override
    public Iterable<WorkingTime> findAll() {
        return workingTimeRepository.findAll();
    }

    @Override
    public Page<WorkingTime> findAll(Pageable pageable) {
        if (pageable == null) {
            logger.error("Pageable parameter is null");
            throw new IllegalArgumentException("Pageable parameter cannot be null");
        }
        return workingTimeRepository.findAll(pageable);
    }

    @Override
    public void deleteById(Long id) {
        if (id == null) {
            logger.error("ID parameter is null");
            throw new IllegalArgumentException("ID parameter cannot be null");
        }
        workingTimeRepository.deleteById(id);
    }

    @Override
    public WorkingTime save(WorkingTime workingTime) {
        if (workingTime == null) {
            logger.error("WorkingTime parameter is null");
            throw new IllegalArgumentException("WorkingTime parameter cannot be null");
        }
        return workingTimeRepository.save(workingTime);
    }

    @Override
    public Optional<WorkingTime> findById(Long id) {
        if (id == null) {
            logger.error("ID parameter is null ");
            throw new IllegalArgumentException("ID parameter cannot be null");
        }
        return workingTimeRepository.findById(id);
    }
}
