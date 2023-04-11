package com.example.mailSender.service;

import com.example.mailSender.entity.MailDetailEntity;
import com.example.mailSender.repository.MailDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.List;
import java.util.Properties;

@Service
public class MailSenderService {

    @Autowired
    MailServices mailServices;
    @Autowired
    MailDetailRepository mailDetailRepository;
    @Autowired
    JavaMailSender javaMailSender;


    public void sendMailToAll() {
        List<MailDetailEntity> listMail = mailDetailRepository.findAll();

        for (MailDetailEntity list : listMail) {
            mail(list.getRecipient(), list.getSubject(), list.getMessage());
        }


    }


    public void mail(String email, String subject, String message) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(email);
        mail.setSubject(subject);
        mail.setText("Hi " + "\n" + message);

        mailServices.sendEmail(mail);
    }
  public void sendAttachmentMail(String email, String subject, String message, String path) throws MessagingException {

      MimeMessage mimeMessage=javaMailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(mimeMessage,true);
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject(subject);
        mimeMessage.setText(message);
        FileSystemResource fileSystemResource=new FileSystemResource(new File(path));
        mimeMessageHelper.addAttachment(fileSystemResource.getFilename(),fileSystemResource);
        javaMailSender.send(mimeMessage);


       /* Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        Message mimeMessage = new MimeMessage(session);
        Multipart multipart = new MimeMultipart();
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        String filename = "/home/manisha/file.txt";
    //    DataSource source = new FileDataSource(filename);

      //  messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.attachFile(new File(path));
      //  messageBodyPart.setFileName(filename);
        multipart.addBodyPart(messageBodyPart);
        mimeMessage.setContent(multipart);

      */
    }



}
