package com.findjob.findjobbackend.service.CV;

import com.findjob.findjobbackend.model.CV;
import com.findjob.findjobbackend.repository.ICVRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CVService implements ICVService {
    private static final Logger logger = LoggerFactory.getLogger(CVService.class);

    @Autowired
    private final ICVRepository icvRepository;

    public CVService(ICVRepository icvRepository) {
        this.icvRepository = icvRepository;
    }

    @Override
    public Iterable<CV> findAll() {
        return icvRepository.findAll();
    }

    @Override
    public Page<CV> findAll(Pageable pageable) {
        if (pageable == null) {
            logger.error("Pageable parameter is null");
            throw new IllegalArgumentException("Pageable parameter cannot be null");
        }
        return icvRepository.findAll(pageable);
    }

    @Override
    public void deleteById(Long id) {
        if (id == null) {
            logger.error("ID parameter is null or empty");
            throw new IllegalArgumentException("ID parameter cannot be null");
        }
        if (!icvRepository.existsById(id)) {
            logger.error("CV with ID {} does not exist", id);
            throw new IllegalArgumentException("CV with ID " + id + " does not exist");
        }
        icvRepository.deleteById(id);
    }

    @Override
    public CV save(CV cv) {
        if (cv == null) {
            logger.error("CV parameter is null");
            throw new IllegalArgumentException("CV parameter cannot be null");
        }
        return icvRepository.save(cv);
    }

    @Override
    public Optional<CV> findById(Long id) {
        if (id == null) {
            logger.error("ID parameter is null or ");
            throw new IllegalArgumentException("ID parameter cannot be null");
        }
        return icvRepository.findById(id);
    }

    @Override
    public Boolean existsByUserId(Long idUser) {
        if (idUser == null) {
            logger.error("UserID parameter is null");
            throw new IllegalArgumentException("UserID parameter cannot be null");
        }
        return icvRepository.existsByUserId(idUser);
    }

    @Override
    public Optional<CV> findByUserId(Long id) {
        if (id == null) {
            logger.error("ID parameter is null");
            throw new IllegalArgumentException("ID parameter cannot be null");
        }
        return icvRepository.findByUserId(id);
    }
}
