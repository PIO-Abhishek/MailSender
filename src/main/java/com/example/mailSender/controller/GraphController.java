package com.example.mailSender.controller;

import com.example.mailSender.service.SMTP_MailService;
import com.example.mailSender.service.MailService;
import com.ibm.as400.access.AS400SecurityException;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.io.IOException;

@RestController
public class GraphController {

    @Autowired
    MailService service;

    @Autowired
    SMTP_MailService finalForGmailService;




    @RequestMapping("sendEmailUsingGraphApi")
    public void sendEmailUsingGraphApi() throws IOException, AS400SecurityException, MessagingException, DocumentException {
        service.sendMail();
    }

    @RequestMapping("sendEmailUsingSMTP")
    public void sendEmailUsingSMTP() throws MessagingException, IOException {
        finalForGmailService.sendAttachedMail();
    }
}
