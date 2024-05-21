package com.findjob.findjobbackend.repository;

import com.findjob.findjobbackend.model.WorkExp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IWorkExpRepository extends JpaRepository<WorkExp, Long> {
    Iterable<WorkExp> findAllByCv_Id(Long id);
    Boolean existsByCv_Id(Long id);
}
