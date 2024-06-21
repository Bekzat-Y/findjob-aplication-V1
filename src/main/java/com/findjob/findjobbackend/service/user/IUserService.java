package com.findjob.findjobbackend.service.user;


import com.findjob.findjobbackend.model.User;
import com.findjob.findjobbackend.service.IGeneralService;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface IUserService extends IGeneralService<User> {
    Optional<User> findByAccount_Id(Long id);

    Boolean existsByName(String name);

    Optional<User> findUsernameAndPassword(Long id);

    Optional<User> updateUser(User user);
}
