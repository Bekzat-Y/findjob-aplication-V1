package com.findjob.findjobbackend.controller;


import com.findjob.findjobbackend.dto.request.ChangePassword;
import com.findjob.findjobbackend.dto.request.SignInForm;
import com.findjob.findjobbackend.dto.request.SignUpForm;
import com.findjob.findjobbackend.dto.response.JwtResponse;
import com.findjob.findjobbackend.dto.response.ResponseAccount;
import com.findjob.findjobbackend.dto.response.ResponseMessage;
import com.findjob.findjobbackend.enums.RoleName;
import com.findjob.findjobbackend.enums.Status;
import com.findjob.findjobbackend.model.Account;
import com.findjob.findjobbackend.model.Company;
import com.findjob.findjobbackend.model.Role;
import com.findjob.findjobbackend.model.User;
import com.findjob.findjobbackend.security.jwt.JwtProvider;
import com.findjob.findjobbackend.security.userprincipal.UserDetailServices;
import com.findjob.findjobbackend.security.userprincipal.UserPrinciple;
import com.findjob.findjobbackend.service.account.AccountService;
import com.findjob.findjobbackend.service.company.CompanyService;
import com.findjob.findjobbackend.service.role.RoleService;
import com.findjob.findjobbackend.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@RestController
@RequestMapping("/sign")
@RequiredArgsConstructor
public class AuthController {
    private final AccountService accountService;

    private final RoleService roleService;

    private final PasswordEncoder passwordEncoder;

    private final  AuthenticationManager authenticationManager;

    private final JwtProvider jwtProvider;

    private final UserDetailServices userDetailService;

    private final CompanyService companyService;


    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> register(@RequestBody SignUpForm signUpForm ) {
        if (accountService.existsByUsername(signUpForm.getUsername())) {
            return new ResponseEntity<>(new ResponseAccount("no_user", -1L),HttpStatus.BAD_REQUEST);
        }
        String originalPassword = passwordEncoder.encode(signUpForm.getPassword());
        Account account = new Account(signUpForm.getUsername(),originalPassword);
        Set<String> strRoles = signUpForm.getRoles();
        Set<Role> roles = new HashSet<>();
        strRoles.forEach(role -> {
            switch (role) {
                case "admin":
                    Role adminRole = roleService.findByName(RoleName.ADMIN).orElseThrow(
                            () -> new RuntimeException("Role not found")
                    );
                    roles.add(adminRole);
                    break;
                case "company":
                    Role pmRole = roleService.findByName(RoleName.COMPANY).orElseThrow(() -> new RuntimeException("Role not found"));
                    roles.add(pmRole);
                    int min = 10000000;
                    int max = 99999999;
                    String passwordNew = String.valueOf((int) Math.floor(Math.round((Math.random() * (max - min + 1) + min))));
                    account.setPassword(passwordEncoder.encode(passwordNew));
                    break;
                default:
                    Role userRole = roleService.findByName(RoleName.USER).orElseThrow(() -> new RuntimeException("Role not found"));
                    roles.add(userRole);
            }
        });

        account.setRoles(roles);
        account.setStatus(Status.NON_ACTIVE);
        accountService.save(account);
        return new ResponseEntity<>(new ResponseAccount("Yes", account.getId()), HttpStatus.OK);
    }





    @PostMapping("/signin")
    public ResponseEntity<?> login(@RequestBody SignInForm signInForm) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(signInForm.getUsername(), signInForm.getPassword())
            );
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage("Invalid username or password"), HttpStatus.UNAUTHORIZED);
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();

        if (userPrinciple.getStatus().equalsIgnoreCase(String.valueOf(Status.NON_ACTIVE))) {
            return new ResponseEntity<>(new ResponseMessage("LOCK"), HttpStatus.OK);
        }

        String token = jwtProvider.createToken(authentication);
        Long id = userPrinciple.getId();
        String authorities = userPrinciple.getAuthorities().toString();

        if (authorities.contains("COMPANY")) {
            Optional<Company> optionalCompany = companyService.findAllByAccount_Id(id);
            if (optionalCompany.isPresent()) {
                Long companyId = optionalCompany.get().getId();
                System.out.println("COMPANY ID: " + companyId);
            }
        } else if (authorities.contains("USER")) {
            Optional<User> optionalUser = userService.findByAccount_Id(id);
            if (optionalUser.isPresent()) {
                Long userId = optionalUser.get().getId();
                System.out.println("USER ID: " + userId);
                }
}
            return ResponseEntity.ok(new JwtResponse(id, token, userPrinciple.getUsername(), userPrinciple.getAuthorities()));
        }


    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword( @RequestBody ChangePassword changePassword) {
        Account account = userDetailService.getCurrentUser();
        if (account.getUsername().equals("Anonymous")) {
            return new ResponseEntity<>(new ResponseMessage("Please login"), HttpStatus.OK);
        }
        account.setPassword(passwordEncoder.encode(changePassword.getPassword()));
        accountService.save(account);
        return new ResponseEntity<>(new ResponseMessage("yes"), HttpStatus.OK);
    }
//    @PutMapping("/change-avatar")
//    public ResponseEntity<?> updateAvatar(@RequestBody ChangeAvatar avatar){

    //
//   }
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<Account> accountOptional = accountService.findById(id);
        if (accountOptional.isPresent()) {
            return new ResponseEntity<>(accountOptional.get().getStatus(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseMessage("Account not found"), HttpStatus.NOT_FOUND);
        }
    }



    @GetMapping("/verify/{id}")
    public ResponseEntity<Account> verifyAccount(@PathVariable Long id) {
        Optional<Account> account = accountService.findById(id);
        if (account.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        account.get().setStatus(Status.ACTIVE);
        accountService.save(account.get());
        return new ResponseEntity<>(account.get(), HttpStatus.OK);
    }


    @GetMapping("/showAllAccount")
    public ResponseEntity<?> showAllAccount(){
        List<Account> accounts = (List<Account>) accountService.findAll();
        for (Account account : accounts) {
            account.setStatus2(!account.getStatus().equals(Status.NON_ACTIVE));
        }
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @PutMapping("/editStatusAccount/{id}")
    public ResponseEntity<?> editStatus(@PathVariable Long id) {
        Optional<Account> accountOptional = accountService.findById(id);
        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            if (account.getStatus().equals(Status.NON_ACTIVE)) {
                account.setStatus2(true);
                account.setStatus(Status.ACTIVE);
            } else {
                account.setStatus2(false);
                account.setStatus(Status.NON_ACTIVE);
            }
            accountService.save(account);
            return new ResponseEntity<>(new ResponseMessage("yes"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseMessage("Account not found"), HttpStatus.NOT_FOUND);
        }
    }

}
