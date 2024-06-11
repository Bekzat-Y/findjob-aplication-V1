package com.findjob.findjobbackend.repository;

import com.findjob.findjobbackend.model.WorkingTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IWorkingTimeRepository extends JpaRepository<WorkingTime,Long> {
}
