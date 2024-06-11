package com.findjob.findjobbackend.model.email;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MailObject {
    private String mailFrom;

    private String mailTo;

    private String mailCc;

    private String mailBcc;

    private String mailSubject;

    private String mailContent;

    @Getter
    private String contentType;

    private List< Object > attachments;

    public MailObject() {
    }

    public MailObject(String mailFrom, String mailTo, String mailSubject, String mailContent) {
        this.mailFrom = mailFrom;
        this.mailTo = mailTo;
        this.mailSubject = mailSubject;
        this.mailContent = mailContent;
    }


}
