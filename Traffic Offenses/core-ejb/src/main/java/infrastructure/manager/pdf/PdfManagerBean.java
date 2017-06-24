package infrastructure.manager.pdf;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import domainmodel.domain.event.Event;
import domainmodel.embaddable.UserData;
import infrastructure.api.PdfManager;
import org.slf4j.Logger;
import utility.DateFormatter;


import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.*;
import java.util.Date;

@Stateless
public class PdfManagerBean implements PdfManager {

    @Inject
    private Logger logger;

    @Override
    public byte[] createPdf(Event event, long numberOfPoints, long amount){

        ByteArrayOutputStream bOutput = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(bOutput);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        Table table = createPdfMandateTable(event, numberOfPoints, amount);
        Paragraph mainParagraph = createMainParagraph();
        Paragraph footerParagraph = createFooterParagraph();

        document.add(mainParagraph);
        document.add(table);
        document.add(footerParagraph);

        document.close();
        return bOutput.toByteArray();
    }


    private Paragraph createMainParagraph(){
        PdfFont boldFont = null;
        try {
            boldFont = PdfFontFactory.createFont(FontConstants.TIMES_BOLD);
        } catch (IOException e) {
            logger.debug("Create PDF font exception");
            e.printStackTrace();
        }
        Text title = new Text("Raport").setFont(boldFont).setFontSize(25f);
        return new Paragraph().add(title).setTextAlignment(TextAlignment.CENTER);
    }

    private Table createPdfMandateTable(Event event,long numberOfPoints, long amount ){

        PdfFont normalFont = null;
        try {
            normalFont = PdfFontFactory.createFont(FontConstants.TIMES_ROMAN, "Cp1250");
        } catch (IOException e) {
            e.printStackTrace();
        }
        UserData userData = event.getUserData();
        String creationDate = formatDateToString(event.getCreationDate());
        Table table = new  Table(new float[]{1});
        table.setHorizontalAlignment(HorizontalAlignment.CENTER);
        table.addCell(new Paragraph(new Text("Mandat Karny").setFont(normalFont)).setBold().setMargin(10).setFontSize(15f));
        table.addCell(new Paragraph(new Text("Na podstawie art 95 & 1 i art. 96 & 1 z ustawy z dn. 24.08.01r. - Kodeks postepowania" +
                "w sprawach o wykroczenia nałożono mandat karny w wysokości:  " + amount + " zł")).setFont(normalFont).setFont(normalFont).setBold().setMargin(10).setFontSize(10f));
        table.addCell(new Paragraph("Imie i nazwisko: " + userData.getFirstName() + " " + userData.getLastName()).setFont(normalFont).setBold().setMargin(10).setFontSize(10f).setFontSize(12f));
        table.addCell(new Paragraph("Pesel: " + userData.getPesel()).setFont(normalFont).setBold().setMargin(10).setFontSize(10f).setFontSize(12f));
        table.addCell(new Paragraph("Liczba punktów: " + numberOfPoints).setFont(normalFont).setBold().setMargin(10).setFontSize(10f).setFontSize(12f));
        table.addCell(new Paragraph("Data:" + creationDate).setFont(normalFont).setBold().setMargin(10).setFontSize(10f).setFontSize(12f));
        return table;
    }

    private Paragraph createFooterParagraph(){
        PdfFont boldFont = null;
        try {
            boldFont = PdfFontFactory.createFont(FontConstants.TIMES_BOLD);
        } catch (IOException e) {
            logger.debug("Create PDF font exception");
            e.printStackTrace();
        }
        Text title =  new Text("Raport został automatycznie wygenerowany po wystawieniu mandatu.").setFont(boldFont).setFontSize(10f);
        return new Paragraph().add(title);
    }

    private String formatDateToString(Date date){
        return  DateFormatter.formatDate(date,"yyyy-MM-dd");
    }
}