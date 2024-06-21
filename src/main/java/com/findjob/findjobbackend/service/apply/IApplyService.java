package com.findjob.findjobbackend.service.apply;

import com.findjob.findjobbackend.dto.response.ApplyShowAll;
import com.findjob.findjobbackend.model.Apply;
import com.findjob.findjobbackend.service.IGeneralService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

public interface IApplyService  extends IGeneralService<Apply> {
//    Page<ApplyShowAll> findAllByCompanyId(Pageable page, @Param("idCompany") Long id);

    Page<Apply> findAllByUserId(Pageable pageable, Long id);

    boolean existsByUserIdAndRecruitmentNewId(Long userID, Long recruitmentID);

}
