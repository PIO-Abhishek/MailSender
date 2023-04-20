package com.example.mailSender.service;

import com.microsoft.graph.models.FileAttachment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.Properties;

@Service
public class FinalForGmailService {



    @Scheduled(cron = "${sendMail.file.schedule}")
    @Async
    public void sendAttachedMail(String subject,String to,String file,String body) throws MessagingException, IOException {
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
            message.setSubject(subject);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        Address addressTo = new InternetAddress(to);
        message.addRecipient(Message.RecipientType.TO, addressTo);
        Address addressFrom[] = {new InternetAddress("abhishek.jadhav@programmers.io")};

        message.addFrom(addressFrom);

        MimeMultipart multipart = new MimeMultipart();


        MimeBodyPart attachment = new MimeBodyPart();
       /* FileAttachment attachments = new FileAttachment();
        attachments.oDataType = "#microsoft.graph.fileAttachment";
        attachments.name = "New Text Document.pdf";

        String encodedString = Base64.getEncoder().encodeToString(file.getBytes());
        attachments.contentBytes = Base64.getDecoder().decode(encodedString);


        attachment.attachFile(new File(String.valueOf(attachment)));

        */

        MimeBodyPart messageBody = new MimeBodyPart();
        messageBody.setContent(body, "text/html");
        multipart.addBodyPart(messageBody);
        multipart.addBodyPart(attachment);

        message.setContent(multipart);
        Transport.send(message);
    }


}
