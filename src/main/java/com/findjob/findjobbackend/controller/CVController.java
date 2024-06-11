package com.findjob.findjobbackend.controller;


import com.findjob.findjobbackend.dto.request.CvDTO;
import com.findjob.findjobbackend.dto.request.SkillDTO;
import com.findjob.findjobbackend.dto.request.WorkExpDTO;
import com.findjob.findjobbackend.dto.response.ResponseMessage;
import com.findjob.findjobbackend.model.CV;
import com.findjob.findjobbackend.model.Skill;
import com.findjob.findjobbackend.model.WorkExp;
import com.findjob.findjobbackend.service.CV.CVService;
import com.findjob.findjobbackend.service.skill.SkillService;
import com.findjob.findjobbackend.service.user.UserService;
import com.findjob.findjobbackend.service.workExp.WorkExpService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RequestMapping("CV")
@RestController
@AllArgsConstructor
public class CVController {

   private final CVService cvService;


   private final SkillService skillService;


    private final WorkExpService workExpService;


    private final UserService userService;

    @GetMapping("/showAll")
    public ResponseEntity<?> showAll() {
        return new ResponseEntity<>(cvService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detailCV(@PathVariable Long id) {
        Optional<CV> cv = cvService.findById(id);
        if (cv.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(cv, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCV(@PathVariable Long id) {
        Optional<CV> cv = cvService.findById(id);
        if (cv.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        cvService.deleteById(id);
        return new ResponseEntity<>(new ResponseMessage("yes"), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCvByUserId(@PathVariable Long id, @RequestBody CvDTO cvDTO) {
        Optional<CV> cvOptional = cvService.findByUserId(id);
        if (cvOptional.isEmpty()) {
            return new ResponseEntity<>(new ResponseMessage("CV not found"), HttpStatus.NOT_FOUND);
        }

        CV cv = cvOptional.get();
        cv.setUser(userService.findById(cvDTO.getUserId()).orElseThrow(() -> new RuntimeException("User not found")));
        cv.setExpYear(cvDTO.getExpYear());
        cv.setFileCV(cvDTO.getFileCV());
        cv.setSalaryExpectation(cvDTO.getSalaryExpectation());

        List<Skill> skills = new ArrayList<>();
        for (SkillDTO skillDTO : cvDTO.getSkills()) {
            Skill skill = skillService.findById(skillDTO.getId()).orElse(new Skill());
            skill.setCv(cv);
            skill.setName(skillDTO.getName());
            skill.setProficiency(skillDTO.getProficiency());
            skillService.save(skill);
            skills.add(skill);
        }

        List<WorkExp> workExps = new ArrayList<>();
        for (WorkExpDTO workExpDTO : cvDTO.getWorkExps()) {
            WorkExp workExp = workExpService.findById(workExpDTO.getId()).orElse(new WorkExp());
            workExp.setCv(cv);
            workExp.setTitle(workExpDTO.getTitle());
            workExp.setContent(workExpDTO.getContent());
            workExp.setStartDate(workExpDTO.getStartDate());
            workExp.setEndDate(workExpDTO.getEndDate());
            workExpService.save(workExp);
            workExps.add(workExp);
        }

        cv.setSkills(skills);
        cv.setWorkExps(workExps);
        cvService.save(cv);

        CvDTO cvDTO1 = cv.toDto(cv);
        return new ResponseEntity<>(cvDTO1, HttpStatus.OK);
    }


    @PostMapping("/createCV")
    public ResponseEntity<?> create(@RequestBody CvDTO cvDTO) {
        if (cvService.existsByUserId(cvDTO.getUserId())) {
            return new ResponseEntity<>(new ResponseMessage("user_da_ton_tai"), HttpStatus.OK);
        }

        CV cv = new CV();
        cv = cv.toEntity(cvDTO);

        cv.setUser(userService.findById(cvDTO.getUserId()).orElseThrow(() -> new RuntimeException("User not found")));

        CV savedCv = cvService.save(cv);

        List<Skill> skills = new ArrayList<>();
        for (SkillDTO skillDTO : cvDTO.getSkills()) {
            Skill skill = new Skill();
            skill = skill.toEntity(skillDTO);
            skill.setCv(savedCv);
            skillService.save(skill);
            skills.add(skill);
        }

        List<WorkExp> workExps = new ArrayList<>();
        for (WorkExpDTO workExpDTO : cvDTO.getWorkExps()) {
            WorkExp workExp = new WorkExp();
            workExp = workExp.toEntity(workExpDTO);
            workExp.setCv(savedCv);
            workExpService.save(workExp);
            workExps.add(workExp);
        }

        savedCv.setSkills(skills);
        savedCv.setWorkExps(workExps);
        cvService.save(savedCv);

        CvDTO cvDTOResponse = savedCv.toDto(savedCv);
        return new ResponseEntity<>(cvDTOResponse, HttpStatus.OK);
    }



    @GetMapping("/user/{id}")
    public ResponseEntity<?> findByUserIdToDto(@PathVariable Long id) {
        Optional<CV> cv = cvService.findByUserId(id);
        if (cv.isPresent()) {
            return new ResponseEntity<>(cv.get().toDto(cv.get()), HttpStatus.OK);
        }
        return new ResponseEntity<>(cv, HttpStatus.OK);
    }

    @GetMapping("/userId/{id}")
    public ResponseEntity<?> findByUserId(@PathVariable Long id) {
        Optional<CV> cv = cvService.findByUserId(id);
        return new ResponseEntity<>(cv, HttpStatus.OK);
    }
}
