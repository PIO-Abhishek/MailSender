package com.example.mailSender.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class MailServices {


    @Autowired
    JavaMailSender javaMailSender;


    @Autowired
    public MailServices(JavaMailSender javaMailSender)
    {
        this.javaMailSender=javaMailSender;
    }


    @Async
    public void sendEmail(SimpleMailMessage email)
    {
        javaMailSender.send(email);
    }



}
