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
import org.springframework.transaction.annotation.Transactional;
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

    @GetMapping("delete/{id}")
    public ResponseEntity<?> deleteCV(@PathVariable Long id) {
        Optional<CV> cv = cvService.findById(id);
        if (cv.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        cvService.deleteById(id);
        return new ResponseEntity<>(new ResponseMessage("yes"), HttpStatus.OK);
    }
    @PutMapping("/{id}")
    @Transactional
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

        // Управление навыками
        List<Skill> existingSkills = cv.getSkills();
        List<SkillDTO> updatedSkillsDTO = cvDTO.getSkills();

        // Удаляем навыки, которые больше не существуют
        existingSkills.removeIf(existingSkill -> {
            boolean exists = updatedSkillsDTO.stream().anyMatch(updatedSkillDTO -> updatedSkillDTO.getId().equals(existingSkill.getId()));
            if (!exists) {
                skillService.delete(existingSkill);
            }
            return !exists;
        });

        // Обновляем или добавляем навыки
        for (SkillDTO skillDTO : updatedSkillsDTO) {
            Skill skill = skillService.findById(skillDTO.getId()).orElse(new Skill());
            skill.setCv(cv);
            skill.setName(skillDTO.getName());
            skill.setProficiency(skillDTO.getProficiency());
            skillService.save(skill);
            if (!existingSkills.contains(skill)) {
                existingSkills.add(skill);
            }
        }

        // Управление опытом работы
        List<WorkExp> existingWorkExps = cv.getWorkExps();
        List<WorkExpDTO> updatedWorkExpsDTO = cvDTO.getWorkExps();

        // Удаляем опыт работы, который больше не существует
        existingWorkExps.removeIf(existingWorkExp -> {
            boolean exists = updatedWorkExpsDTO.stream().anyMatch(updatedWorkExpDTO -> updatedWorkExpDTO.getId().equals(existingWorkExp.getId()));
            if (!exists) {
                workExpService.delete(existingWorkExp);
            }
            return !exists;
        });

        // Обновляем или добавляем опыт работы
        for (WorkExpDTO workExpDTO : updatedWorkExpsDTO) {
            WorkExp workExp = workExpService.findById(workExpDTO.getId()).orElse(new WorkExp());
            workExp.setCv(cv);
            workExp.setTitle(workExpDTO.getTitle());
            workExp.setContent(workExpDTO.getContent());
            workExp.setStartDate(workExpDTO.getStartDate());
            workExp.setEndDate(workExpDTO.getEndDate());
            workExpService.save(workExp);
            if (!existingWorkExps.contains(workExp)) {
                existingWorkExps.add(workExp);
            }
        }

        cv.setSkills(existingSkills);
        cv.setWorkExps(existingWorkExps);
        cvService.save(cv);

        CvDTO cvDTO1 = cv.toDto(cv);
        return new ResponseEntity<>(cvDTO1, HttpStatus.OK);
    }

    @PostMapping("/createCV")
    public ResponseEntity<?> create(@RequestBody CvDTO cvDTO) {
        if (cvDTO.getUserId()==null){
            return new ResponseEntity<>(new ResponseMessage("User not found"), HttpStatus.NOT_FOUND);
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

