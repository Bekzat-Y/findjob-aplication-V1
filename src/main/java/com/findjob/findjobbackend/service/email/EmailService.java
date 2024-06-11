package com.findjob.findjobbackend.service.email;


import com.findjob.findjobbackend.model.email.MailObject;

public interface EmailService {
    void sendSimpleMessage(MailObject mail);

}
