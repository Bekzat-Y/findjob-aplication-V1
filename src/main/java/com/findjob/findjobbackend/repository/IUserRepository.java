package com.findjob.findjobbackend.repository;


import com.findjob.findjobbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    Optional<User> findByAccount_Id(Long id);
    Boolean existsByName(String name);
}
