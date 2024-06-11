package com.findjob.findjobbackend.service.role;


import com.findjob.findjobbackend.model.Role;
import com.findjob.findjobbackend.enums.RoleName;
import com.findjob.findjobbackend.service.IGeneralService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface IRoleService extends IGeneralService<Role> {
    Optional<Role> findByName(RoleName name);
}
