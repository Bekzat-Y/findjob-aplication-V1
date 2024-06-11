package com.findjob.findjobbackend.repository;

import com.findjob.findjobbackend.model.CV;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICVRepository extends JpaRepository<CV,Long> {
    Boolean existsByUserId(Long idUser);
    Optional<CV> findByUserId(Long id);
}
