package com.findjob.findjobbackend.repository;

import com.findjob.findjobbackend.model.Vacancies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IVacanciesRepository extends JpaRepository<Vacancies,Long> {
}
