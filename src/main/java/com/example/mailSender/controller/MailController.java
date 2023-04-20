package com.example.mailSender.controller;

import com.example.mailSender.service.*;
import com.ibm.as400.access.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;

@RestController
public class MailController {

    @Autowired
    MailSenderService mailSenderService;
    @Autowired
    IfsUtil ifsUtil;
    @Autowired
    AttachedMail attachedMail;

    @Autowired
    sendMailWithAttachment send;

    @Autowired
    GraphClientSimpleMail graphClientSimpleMail;
    @Autowired
    GraphClientWithAttachmentService graphClientWithAttachmentService;


    @RequestMapping("ftp")
    public String ftp() throws IOException, AS400SecurityException, ObjectAlreadyExistsException, MessagingException, MessagingException {


      //  IFSFile file = ifsUtil.getIfsFile("/home/UC36664103_inbound_edi.edi");
        IFSFile file = null;// = ifsUtil.getIfsFile("/home/UC36664103_inbound_edi.edi");

        String fileString = readFromInputStream(file);


        graphClientWithAttachmentService.getGraphClient(fileString);
        System.out.println("send mail");

        if (!file.exists()) {
            return "no file";
        } else {
            return file.getName();
        }
    }

    @RequestMapping("send")
    public void sendMail() {
        mailSenderService.sendMailToAll();
    }

    private String readFromInputStream(IFSFile inputStream) throws IOException, AS400SecurityException {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new IFSFileReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        }
        return resultStringBuilder.toString();
    }
}
