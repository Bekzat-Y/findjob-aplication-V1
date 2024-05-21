package com.findjob.findjobbackend.service.workExp;


import com.findjob.findjobbackend.model.WorkExp;
import com.findjob.findjobbackend.service.IGeneralService;
import org.springframework.stereotype.Repository;

@Repository
public interface IWorkExpService extends IGeneralService<WorkExp> {
    Iterable<WorkExp> findAllByCv_Id(Long id);
    Boolean existsByCv_Id(Long id);
}
