package com.findjob.findjobbackend.service.account;


import com.findjob.findjobbackend.model.Account;
import com.findjob.findjobbackend.service.IGeneralService;

import java.util.Optional;

public interface IAccountService extends IGeneralService<Account> {
    Optional<Account> findByUsername(String name);
    Boolean existsByUsername(String username);
}

