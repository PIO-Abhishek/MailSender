package com.example.mailSender.service;

import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.io.IOException;
import java.util.Properties;



@Service
public class sendMailWithAttachment {



public void sendAttachedMail() throws MessagingException, IOException {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.starttls.enable", true);
        properties.put("mail.smtp.host", "smtp.office365.com");
        properties.put("mail.smtp.port", 587);

    // properties.put("mail.transport.portocol", "smtp");

        Session session = Session.getInstance(properties, new Authenticator() {


            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("programmersio@homecrest.com", "Homecrest23!");
            }
        });


        Message message = new MimeMessage(session);
        try {
            message.setSubject("this IDE");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        Address addressTo = new InternetAddress("abhishek.jadhav@programmers.io");
        message.addRecipient(Message.RecipientType.TO, addressTo);

        MimeMultipart multipart = new MimeMultipart();


        MimeBodyPart attachment = new MimeBodyPart();
        attachment.attachFile(new File("C:\\Users\\AbhishekJadhav\\Desktop\\New Text Document.txt"));

        MimeBodyPart messageBody = new MimeBodyPart();
        messageBody.setContent("<h1> this is email</h1>", "text/html");
        multipart.addBodyPart(messageBody);
        multipart.addBodyPart(attachment);

        message.setContent(multipart);
        Transport.send(message);
    }



}
