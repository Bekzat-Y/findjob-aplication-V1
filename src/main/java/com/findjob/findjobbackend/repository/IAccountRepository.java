package com.findjob.findjobbackend.repository;


import com.findjob.findjobbackend.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IAccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByUsername(String name);
    Boolean existsByUsername(String username);
}
