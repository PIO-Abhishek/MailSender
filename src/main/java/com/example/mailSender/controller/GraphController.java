package com.example.mailSender.controller;

import com.example.mailSender.service.FinalGraphMailHardCodeService;
import com.example.mailSender.service.FinalMailService;
import com.example.mailSender.service.ItextService;
import com.example.mailSender.service.sendMailWithAttachment;
import com.ibm.as400.access.AS400SecurityException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

@RestController
public class GraphController {


    @Autowired
    FinalMailService service;

    @Autowired
    sendMailWithAttachment mail;
    @Autowired
    FinalGraphMailHardCodeService finalGraphMailHardCodeService;

    @Autowired
    ItextService itextService;


  /*  @PostMapping("sendmail")
    public String sendMail(@RequestParam String type) throws AS400SecurityException, IOException, MessagingException {

       if("GRAPH".equalsIgnoreCase(type))
        service.sendMailToAll();
        else if("SMTP".equalsIgnoreCase(type))
            mail.sendAttachedMail();
        return "successfull";
    }
*/
    @RequestMapping("local")
    public String localAttachment() throws IOException {
        finalGraphMailHardCodeService.getGraphClient();
        return "succcess";
    }


    @RequestMapping("pdf")
    public void pdf() throws IOException, AS400SecurityException, MessagingException, DocumentException {
        service.sendMail();


    }
  /*  @PostMapping(path = "/generatePdf", produces =
            MediaType.APPLICATION_JSON_VALUE, consumes =
            MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> pdfGenerateForObjectReferenceMatrix(
            @RequestBody final ObjectContextMatrixPdfRequest objectContextMatrixPdfRequest) {


//        LOGGER.info(OBJECT_CONTEXT_MATRIX_PDF_FOR_OBJECT.toValue(),
//                objectContextMatrixPdfRequest.getObjectName());
//        final long startMilliSeconds = System.currentTimeMillis();


        final ByteArrayInputStream pdfFile = objectReferenceService
                .pdfGenerateForObjectReferenceMatrix(objectContextMatrixPdfRequest);
        final HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition",
                "attachment; filename=Object_Context_Matrix_" + objectContextMatrixPdfRequest.getObjectName()
                        + ".pdf");
        final long endMilliSeconds = System.currentTimeMillis();

        LOGGER.info(API_PROCESS_COMPLETED,
                (endMilliSeconds - startMilliSeconds), MILLISECOND);
        return ResponseEntity.ok().headers(headers).body(new InputStreamResource(pdfFile));
    }
*/


}
