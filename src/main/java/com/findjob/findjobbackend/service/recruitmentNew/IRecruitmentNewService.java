package com.findjob.findjobbackend.service.recruitmentNew;


import com.findjob.findjobbackend.dto.request.SearchJob;
import com.findjob.findjobbackend.dto.response.PageResponse;
import com.findjob.findjobbackend.dto.response.ResponseMessage;
import com.findjob.findjobbackend.model.RecruitmentNew;
import com.findjob.findjobbackend.service.IGeneralService;

import java.util.List;

public interface IRecruitmentNewService extends IGeneralService<RecruitmentNew> {
    List<RecruitmentNew> findAllByCompany_Id(Long id);

    PageResponse searchByObj(SearchJob searchJob);

     ResponseMessage updateRecruitmentNew(Long id, RecruitmentNew recruitmentNew);

}


