package com.findjob.findjobbackend.repository;

import com.findjob.findjobbackend.model.Role;
import com.findjob.findjobbackend.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName name);
}