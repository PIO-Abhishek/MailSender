package com.example.mailSender.controller;

import com.example.mailSender.service.FinalMailService;
import com.ibm.as400.access.AS400SecurityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class GraphController {


    @Autowired
    FinalMailService service;

    @RequestMapping("sendMail")
    public String sendMail() throws AS400SecurityException, IOException {
        service.sendMailToAll();
        return "successfull";
    }

}
