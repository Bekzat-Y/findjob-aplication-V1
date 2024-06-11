package com.findjob.findjobbackend.service.recruitmentNew;

import com.findjob.findjobbackend.dto.request.SearchJob;
import com.findjob.findjobbackend.dto.response.PageResponse;
import com.findjob.findjobbackend.dto.response.RecruitmentNewDTO;
import com.findjob.findjobbackend.model.RecruitmentNew;
import com.findjob.findjobbackend.repository.IRecruitmentNewDAO;
import com.findjob.findjobbackend.repository.IRecruitmentNewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecruitmentNewService implements IRecruitmentNewService {

    private static final Logger logger = LoggerFactory.getLogger(RecruitmentNewService.class);

    private final IRecruitmentNewRepository recruitmentNewRepository;
    private final IRecruitmentNewDAO recruitmentNewDAO;

    @Override
    public Iterable<RecruitmentNew> findAll() {
        return recruitmentNewRepository.findAll();
    }

    @Override
    public Page<RecruitmentNew> findAll(Pageable pageable) {
        if (pageable == null) {
            logger.error("Pageable parameter is null");
            throw new IllegalArgumentException("Pageable parameter cannot be null");
        }
        return recruitmentNewRepository.findAll(pageable);
    }

    @Override
    public void deleteById(Long id) {
        if (id == null) {
            logger.error("ID parameter is null or empty");
            throw new IllegalArgumentException("ID parameter cannot be null");
        }
        recruitmentNewRepository.deleteById(id);
    }

    @Override
    public RecruitmentNew save(RecruitmentNew recruitmentNew) {
        if (recruitmentNew == null) {
            logger.error("RecruitmentNew parameter is null");
            throw new IllegalArgumentException("RecruitmentNew parameter cannot be null");
        }
        return recruitmentNewRepository.save(recruitmentNew);
    }

    @Override
    public Optional<RecruitmentNew> findById(Long id) {
        if (id == null) {
            logger.error("ID parameter is null");
            throw new IllegalArgumentException("ID parameter cannot be null");
        }
        return recruitmentNewRepository.findById(id);
    }

    @Override
    public List<RecruitmentNew> findAllByCompany_Id(Long id) {
        if (id == null) {
            logger.error("Company ID parameter is null");
            throw new IllegalArgumentException("Company ID parameter cannot be null");
        }
        return recruitmentNewRepository.findAllByCompany_Id(id);
    }

    @Override
    public PageResponse searchByObj(SearchJob searchJob) {
        if (searchJob == null) {
            logger.error("SearchJob parameter is null");
            throw new IllegalArgumentException("SearchJob parameter cannot be null");
        }

        List<RecruitmentNewDTO> list = recruitmentNewDAO.findJob(searchJob);
        PageResponse pageResponse = new PageResponse();
        pageResponse.setData(list);
        pageResponse.setTotalRecord(recruitmentNewRepository.countTotalRecords(
                searchJob.getTitle(),
                searchJob.getCityId(),
                searchJob.getFieldId(),
                searchJob.getCompanyId(),
                searchJob.getVacancies(),
                searchJob.getWorkingTimeId(),
                searchJob.getSalary()
        ));

        return pageResponse;
    }
}
