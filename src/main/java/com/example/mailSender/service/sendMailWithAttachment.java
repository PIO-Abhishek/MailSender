package com.example.mailSender.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;
import java.util.Properties;


@Service
public class sendMailWithAttachment {

    @Autowired
    IfsUtil ifsUtil;


//    @Scheduled(cron = "${sendMail.file.schedule}")
//    @Async
    public void sendAttachedMail() throws MessagingException, IOException {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.starttls.enable", true);
        properties.put("mail.smtp.host", "outlook.office365.com");
        properties.put("mail.smtp.port", 587);


        Session session = Session.getInstance(properties, new Authenticator() {


            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("abhishek.jadhav@programmers.io", "nrbdsptwkzvfyydc");
            }
        });


        Message message = new MimeMessage(session);
        try {
            message.setSubject("From SMTP API");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        Address addressTo = new InternetAddress("keshav.mundra@programmers.io");
        message.addRecipient(Message.RecipientType.TO, addressTo);
        Address addressFrom[] = {new InternetAddress("abhishek.jadhav@programmers.io")};

        message.addFrom(addressFrom);

        MimeMultipart multipart = new MimeMultipart();


        MimeBodyPart attachment = new MimeBodyPart();
       attachment.attachFile(new File("C:\\Users\\AbhishekJadhav\\Desktop\\New Text Document.txt"));

        MimeBodyPart messageBody = new MimeBodyPart();
        messageBody.setContent("<h1> THIS IS TEST SMTP EMAIL</h1>", "text/html");
        multipart.addBodyPart(messageBody);
        multipart.addBodyPart(attachment);

        message.setContent(multipart);
        Transport.send(message);
    }


}
