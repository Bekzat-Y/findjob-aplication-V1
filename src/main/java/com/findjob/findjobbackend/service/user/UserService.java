package com.findjob.findjobbackend.service.user;

import com.findjob.findjobbackend.dto.request.UsernameAndPasswordUser;
import com.findjob.findjobbackend.enums.UserStatus;
import com.findjob.findjobbackend.model.User;
import com.findjob.findjobbackend.repository.IUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
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
            logger.error("ID parameter is null");
            throw new IllegalArgumentException("ID parameter cannot be null");
        }

        Optional<User> userOptional = userRepository.findByAccount_Id(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setStatus(UserStatus.DELETE);
            userRepository.save(user);
        } else {
            logger.error("User not found with ID: {}", id);
            throw new RuntimeException("User not found with ID: " + id);
        }
    }

    @Override
    public User save(User user) {
        if (user == null) {
            logger.error("User parameter is null");
            throw new IllegalArgumentException("User parameter cannot be null");
        }
         if (!Objects.equals(user.getId(), user.getAccount().getId())) {
            throw new IllegalArgumentException("Такой пользователь не найден");
         }
        return userRepository.save(user);
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
    public UsernameAndPasswordUser findUsernameAndPassword(Long id) {
        if (id == null) {
            logger.error("ID parameter is null ");
            throw new IllegalArgumentException("ID parameter cannot be null");
        }
        return null;
    }
}
