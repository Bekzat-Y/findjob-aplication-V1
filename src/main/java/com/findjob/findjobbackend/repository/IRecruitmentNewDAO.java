package com.findjob.findjobbackend.repository;

import com.findjob.findjobbackend.dto.request.SearchJob;
import com.findjob.findjobbackend.dto.response.RecruitmentNewDTO;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface IRecruitmentNewDAO {
    List<RecruitmentNewDTO> findJob(SearchJob searchJob);
}
