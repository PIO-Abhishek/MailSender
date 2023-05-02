package com.example.mailSender.service;

import com.ibm.as400.access.AS400SecurityException;
import com.ibm.as400.access.IFSFile;
import com.ibm.as400.access.IFSFileReader;
import com.itextpdf.text.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.BufferedReader;
import java.io.IOException;

@Service
public class MailService {
    @Autowired
    IfsUtil ifsUtil;
    @Autowired
    GraphApiMailService service;



    public void sendMail() throws IOException, AS400SecurityException, MessagingException, DocumentException {
        IFSFile file = ifsUtil.getIfsFile("/home/piolib/SDPFTPF.txt");
      //  IFSFile file = ifsUtil.getIfsFile("/home/UC36664103_inbound_edi.edi");
        //byte[] bytes = readFromInputStream(file);
        service.getGraphClient(readFromInputStream(file));
    }

    private String readFromInputStream(IFSFile inputStream) throws IOException, AS400SecurityException, DocumentException {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new IFSFileReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
           //     System.out.println(line);
                resultStringBuilder.append(line).append("\n");
            }
        }
        return resultStringBuilder.toString();

      /*  final ByteArrayOutputStream out = new ByteArrayOutputStream();
          *//*landscape page *//*
        final Document document = new Document(PageSize.A4);


        PdfWriter.getInstance(document, out);
        document.open();
        Paragraph paragraph = new Paragraph(resultStringBuilder.toString());
        paragraph.setAlignment(Element.ALIGN_JUSTIFIED_ALL);
        document.add(paragraph);
        document.close();
        return out.toByteArray();*/
    }



}
