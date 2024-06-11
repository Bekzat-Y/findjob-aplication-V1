package com.findjob.findjobbackend.controller;


import com.findjob.findjobbackend.dto.response.ResponseMessage;
import com.findjob.findjobbackend.model.WorkExp;
import com.findjob.findjobbackend.service.workExp.WorkExpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RequestMapping("WorkExp")
@RestController
@RequiredArgsConstructor
public class WorkExpController {
    private final   WorkExpService workExpService;

    @GetMapping("/list")
    public ResponseEntity<?> showListWorkExp() {
        List<WorkExp> workExps = (List<WorkExp>) workExpService.findAll();
        if (workExps.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(workExps, HttpStatus.OK);
    }
    @PostMapping("")
    public ResponseEntity<?> createWorkExp(@RequestBody WorkExp workExp) {
        if (workExp.getTitle() == null) {
            return new ResponseEntity<>(new ResponseMessage("no_title"), HttpStatus.BAD_REQUEST);
        }
        if (workExp.getStartDate() == null) {
            return new ResponseEntity<>(new ResponseMessage("no_start_date"), HttpStatus.BAD_REQUEST);
        }
        if (workExp.getEndDate() == null) {
            return new ResponseEntity<>(new ResponseMessage("no_end_date"), HttpStatus.BAD_REQUEST);
        }
        workExpService.save(workExp);
        return new ResponseEntity<>(new ResponseMessage("yes"), HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateWorkExp(@PathVariable Long id, @RequestBody WorkExp workExp) {
        Optional<WorkExp> workExpOptional = workExpService.findById(id);
        if (workExpOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (workExp.getTitle().isEmpty()) {
            return new ResponseEntity<>(new ResponseMessage("no_title"), HttpStatus.BAD_REQUEST);
        }
        if (workExp.getStartDate() == null) {
            return new ResponseEntity<>(new ResponseMessage("no_start_date"), HttpStatus.BAD_REQUEST);
        }
        if (workExp.getEndDate() == null) {
            return new ResponseEntity<>(new ResponseMessage("no_end_date"), HttpStatus.BAD_REQUEST);
        }

        WorkExp existingWorkExp = workExpOptional.get();
        existingWorkExp.setTitle(workExp.getTitle());
        existingWorkExp.setContent(workExp.getContent());
        existingWorkExp.setCv(workExp.getCv());
        existingWorkExp.setStartDate(workExp.getStartDate());
        existingWorkExp.setEndDate(workExp.getEndDate());

        workExpService.save(existingWorkExp);
        return new ResponseEntity<>(existingWorkExp, HttpStatus.OK);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteWorkExp(@PathVariable Long id) {
        Optional<WorkExp> workExp = workExpService.findById(id);
        if (workExp.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        workExpService.deleteById(id);
        return new ResponseEntity<>(new ResponseMessage("yes"), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detailWorkExp(@PathVariable Long id) {
        Optional<WorkExp> workExp = workExpService.findById(id);
        if (workExp.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(workExp, HttpStatus.OK);
    }

    @GetMapping("/cv/{id}")
    public ResponseEntity<?> findWorkingByCvId(@PathVariable Long id) {
        List<WorkExp> workExps = (List<WorkExp>) workExpService.findAllByCv_Id(id);
        return new ResponseEntity<>(workExps, HttpStatus.OK);
    }
}
