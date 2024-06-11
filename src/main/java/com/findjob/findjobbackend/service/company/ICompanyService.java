package com.findjob.findjobbackend.service.company;


import com.findjob.findjobbackend.dto.response.CompanyRecruitmentNeed;
import com.findjob.findjobbackend.model.Company;
import com.findjob.findjobbackend.service.IGeneralService;

import java.util.List;
import java.util.Optional;

public interface ICompanyService extends IGeneralService<Company> {
    Optional<Company> findAllByAccount_Id(Long id);
    Boolean existsByName(String name);
    List<Company> findCompanyByStatus(Integer stt);
    List<CompanyRecruitmentNeed> findCompanyByRecuitmentNew();

}
