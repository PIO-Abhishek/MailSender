package com.example.mailSender.service;

import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.util.Properties;

@Service
public class SMTP_MailService {

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
            message.setSubject("subject");

        Address addressTo = new InternetAddress("carapplicationcare@gmail.com");
        message.addRecipient(Message.RecipientType.TO, addressTo);
        Address addressFrom[] = {new InternetAddress("abhishek.jadhav@programmers.io")};
        message.addFrom(addressFrom);

        MimeMultipart multipart = new MimeMultipart();
        MimeBodyPart attachment = new MimeBodyPart();
      //  FileSystemResource fileSystemResource = new FileSystemResource(new File("C:\\Users\\AbhishekJadhav\\Desktop\\New Text Document.txt"));
        attachment.attachFile("C:\\Users\\AbhishekJadhav\\Desktop\\New Text Document.txt");
        MimeBodyPart messageBody = new MimeBodyPart();
        messageBody.setContent("hi team", "text/html");
        multipart.addBodyPart(messageBody);
        multipart.addBodyPart(attachment);

        message.setContent(multipart);
        Transport.send(message);
    }


}
