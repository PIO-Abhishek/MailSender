package com.example.mailSender.service;


import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.microsoft.graph.models.FileAttachment;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class PDFService {


    public void pdf(String text1) throws DocumentException, IOException {
       // String path="C:\\Users\\AbhishekJadhav\\Desktop\\new file.pdf";


        File file = new File("test.pdf");
        Document document=new Document();
        PdfWriter.getInstance(document,new FileOutputStream(file));

        document.open();
        Paragraph paragraph=new Paragraph(text1);
        document.add(paragraph);
        // Loading pdf file
        PDDocument document1 = PDDocument.load(file);
        PDFTextStripper pdfStripper = new PDFTextStripper();
        // Fetching PDF document
        String data = pdfStripper.getText(document1);
        System.out.println(data);
        // Closing the document
        document.close();



    }
    public ByteArrayInputStream createPdf(String text) throws DocumentException, IOException {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();

        final Document document = new Document();

        final PdfWriter pdfWriter = PdfWriter.getInstance(document, out);
        document.open();
        Paragraph paragraph=new Paragraph(text);
        document.add(paragraph);
        document.close();
        return new ByteArrayInputStream(out.toByteArray());


    }




  /*  public ByteArrayInputStream pdfGenerateForObjectReferenceMatrix(
            final ObjectContextMatrixPdfRequest objectContextMatrixPdfRequest) {


        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final String pdfTitle =
                PDF_TITLE.toValue() + objectContextMatrixPdfRequest.getObjectName();
        final Document document = new Document();
        try {
            final PdfWriter pdfWriter = PdfWriter.getInstance(document, out);
            final Rectangle rectangle = new Rectangle(600, 600);
            pdfWriter.setBoxSize(PDF_RECTANGLE.toValue(), rectangle);
            document.setPageSize(rectangle);
            document.open();
            addDocumentTitle(pdfTitle, document);
            final ObjectContextDiagramResponse objectContextDiagramResponse =
                    objectContextMatrixPdfRequest
                            .getObjectContextDiagramResponse();
            addTableDataWithList(objectContextDiagramResponse.getObjectInformation(),
                    ObjectInformation.class.getName(), document);
            addTableDataWithList(objectContextDiagramResponse.getFileInformation(), FileInformation.class.getName(),
                    document);
            addTableDataWithList(objectContextDiagramResponse.getModuleInformation(),
                    ModuleInformation.class.getName(), document);
            addTableDataWithList(objectContextDiagramResponse.getProcedureInformation(),
                    ProcedureInformation.class.getName(), document);
            document.close();
        } catch (final DocumentException ex) {
            ex.printStackTrace();
        }
        return new ByteArrayInputStream(out.toByteArray());
    }
*/
}
