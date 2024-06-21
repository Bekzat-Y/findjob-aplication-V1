package com.findjob.findjobbackend.service.recruitmentNew;

import com.findjob.findjobbackend.dto.request.SearchJob;
import com.findjob.findjobbackend.dto.response.PageResponse;
import com.findjob.findjobbackend.dto.response.RecruitmentNewDTO;
import com.findjob.findjobbackend.dto.response.ResponseMessage;
import com.findjob.findjobbackend.enums.Status;
import com.findjob.findjobbackend.model.RecruitmentNew;
import com.findjob.findjobbackend.repository.IRecruitmentNewDAO;
import com.findjob.findjobbackend.repository.IRecruitmentNewRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecruitmentNewService implements IRecruitmentNewService {

    private static final Logger logger = LoggerFactory.getLogger(RecruitmentNewService.class);

    private final IRecruitmentNewRepository recruitmentNewRepository;
    @Qualifier("IRecruitmentNewDAO")
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
        RecruitmentNew recruitmentNew = recruitmentNewRepository.findById(id).orElse(null);
        if (recruitmentNew != null) {
            recruitmentNew.setRecruitStatus(Status.DELETE);
            recruitmentNewRepository.save(recruitmentNew);
        }
        }

    @Override
    public RecruitmentNew save(RecruitmentNew recruitmentNew) {
        if (recruitmentNew == null) {
            logger.error("RecruitmentNew parameter is null");
            throw new IllegalArgumentException("RecruitmentNew parameter cannot be null");
        }
        else
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
    public ResponseMessage updateRecruitmentNew(Long id, RecruitmentNew recruitmentNew) {
        Optional<RecruitmentNew> recruitmentNewOptional = findById(id);
        if (recruitmentNewOptional.isEmpty()) {
            return new ResponseMessage("not_found");
        }

        RecruitmentNew existingRecruitmentNew = recruitmentNewOptional.get();

        if (recruitmentNew.getQuantity() == null) {
            return new ResponseMessage("no_quantity");
        }

        if (recruitmentNew.getSalary() == null) {
            return new ResponseMessage("no_salary");
        }

        existingRecruitmentNew.setTitle(recruitmentNew.getTitle());
        existingRecruitmentNew.setWorkingTime(recruitmentNew.getWorkingTime());
        existingRecruitmentNew.setField(recruitmentNew.getField());
        existingRecruitmentNew.setVacancies(recruitmentNew.getVacancies());
        existingRecruitmentNew.setExpDate(recruitmentNew.getExpDate());
        existingRecruitmentNew.setDescription(recruitmentNew.getDescription());
        existingRecruitmentNew.setQuantity(recruitmentNew.getQuantity());
        existingRecruitmentNew.setSalary(recruitmentNew.getSalary());
        existingRecruitmentNew.setGender(recruitmentNew.getGender());
        existingRecruitmentNew.setCity(recruitmentNew.getCity());

        save(existingRecruitmentNew);

        return new ResponseMessage("yes");
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
