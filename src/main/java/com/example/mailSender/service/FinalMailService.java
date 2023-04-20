package com.example.mailSender.service;

import com.ibm.as400.access.AS400SecurityException;
import com.ibm.as400.access.IFSFile;
import com.ibm.as400.access.IFSFileReader;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class FinalMailService {
    @Autowired
    IfsUtil ifsUtil;
    @Autowired
    ItextService service;

    public void sendMail() throws IOException, AS400SecurityException, MessagingException, DocumentException {
        IFSFile file = ifsUtil.getIfsFile("/home/UC36664103_inbound_edi.edi");
        byte[] bytes = readFromInputStream(file);
        service.getGraphClient(bytes);
    }

    private byte[] readFromInputStream(IFSFile inputStream) throws IOException, AS400SecurityException, DocumentException {
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
        Paragraph paragraph = new Paragraph(resultStringBuilder.toString());
        document.add(paragraph);
        document.close();
        return out.toByteArray();
    }

}
