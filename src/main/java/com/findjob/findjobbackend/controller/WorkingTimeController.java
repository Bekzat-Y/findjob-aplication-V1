package com.findjob.findjobbackend.controller;

import com.findjob.findjobbackend.model.WorkingTime;
import com.findjob.findjobbackend.service.workingTime.IWorkingTimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/workingTime")
@RequiredArgsConstructor
public class WorkingTimeController {
    private final IWorkingTimeService workingTimeService;

    @GetMapping("/showAll")
    public ResponseEntity<Iterable<WorkingTime>> showAll(){
        return new ResponseEntity<>(workingTimeService.findAll(), HttpStatus.OK);
    }
    @PostMapping("/create")
    public ResponseEntity<WorkingTime> createWorkingTime(@RequestBody WorkingTime workingTime){
        return new ResponseEntity<>(workingTimeService.save(workingTime),HttpStatus.CREATED);
    }
}
