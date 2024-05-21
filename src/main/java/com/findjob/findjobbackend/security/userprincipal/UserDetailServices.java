package com.findjob.findjobbackend.security.userprincipal;

import com.findjob.findjobbackend.model.Account;
import com.findjob.findjobbackend.repository.IAccountRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailServices implements UserDetailsService {
    private final IAccountRepository accountRepository;

    public UserDetailServices(IAccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account getCurrentUser() {
        String userName = getString();
        Optional<Account> account = accountRepository.findByUsername(userName);
        if (account.isPresent()) {
            return account.get();
        } else {
            Account anonymousUser = new Account();
            anonymousUser.setUsername("Anonymous");
            return anonymousUser;
        }
    }

    private static String getString() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Implement this method if needed
        throw new UsernameNotFoundException("User not found with username: " + username);
    }
}
