package com.example.mailSender.controller;

import com.example.mailSender.service.*;
import com.ibm.as400.access.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.io.BufferedReader;
import java.io.File;
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


    @RequestMapping("ftp")
    public String ftp() throws IOException, AS400SecurityException, ObjectAlreadyExistsException, MessagingException, MessagingException {


        IFSFile file= ifsUtil.getIfsFile("/home/New Text Document.txt");
 //       IFSFileInputStream fis = new IFSFileInputStream(file,IFSFileInputStream.SHARE_NONE);
        BufferedReader reader = new BufferedReader(new IFSFileReader(file));

// Read the first line of the file, converting characters.
        File file1=new File(String.valueOf(new IFSFileInputStream(file)));

        String line1 = reader.readLine();

        System.out.println(line1);

        reader.close();

  //  String path=("C:\\Users\\AbhishekJadhav\\Desktop\\New Text Document.txt");
   //     mailSenderService.sendAttachmentMail("abhishek.jadhav@programmers.io","file send","this is try to send file","C:\\Users\\AbhishekJadhav\\Desktop\\New Text Document.txt");
   //    attachedMail.sendMailWithAttachment("abhishek.jadhav@programmers.io","file send","this is try to send file","C:\\Users\\AbhishekJadhav\\Desktop\\New Text Document.txt");

     //   send.sendAttachedMail();
        graphClientSimpleMail.simpleGraphApiMail();
        System.out.println("send mail");

        if(!file.exists())
       {
           return "no file";
       }
       else {
           return file.getName();
       }
    }

    @RequestMapping("send")
    public void sendMail()
    {
        mailSenderService.sendMailToAll();
    }
}
