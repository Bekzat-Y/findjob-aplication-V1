package com.findjob.findjobbackend.service.apply;

import com.findjob.findjobbackend.dto.response.ApplyShowAll;
import com.findjob.findjobbackend.model.Apply;
import com.findjob.findjobbackend.repository.IApplyRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@AllArgsConstructor
public class ApplyService implements IApplyService {


    private final Logger logger = LoggerFactory.getLogger(ApplyService.class);
    private final IApplyRepository applyRepository;



    @Override
    public Iterable<Apply> findAll() {
        return applyRepository.findAll();
    }

    @Override
    public Page<Apply> findAll(Pageable pageable) {
        if (pageable == null) {
            logger.error("Pageable parameter is null");
            throw new IllegalArgumentException("Pageable parameter cannot be null");
        }
        return applyRepository.findAll(pageable);
    }

    @Override
    public void deleteById(Long id) {
        if (id == null) {
            logger.error("ID parameter is null");
            throw new IllegalArgumentException("ID parameter cannot be null");
        }
        if (!applyRepository.existsById(id)) {
            logger.error("Applys with ID {} does not exist", id);
            throw new IllegalArgumentException("Applys with ID " + id + " does not exist");
        }
        applyRepository.deleteById(id);
    }

    @Override
    public Apply save(Apply Apply) {
        if (Apply == null) {
            logger.error("Applys parameter is null");
            throw new IllegalArgumentException("Applys parameter cannot be null");
        }
        return applyRepository.save(Apply);
    }

    @Override
    public Optional<Apply> findById(Long id) {
        if (id == null) {
            logger.error("ID parameter is null ");
            throw new IllegalArgumentException("ID parameter cannot be null");
        }
        return applyRepository.findById(id);
    }

    @Override
    public Page<ApplyShowAll> findAllByCompanyId(Pageable page, Long id) {
        if (page == null || id == null) {
            logger.error("Page or ID parameter is null");
            throw new IllegalArgumentException("Page and ID parameters cannot be null");
        }
        return applyRepository.findAllByCompanyId(page, id);
    }

    @Override
    public Page<Apply> findAllByUserId(Pageable pageable, Long id) {
        if (pageable == null || id == null) {
            logger.error("Pageable or ID parameter is null");
            throw new IllegalArgumentException("Pageable and ID parameters cannot be null");
        }
        return applyRepository.findAllByUser_Id(pageable, id);
    }

    @Override
    public boolean existsByUserIdAndRecruitmentNewId(Long userID, Long recruitmentID) {
        if (userID == null || recruitmentID == null) {
            logger.error("UserID or RecruitmentID parameter is null");
            throw new IllegalArgumentException("UserID and RecruitmentID parameters cannot be null");
        }
        return applyRepository.existsByUserIdAndRecruitmentNewId(userID, recruitmentID);
    }
}

