package com.findjob.findjobbackend.controller;

import com.findjob.findjobbackend.dto.request.ApplyJobDTO;
import com.findjob.findjobbackend.dto.response.ResponseMessage;
import com.findjob.findjobbackend.enums.Status;
import com.findjob.findjobbackend.model.Apply;
import com.findjob.findjobbackend.model.ChangeStatusApply;
import com.findjob.findjobbackend.model.RecruitmentNew;
import com.findjob.findjobbackend.model.User;
import com.findjob.findjobbackend.service.apply.IApplyService;
import com.findjob.findjobbackend.service.recruitmentNew.IRecruitmentNewService;
import com.findjob.findjobbackend.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/applies")
public class ApplyController {
    private final IApplyService applyService;
    private final IRecruitmentNewService recruitmentNewService;
    private final IUserService userService;

    @PostMapping
    public ResponseEntity<?> createApply(@RequestBody ApplyJobDTO applyJobDTO) {
        if (Objects.isNull(applyJobDTO)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }else {
            LocalDate now = LocalDate.now();
            RecruitmentNew recruitmentNew = recruitmentNewService.findById(applyJobDTO.getRecruitmentNewId())
                    .orElseThrow(() -> new RuntimeException("RecruitmentNew not found"));
            User user = userService.findByAccount_Id(applyJobDTO.getAccountId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            Apply Apply = new Apply();
            Apply.setDate(now);
            Apply.setStatus(Status.WAIT);
            Apply.setUser(user);
            Apply.setRecruitmentNew(recruitmentNew);
            applyService.save(Apply);
            return new ResponseEntity<>(new ResponseMessage("CREATE"), HttpStatus.CREATED);
        }

    }

//    @GetMapping("/findAllByCompanyID/{id}")
//    public ResponseEntity<?> findAllByCompany(@PageableDefault(direction = Sort.Direction.DESC) Pageable pageable, @PathVariable Long id) {
//        Page<ApplyShowAll> list = applyService.findAllByCompanyId(pageable, id);
//        if (list.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }
//        return new ResponseEntity<>(list, HttpStatus.OK);
//    }

    @GetMapping("/showAllApply/{id}")
    public ResponseEntity<?> showAllApplyById(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable, @PathVariable Long id) {
        Page<Apply> list = applyService.findAllByUserId(pageable, id);
        if (list.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("/changeStatusApply")
    public ResponseEntity<?> changeStatusApply(@RequestBody ChangeStatusApply changeStatusApply) {
        Apply Apply = applyService.findById(changeStatusApply.getApplyId())
                .orElseThrow(() -> new RuntimeException("Applys not found"));

        boolean check = false;
        List<Apply> ApplyList = (List<Apply>) applyService.findAll();
        for (Apply Apply1 : ApplyList) {
            if (Objects.equals(Apply1.getUser().getId(), Apply.getUser().getId()) &&
                    Objects.equals(Apply1.getRecruitmentNew().getCompany().getId(), Apply.getRecruitmentNew().getCompany().getId())) {
                String status = String.valueOf(Apply1.getStatus());
                if (status.equals("ACCEPT")) {
                    check = true;
                }
            }
        }
        if (changeStatusApply.getStatus() == 1) {
            if (check) {
                return new ResponseEntity<>(new ResponseMessage("Сотрудники были приняты на работу в другие компании"), HttpStatus.OK);
            }
            Apply.setStatus(Status.ACCEPT);
            applyService.save(Apply);
            return new ResponseEntity<>(new ResponseMessage("Сотрудники были успешно приняты "), HttpStatus.OK);
        }

        if (changeStatusApply.getStatus() == 0) {
            Apply.setStatus(Status.REJECT);
            applyService.save(Apply);
            return new ResponseEntity<>(new ResponseMessage("Вы успешно отказались"), HttpStatus.OK);
        }

        if (changeStatusApply.getStatus() == 2) {
            Apply.setStatus(Status.WAIT);
            applyService.save(Apply);
            return new ResponseEntity<>(new ResponseMessage("Вы успешно изменили свой статус на ожидание"), HttpStatus.OK);
        }

        return new ResponseEntity<>(new ResponseMessage("Вы изменили состояние "), HttpStatus.OK);
    }
}
