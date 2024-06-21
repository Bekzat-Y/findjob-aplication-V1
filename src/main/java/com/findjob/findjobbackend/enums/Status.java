package com.findjob.findjobbackend.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Status {
    ACTIVE,
    NON_ACTIVE,
    LOCK,
    UNLOCK,
    WAIT,
    REJECT,
    ACCEPT,
    DELETE

}
