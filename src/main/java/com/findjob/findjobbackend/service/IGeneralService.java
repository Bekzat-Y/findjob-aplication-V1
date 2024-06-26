package com.findjob.findjobbackend.service;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IGeneralService<T> {
    Iterable<T> findAll();

    Page<T> findAll(Pageable pageable);

    void deleteById(Long id) throws ChangeSetPersister.NotFoundException;

    T save(T t);

    Optional<T> findById(Long id);
}
