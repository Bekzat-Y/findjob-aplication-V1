package com.findjob.findjobbackend.dto.response;

public interface ApplyShowAll {
    Long getId();
    String getDate();
    String getStatus();

    Long getUserId();
    String getTitle();
    Long getVacanciesId();

    String getAvatar();
    String getNameUser();
    String getPhoneUser();
}
