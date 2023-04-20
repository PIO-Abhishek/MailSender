package com.example.mailSender.controller;

import com.example.mailSender.service.FinalGraphMailHardCodeService;
import com.example.mailSender.service.FinalMailService;
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
    FinalMailService service;
    @Autowired
    FinalGraphMailHardCodeService finalGraphMailHardCodeService;

    @RequestMapping("local")
    public String localAttachment() throws IOException {
        finalGraphMailHardCodeService.getGraphClient();
        return "success";
    }


    @RequestMapping("pdf")
    public void pdf() throws IOException, AS400SecurityException, MessagingException, DocumentException {
        service.sendMail();
    }

}
