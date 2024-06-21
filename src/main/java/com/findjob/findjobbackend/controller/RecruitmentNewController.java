package com.findjob.findjobbackend.controller;



import com.findjob.findjobbackend.dto.request.SearchJob;
import com.findjob.findjobbackend.dto.response.ResponseMessage;
import com.findjob.findjobbackend.model.RecruitmentNew;
import com.findjob.findjobbackend.service.recruitmentNew.RecruitmentNewService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("recruitment")
@AllArgsConstructor
public class RecruitmentNewController {
   private final RecruitmentNewService recruitmentNewService;

    @GetMapping("/list")
    public ResponseEntity<?> showListRecruitmentNew() {
        List<RecruitmentNew> recruitmentNewList = (List<RecruitmentNew>) recruitmentNewService.findAll();
        if (recruitmentNewList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(recruitmentNewList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createRecruitmentNew(@RequestBody RecruitmentNew recruitmentNew) {
        if (recruitmentNew.getQuantity() == null) {
            return new ResponseEntity<>(new ResponseMessage("no_quantity"), HttpStatus.OK);
        }
        recruitmentNew.setStatus(true);

        recruitmentNewService.save(recruitmentNew);
        return new ResponseEntity<>(new ResponseMessage("yes"), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detailRecruitmentNew(@PathVariable Long id) {
        Optional<RecruitmentNew> recuitmentNew = recruitmentNewService.findById(id);
        if (recuitmentNew.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(recuitmentNew, HttpStatus.OK);
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<?> deleteRecruitmentNew(@PathVariable Long id) {
        Optional<RecruitmentNew> recruitmentNew = recruitmentNewService.findById(id);
        if (recruitmentNew.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        recruitmentNewService.deleteById(id);
        return new ResponseEntity<>(new ResponseMessage("yes"), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRecruitmentNew(@PathVariable Long id, @RequestBody RecruitmentNew recruitmentNew) {
        ResponseMessage responseMessage = recruitmentNewService.updateRecruitmentNew(id, recruitmentNew);

        if ("not_found".equals(responseMessage.getMessage())) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    @GetMapping("/showAll/{id}")
    public ResponseEntity<?> findAllByCompany(@PathVariable Long id) {
        return new ResponseEntity<>(recruitmentNewService.findAllByCompany_Id(id), HttpStatus.OK);
    }


    @PutMapping("/editStatus/{id}")
    public ResponseEntity<?> editStatus(@PathVariable Long id) {
        Optional<RecruitmentNew> recruitmentNewOptional = recruitmentNewService.findById(id);
        if (recruitmentNewOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        RecruitmentNew recruitmentNew = recruitmentNewOptional.get();
        recruitmentNew.setStatus(!recruitmentNew.getStatus());

        recruitmentNewService.save(recruitmentNew);
        return new ResponseEntity<>(new ResponseMessage("yes"), HttpStatus.OK);
    }



    @GetMapping("/showPageRecruitmentNew")
    public ResponseEntity<?> showPageRecruitmentNew(@PageableDefault(sort = "title", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<RecruitmentNew> list = recruitmentNewService.findAll(pageable);
        if (list.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/showRecruitmentNewest")
    public ResponseEntity<?> showRecruitmentNewest(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<RecruitmentNew> list = recruitmentNewService.findAll(pageable);
        if (list.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("/findByObj")
    public ResponseEntity<?> findByObj(@RequestBody SearchJob searchJob) {
        return new ResponseEntity<>(recruitmentNewService.searchByObj(searchJob), HttpStatus.OK);
    }
}
