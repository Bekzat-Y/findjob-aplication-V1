package com.findjob.findjobbackend.service.user;

import com.findjob.findjobbackend.enums.Status;
import com.findjob.findjobbackend.model.User;
import com.findjob.findjobbackend.repository.IUserRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class UserService implements IUserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final IUserRepository userRepository;

    @Override
    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        if (pageable == null) {
            logger.error("Pageable parameter is null");
            throw new IllegalArgumentException("Pageable parameter cannot be null");
        }
        return userRepository.findAll(pageable);
    }

    @Override
    public void deleteById(Long id) {
        if (id == null) {
            logger.error("User id cannot be null");
            throw new IllegalArgumentException("User id cannot be null");
        }

        userRepository.findById(id).ifPresent(user -> {
            user.setStatus(Status.DELETE);
            userRepository.save(user);
            logger.info("User with id {} was deleted", id);
        });
    }


    @Override
    public User save(User user) {
        if (user == null) {
            logger.error("User parameter is null");
            throw new IllegalArgumentException("User parameter cannot be null");

        }else return  userRepository.save(user);
    }

    @Override
    public Optional<User> findById(Long id) {
        if (id == null) {
            logger.error("ID parameter is null  ");
            throw new IllegalArgumentException("ID parameter cannot be null");
        }
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> findByAccount_Id(Long id) {
        if (id == null) {
            logger.error("Account ID parameter is null");
            throw new IllegalArgumentException("Account ID parameter cannot be null");
        }
        return userRepository.findByAccount_Id(id);
    }

    @Override
    public Boolean existsByName(String name) {
        if (name == null || name.isEmpty()) {
            logger.error("Name parameter is null or empty");
            throw new IllegalArgumentException("Name parameter cannot be null or empty");
        }
        return userRepository.existsByName(name);
    }

    @Override
    public Optional<User> findUsernameAndPassword(Long id) {
        return userRepository.findById(id)
                .map(User::getAccount)
                .flatMap(account -> userRepository.findUsernameAndPassword(account.getUsername(), account.getPassword()));
    }

    public Optional<User> updateUser(User user) {
        Optional<User> userOptional = userRepository.findById(user.getId());
        if (userOptional.isEmpty()) {
            logger.error("User not found");
            return Optional.empty();
        } else {
            // Обновляем только те поля, которые переданы и не являются null
            if (user.getAccount() != null) {
                userOptional.get().setAccount(user.getAccount());
            }
            if (user.getName() != null) {
                userOptional.get().setName(user.getName());
            }
            if (user.getPhone() != null) {
                userOptional.get().setPhone(user.getPhone());
            }
            userRepository.save(userOptional.get());
        }
        return userOptional;
    }


}
