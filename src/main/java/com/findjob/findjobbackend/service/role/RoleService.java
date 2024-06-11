package com.findjob.findjobbackend.service.role;

import com.findjob.findjobbackend.model.Role;
import com.findjob.findjobbackend.enums.RoleName;
import com.findjob.findjobbackend.repository.IRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService implements IRoleService {

    private static final Logger logger = LoggerFactory.getLogger(RoleService.class);

    private final IRoleRepository roleRepository;

    @Override
    public Iterable<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Page<Role> findAll(Pageable pageable) {
        if (pageable == null) {
            logger.error("Pageable parameter is null");
            throw new IllegalArgumentException("Pageable parameter cannot be null");
        }
        return roleRepository.findAll(pageable);
    }

    @Override
    public void deleteById(Long id) {
        if (id == null) {
            logger.error("ID parameter is null");
            throw new IllegalArgumentException("ID parameter cannot be null");
        }
        roleRepository.deleteById(id);
    }

    @Override
    public Role save(Role role) {
        if (role == null) {
            logger.error("Role parameter is null");
            throw new IllegalArgumentException("Role parameter cannot be null");
        }
        return roleRepository.save(role);
    }

    @Override
    public Optional<Role> findById(Long id) {
        if (id == null) {
            logger.error("ID parameter is null or empty");
            throw new IllegalArgumentException("ID parameter cannot be null");
        }
        return roleRepository.findById(id);
    }

    @Override
    public Optional<Role> findByName(RoleName name) {
        if (name == null) {
            logger.error("RoleName parameter is null");
            throw new IllegalArgumentException("RoleName parameter cannot be null");
        }
        return roleRepository.findByName(name);
    }
}
