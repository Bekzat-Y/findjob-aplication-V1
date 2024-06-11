package com.findjob.findjobbackend.controller;


import com.findjob.findjobbackend.dto.request.EditCompany;
import com.findjob.findjobbackend.dto.request.StatusRequest;
import com.findjob.findjobbackend.dto.response.CompanyRecruitmentNeed;
import com.findjob.findjobbackend.dto.response.ResponseMessage;
import com.findjob.findjobbackend.enums.Status;
import com.findjob.findjobbackend.model.Account;
import com.findjob.findjobbackend.model.Company;
import com.findjob.findjobbackend.security.userprincipal.UserDetailServices;
import com.findjob.findjobbackend.service.company.CompanyService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RequestMapping("company")
@RestController
@AllArgsConstructor
public class CompanyController {
    private final CompanyService companyService;
    private final UserDetailServices userDetailServices;

    @PostMapping("")
    public ResponseEntity<?> createCompany(@RequestBody Company company) {
        Account account = userDetailServices.getCurrentUser();
        if(account.getUsername().equals("Anonymous")){
            return new ResponseEntity<>(new ResponseMessage("Please login!"), HttpStatus.NOT_FOUND);
        }

        if (companyService.existsByName(company.getName())) {
            return new ResponseEntity<>(new ResponseMessage("no_name_category"), HttpStatus.OK);
        }
        String names = company.getName().substring(0, 3);
        int min = 1000;
        int max = 9999;
        String codeCompany = String.valueOf((int) Math.floor(Math.round((Math.random() * (max - min + 1) + min))));
        System.out.println(codeCompany);
        company.setCodeCompany(names + company.getAccount().getId() + codeCompany);
        company.setStatusCompany(Status.HOT);
        companyService.save(company);
        return new ResponseEntity<>(new ResponseMessage("yes"), HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<?> showListCompany() {
        Iterable<Company> companyList = companyService.findAll();
        return new ResponseEntity<>(companyList, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCompany(@PathVariable Long id, @RequestBody EditCompany editCompany) {
        Account account = userDetailServices.getCurrentUser();
        if (account.getUsername().equals("Anonymous")) {
            return new ResponseEntity<>(new ResponseMessage("Please login!"), HttpStatus.OK);
        }
        Optional<Company> company1 = companyService.findById(id);
        if (company1.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        boolean check = companyService.existsByName(editCompany.getName());

        if (editCompany.getName() != null) {
            company1.get().setName(editCompany.getName());
        }
        if (editCompany.getDescription() != null) {
            company1.get().setDescription(editCompany.getDescription());
        }
        if (editCompany.getAddress() != null) {
            company1.get().setAddress(editCompany.getAddress());
        }
        if (editCompany.getEmployeeQuantity() != null) {
            company1.get().setEmployeeQuantity(editCompany.getEmployeeQuantity());
        }
        if (editCompany.getLinkMap() != null) {
            company1.get().setLinkMap(editCompany.getLinkMap());
        }
        if (editCompany.getPhone() != null) {
            company1.get().setPhone(editCompany.getPhone());
        }
        String names = company1.get().getName().substring(0, 3);
        int min = 1000;
        int max = 9999;
        String codeCompany = String.valueOf((int) Math.floor(Math.round((Math.random() * (max - min + 1) + min))));
        company1.get().setCodeCompany(names + company1.get().getAccount().getId() + codeCompany);
        companyService.save(company1.get());
        if (check) {
            return new ResponseEntity<>(new ResponseMessage("Успешно"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseMessage("yes"), HttpStatus.OK);
    }

    @PutMapping("/change_status{id}")
    public ResponseEntity<?> updateCompany1(@PathVariable Long id, @RequestBody StatusRequest statusRequest) {
        Account account = userDetailServices.getCurrentUser();
        if (account.getUsername().equals("Anonymous")) {
            return new ResponseEntity<>(new ResponseMessage("Please login"), HttpStatus.OK);
        }

        Optional<Company> company1 = companyService.findById(id);
        if (company1.isEmpty()) {
            return new ResponseEntity<>(new ResponseMessage("Company not found"), HttpStatus.NOT_FOUND);
        }

        Company company = company1.get();
        switch (statusRequest.getStatus()) {
            case 1:
                company.setStatusCompany(Status.ACTIVE);
                break;
            case 2:
                company.setStatusCompany(Status.NON_ACTIVE);
                break;
            case 3:
                company.setStatusCompany(Status.LOCK);
                break;
            case 4:
                company.setStatusCompany(Status.UNLOCK);
                break;
            case 5:
                company.setStatusCompany(Status.HOT);
                break;
            case 6:
                company.setStatusCompany(Status.WAIT);
                break;
            case 7:
                company.setStatusCompany(Status.REJECT);
                break;
            default:
                return new ResponseEntity<>(new ResponseMessage("Invalid status"), HttpStatus.BAD_REQUEST);
        }

        companyService.save(company);
        return new ResponseEntity<>(new ResponseMessage("Status updated successfully"), HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> detailCompany(@PathVariable Long id) {
        Optional<Company> company = companyService.findById(id);
        if (company.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(company, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCompany(@PathVariable Long id) {
        Optional<Company> company = companyService.findById(id);
        if (company.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        companyService.deleteById(id);
        return new ResponseEntity<>(new ResponseMessage("yes"), HttpStatus.OK);
    }
    @GetMapping("/findByStatus/{status}")
    public ResponseEntity<?> findByStatus(@PathVariable Integer status){
        List<Company> companyList = companyService.findCompanyByStatus(status);
        return new ResponseEntity<>(companyList,HttpStatus.OK);
    }

    @GetMapping("/findByRecruitmentNewNeed")
    public ResponseEntity<?> findByRecruitmentNewNeed(){
        List<CompanyRecruitmentNeed> recruitmentNeedList = companyService.findCompanyByRecuitmentNew();
        return new ResponseEntity<>(recruitmentNeedList,HttpStatus.OK);
    }

}
