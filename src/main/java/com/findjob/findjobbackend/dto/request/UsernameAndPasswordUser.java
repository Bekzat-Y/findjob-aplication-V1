package com.findjob.findjobbackend.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsernameAndPasswordUser {
    private Long id;
    private String username;
    private String password;

    public UsernameAndPasswordUser(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }
}
