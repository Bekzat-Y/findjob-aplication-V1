package com.findjob.findjobbackend.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ForwardApply {
    private Long idUser;
    private Long idCompany;

    public ForwardApply(Long idUser, Long idCompany) {
        this.idUser = idUser;
        this.idCompany = idCompany;
    }

}
