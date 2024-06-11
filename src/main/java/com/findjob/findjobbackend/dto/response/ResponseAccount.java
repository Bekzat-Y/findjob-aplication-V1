package com.findjob.findjobbackend.dto.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseAccount {
    private String message;
    private Long id;

    public ResponseAccount(String message, Long id) {
        this.message = message;
        this.id = id;
    }


}
