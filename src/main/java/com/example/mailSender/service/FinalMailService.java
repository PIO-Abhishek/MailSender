package com.example.mailSender.service;

import com.example.mailSender.entity.MailDetailEntity;
import com.example.mailSender.repository.MailDetailRepository;
import com.ibm.as400.access.AS400SecurityException;
import com.ibm.as400.access.IFSFile;
import com.ibm.as400.access.IFSFileReader;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.*;
import java.util.List;

@Service
public class FinalMailService {


    @Autowired
    MailDetailRepository repository;
    @Autowired
    FinalGraphMailService finalGraphMailService;
    @Autowired
    IfsUtil ifsUtil;
    @Autowired
    ItextService service;

    @Autowired
    sendMailWithAttachment mail;
    @Autowired
    PDFService pdfService;


  /*  public void sendMailToAll() throws IOException, AS400SecurityException, MessagingException {
        List<MailDetailEntity> listMail = repository.findAll();

        for (MailDetailEntity list : listMail) {

            IFSFile file = ifsUtil.getIfsFile(list.getPath());
            String fileString = readFromInputStream(file);


            finalGraphMailService.getGraphClient(fileString, list.getSubject(), list.getMessage(), list.getRecipient());

        }


    }
    */


    public void sendMail() throws IOException, AS400SecurityException, MessagingException, DocumentException {


        IFSFile file = ifsUtil.getIfsFile("/home/UC36664103_inbound_edi.edi");
        ByteArrayInputStream byteArrayInputStream=readFromInputStream(file);
        service.getGraphClient(byteArrayInputStream);



    }


    // read IFS File and store in String
    /*private String readFromInputStream(IFSFile inputStream) throws IOException, AS400SecurityException {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new IFSFileReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        }
        return resultStringBuilder.toString();



    }*/


    private ByteArrayInputStream readFromInputStream(IFSFile inputStream) throws IOException, AS400SecurityException, DocumentException {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new IFSFileReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        }
        final ByteArrayOutputStream out = new ByteArrayOutputStream();

        final Document document = new Document();

        final PdfWriter pdfWriter = PdfWriter.getInstance(document, out);
        document.open();
        Paragraph paragraph=new Paragraph(resultStringBuilder.toString());
        document.add(paragraph);
        document.close();
        return new ByteArrayInputStream(out.toByteArray());



    }


}
