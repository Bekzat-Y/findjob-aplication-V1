package com.findjob.findjobbackend.controller;


import com.findjob.findjobbackend.dto.response.ResponseMessage;
import com.findjob.findjobbackend.model.Account;
import com.findjob.findjobbackend.model.User;
import com.findjob.findjobbackend.security.userprincipal.UserDetailServices;
import com.findjob.findjobbackend.service.account.AccountService;
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



   private final AccountService accountService;

    @GetMapping
    public ResponseEntity<Iterable<User>> findAll() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<User> user = userService.findById(id);
        if (user.isEmpty()) {
            return new ResponseEntity<>(new ResponseMessage("Пользователь не найден "), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody User user) {
        if (user.getName() == null) {
            return new ResponseEntity<>(new ResponseMessage("name_null"), HttpStatus.OK);
        }
        userService.save(user);
        accountService.findById(user.getAccount().getId());
        return new ResponseEntity<>(new ResponseMessage("Успешно"), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User user) {
        Account account = userDetailServices.getCurrentUser();
        if (account.getUsername().equals("Anonymous")) {
            return new ResponseEntity<>(new ResponseMessage("Пожалуйста войдите !"), HttpStatus.OK);
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
        Optional<User> user = userService.findByAccount_Id(id);
        if (user.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new ResponseMessage("да"), HttpStatus.OK);
    }
}
