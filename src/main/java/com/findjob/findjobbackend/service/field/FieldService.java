package com.findjob.findjobbackend.service.field;

import com.findjob.findjobbackend.enums.Status;
import com.findjob.findjobbackend.model.Field;
import com.findjob.findjobbackend.repository.IFieldRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class FieldService implements IFieldService {
    private static final Logger logger = LoggerFactory.getLogger(FieldService.class);

    @Autowired
    private final IFieldRepository fieldRepository;

    @Override
    public Iterable<Field> findAll() {
        return fieldRepository.findAll();
    }

    @Override
    public Page<Field> findAll(Pageable pageable) {
        if (pageable == null) {
            logger.error("Pageable parameter is null");
            throw new IllegalArgumentException("Pageable parameter cannot be null");
        }
        return fieldRepository.findAll(pageable);
    }

    @Override
    public void deleteById(Long id) {
        if (id == null) {
            logger.error("ID parameter is null or empty");
            throw new IllegalArgumentException("ID parameter cannot be null");
        }
        Field field = fieldRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        field.setStatus(Status.DELETE);
        fieldRepository.save(field);
    }

    @Override
    public Field save(Field field) {
        if (field == null) {
            logger.error("Field parameter is null");
            throw new IllegalArgumentException("Field parameter cannot be null");
        }
        return fieldRepository.save(field);
    }

    @Override
    public Optional<Field> findById(Long id) {
        if (id == null) {
            logger.error("ID parameter is null");
            throw new IllegalArgumentException("ID parameter cannot be null");
        }
        return fieldRepository.findById(id);
    }


}
