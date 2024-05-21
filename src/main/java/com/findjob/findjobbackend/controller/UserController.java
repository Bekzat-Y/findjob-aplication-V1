package com.findjob.findjobbackend.controller;


import com.findjob.findjobbackend.dto.response.ResponseMessage;
import com.findjob.findjobbackend.model.Account;
import com.findjob.findjobbackend.model.User;
import com.findjob.findjobbackend.model.email.MailObject;
import com.findjob.findjobbackend.security.userprincipal.UserDetailServices;
import com.findjob.findjobbackend.service.account.AccountService;
import com.findjob.findjobbackend.service.email.EmailServiceImpl;
import com.findjob.findjobbackend.service.user.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "*")
@RequestMapping("/user")
@RestController
@AllArgsConstructor

public class UserController {


    private final IUserService userService;

    private final UserDetailServices userDetailServices;


   private final EmailServiceImpl emailService;

   private final AccountService accountService;

    @GetMapping
    public ResponseEntity<Iterable<User>> findAll() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<User> user = userService.findById(id);
        if (user.isEmpty()) {
            return new ResponseEntity<>(new ResponseMessage("Không có user này"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addUser(@RequestBody User user) {
        if (user.getName() == null) {
            return new ResponseEntity<>(new ResponseMessage("name_null"), HttpStatus.OK);
        }
        userService.save(user);
        Account account = accountService.findById(user.getAccount().getId()).get();
        MailObject mailObject1 = new MailObject("findJob@job.com",account.getUsername(), "Account Tinder Windy Verified", "Vui lòng nhấn vào đây để xác nhận tài khoản: " + "\n http://localhost:4200/active-status/"+account.getId() );
        emailService.sendSimpleMessage(mailObject1);
        return new ResponseEntity<>(new ResponseMessage("yes"), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User user) {
        Account account = userDetailServices.getCurrentUser();
        if (account.getUsername().equals("Anonymous")) {
            return new ResponseEntity<>(new ResponseMessage("Please login!"), HttpStatus.OK);
        }
        Optional<User> userOptional = userService.findById(id);
        if (userOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        userOptional.get().setName(user.getName());
        userOptional.get().setPhone(user.getPhone());
        userService.save(userOptional.get());
        return new ResponseEntity<>(new ResponseMessage("yes"), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCompany(@PathVariable Long id) {
        Optional<User> user = userService.findById(id);
        if (user.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        userService.deleteById(id);
        return new ResponseEntity<>(new ResponseMessage("yes"), HttpStatus.OK);
    }
}
