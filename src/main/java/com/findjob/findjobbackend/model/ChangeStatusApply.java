package com.findjob.findjobbackend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ChangeStatusApply {
    private Long ApplyId;
    private Integer status;

}
