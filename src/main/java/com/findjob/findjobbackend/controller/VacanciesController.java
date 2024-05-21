package com.findjob.findjobbackend.controller;

import com.findjob.findjobbackend.model.Vacancies;
import com.findjob.findjobbackend.service.vacancies.IVacanciesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/vacancies")

public class VacanciesController {
    @Autowired
    private IVacanciesService vacanciesService;

    @GetMapping("/showAll")
    public ResponseEntity<Iterable<Vacancies>> showAll(){
        return new ResponseEntity<>(vacanciesService.findAll(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Vacancies> findById(@PathVariable Long id){
        Optional<Vacancies> vacanciesOptional = vacanciesService.findById(id);
        return vacanciesOptional.map(vacancies -> new ResponseEntity<>(vacancies, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/create")
    public ResponseEntity<Vacancies> createVacancies(@RequestBody Vacancies vacancies){
        return new ResponseEntity<>(vacanciesService.save(vacancies),HttpStatus.CREATED);
    }
    @PutMapping("/edit/{id}")
    public ResponseEntity<Vacancies> editVacancies(@RequestBody Vacancies vacancies , @PathVariable Long id){
        Optional<Vacancies> vacanciesOptional = vacanciesService.findById(id);
        if (vacanciesOptional.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
           if (vacancies.getId() != null) {
               vacancies.setId(id);
           }
           return new ResponseEntity<>(vacanciesService.save(vacancies),HttpStatus.OK);
        }

    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Vacancies> deleteVacancies(@PathVariable Long id){
        Optional<Vacancies> vacanciesOptional = vacanciesService.findById(id);
        if (vacanciesOptional.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        vacanciesService.deleteById(id);
        return new ResponseEntity<>(vacanciesOptional.get(),HttpStatus.OK);
    }
}
