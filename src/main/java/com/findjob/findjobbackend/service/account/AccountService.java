package com.findjob.findjobbackend.service.account;

import com.findjob.findjobbackend.model.Account;
import com.findjob.findjobbackend.repository.IAccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AccountService implements IAccountService {
    private final IAccountRepository accountRepository;

    @Override
    public Iterable<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public Page<Account> findAll(Pageable pageable) {
        if (pageable == null) {
            System.out.println("Pageable parameter is null");
            throw new IllegalArgumentException("Pageable parameter cannot be null");
        }
        return accountRepository.findAll(pageable);
    }

    @Override
    public void deleteById(Long id) {
        if (id == null) {
            System.out.println("ID parameter is null");
            throw new IllegalArgumentException("ID parameter cannot be null");
        }
        if (!accountRepository.existsById(id)) {
            System.out.println("Account with ID {} does not exist" + id);
            throw new IllegalArgumentException("Account with ID " + id + " does not exist");
        }
        accountRepository.deleteById(id);
    }

    @Override
    public Account save(Account account) {
        if (account == null) {
            System.out.println("Account parameter is null");
            throw new IllegalArgumentException("Account parameter cannot be null");
        }
        return accountRepository.save(account);
    }

    @Override
    public Optional<Account> findById(Long id) {
        if (id == null) {
            System.out.println("ID parameter is null");
            throw new IllegalArgumentException("ID parameter cannot be null");
        }
        return accountRepository.findById(id);
    }

    @Override
    public Optional<Account> findByUsername(String name) {
        if (name == null || name.isEmpty()) {
            System.out.println("Username parameter is null or empty");
            throw new IllegalArgumentException("Username parameter cannot be null or empty");
        }
        return accountRepository.findByUsername(name);
    }

    @Override
    public Boolean existsByUsername(String username) {
        if (username == null || username.isEmpty()) {
            System.out.println("Username parameter is null or empty");
            throw new IllegalArgumentException("Username parameter cannot be null or empty");
        }
        return accountRepository.existsByUsername(username);
    }
}
