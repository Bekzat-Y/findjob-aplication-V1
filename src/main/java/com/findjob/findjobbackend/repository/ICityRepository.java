package com.findjob.findjobbackend.repository;

import com.findjob.findjobbackend.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ICityRepository extends JpaRepository<City, Long> {

    void deleteCitiesById(Long id);
}
