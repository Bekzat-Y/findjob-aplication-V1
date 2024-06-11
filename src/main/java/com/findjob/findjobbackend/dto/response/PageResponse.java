package com.findjob.findjobbackend.dto.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PageResponse {
    private Long totalRecord;
    private Object data;

}
