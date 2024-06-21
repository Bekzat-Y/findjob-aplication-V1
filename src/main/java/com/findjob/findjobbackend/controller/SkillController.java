package com.findjob.findjobbackend.controller;


import com.findjob.findjobbackend.dto.response.ResponseMessage;
import com.findjob.findjobbackend.model.Skill;
import com.findjob.findjobbackend.service.skill.SkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RequestMapping("Skill")
@RestController
@RequiredArgsConstructor
public class SkillController {

   private final SkillService skillService;

    @GetMapping("/showAll")
    public ResponseEntity<?> showAll() {
        return new ResponseEntity<>(skillService.findAll(), HttpStatus.OK);
    }


    @PostMapping("")
    public ResponseEntity<?> createSkill(@RequestBody Skill skill){
        if(skill.getName()==null){
            return new ResponseEntity<>(new ResponseMessage("no_name_skill"), HttpStatus.OK);
        }
        skillService.save(skill);
        return new ResponseEntity<>(new ResponseMessage("yes"), HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateSkill(@PathVariable Long id, @RequestBody Skill skill){
        Optional<Skill> skill1 = skillService.findById(id);
        if(skill1.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if(skill.getName()==null){
            return new ResponseEntity<>(new ResponseMessage("no_name_skill"), HttpStatus.OK);
        }
        skillService.save(skill);
        return new ResponseEntity<>(skill1.get(), HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getSkillsById(@PathVariable Long id) {
        Optional<Skill> skill = skillService.findById(id);
        if (skill.isPresent()) {
            return new ResponseEntity<>(skill.get(), HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @GetMapping("/delete/{id}")
    public ResponseEntity<?> deleteCV(@PathVariable Long id) {
        Optional<Skill> skill = skillService.findById(id);
        if (skill.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        skillService.deleteById(id);
        return new ResponseEntity<>(new ResponseMessage("yes"), HttpStatus.OK);
    }

}
