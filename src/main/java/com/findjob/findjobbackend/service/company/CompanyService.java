package com.findjob.findjobbackend.service.company;

import com.findjob.findjobbackend.dto.response.CompanyRecruitmentNeed;
import com.findjob.findjobbackend.enums.Status;
import com.findjob.findjobbackend.model.Company;
import com.findjob.findjobbackend.repository.ICompanyRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class CompanyService implements ICompanyService {
    private static final Logger logger = LoggerFactory.getLogger(CompanyService.class);
    private final ICompanyRepository iCompanyRepository;

    @Override
    public Iterable<Company> findAll() {
        return iCompanyRepository.findAll();
    }

    @Override
    public Page<Company> findAll(Pageable pageable) {
        if (pageable == null) {
            logger.error("Pageable parameter is null");
            throw new IllegalArgumentException("Pageable parameter cannot be null");
        }
        return iCompanyRepository.findAll(pageable);
    }

    @Override
    public void deleteById(Long id) {
        if (id == null) {
            logger.error("ID parameter is null or empty");
            throw new IllegalArgumentException("ID parameter cannot be null");
        }
        if (!iCompanyRepository.existsById(id)) {
            logger.error("Company with ID {} does not exist", id);
            throw new IllegalArgumentException("Company with ID " + id + " does not exist");
        }
        try {
            Company company = iCompanyRepository.findById(id).orElse(null);
            if (company != null ) {
                company.setStatusCompany(Status.DELETE);
                iCompanyRepository.save(company);
            }
        }catch (NullPointerException e){
            logger.error("{}Company with ID {} does not exist", e, id);
        }

    }

    @Override
    public Company save(Company company) {
        if (company == null) {
            logger.error("Company parameter is null");
            throw new IllegalArgumentException("Company parameter cannot be null");
        }
        return iCompanyRepository.save(company);
    }

    @Override
    public Optional<Company> findById(Long id) {
        if (id == null) {
            logger.error("ID parameter is null");
            throw new IllegalArgumentException("ID parameter cannot be null");
        }
        return iCompanyRepository.findById(id);
    }

    @Override
    public Optional<Company> findAllByAccount_Id(Long id) {
        if (id == null) {
            logger.error("ID parameter is null ");
            throw new IllegalArgumentException("ID parameter cannot be null");
        }
        return iCompanyRepository.findAllByAccount_Id(id);
    }

    @Override
    public Boolean existsByName(String name) {
        if (name == null || name.isEmpty()) {
            logger.error("Name parameter is null or empty");
            throw new IllegalArgumentException("Name parameter cannot be null or empty");
        }
        return iCompanyRepository.existsByName(name);
    }

    @Override
    public List<Company> findCompanyByStatus(Integer stt) {
        if (stt == null) {
            logger.error("Status parameter is null");
            throw new IllegalArgumentException("Status parameter cannot be null");
        }
        return iCompanyRepository.findCompanyByStatus(stt);
    }

    @Override
    public List<CompanyRecruitmentNeed> findCompanyByRecuitmentNew() {
        return iCompanyRepository.findCompanyByRecruitmentNews();
    }
}
