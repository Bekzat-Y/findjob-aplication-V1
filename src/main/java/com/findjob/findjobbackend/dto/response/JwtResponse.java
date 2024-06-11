package com.findjob.findjobbackend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
    private Long idAccount;
    private Long idGuest;
    private String token;
    private String type = "Bearer";
    private String username;
    private Collection<? extends GrantedAuthority> roles;


    public JwtResponse(Long id, String token, String username, Collection<? extends GrantedAuthority> authorities) {
        this.idAccount = id;
        this.idGuest = getIdGuest();
        this.token = token;
        this.username = username;
        this.roles = authorities;

        
    }
}

