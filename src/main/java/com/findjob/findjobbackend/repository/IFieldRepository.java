package com.findjob.findjobbackend.repository;

import com.findjob.findjobbackend.model.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IFieldRepository extends JpaRepository<Field,Long> {
}
