package infrastructure.creation;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import domainmodel.domain.event.Event;
import domainmodel.domain.user.User;
import domainmodel.embaddable.UserData;
import org.hibernate.result.Output;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.*;
import javax.mail.util.ByteArrayDataSource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MessageEmailBuilder {

    private Message message;

    private static final String EMAIL_NAME_FROM = "TrafficOffenses";
    private static final String PDF_NAME = "raport.pdf";

    public MessageEmailBuilder(Session session){
        message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(EMAIL_NAME_FROM));
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public MessageEmailBuilder recipient(String recipientEmail) throws MessagingException {
        this.message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(recipientEmail));
        return this;
    }

    public MessageEmailBuilder subject(String subject) throws MessagingException {
        this.message.setSubject(subject);
        return this;
    }

    public MessageEmailBuilder content(String emailMessage, byte[] pdfByteArray) throws MessagingException {

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(createMessageBodyPart(emailMessage));
        multipart.addBodyPart(createAttachmentPart(pdfByteArray));

        this.message.setContent(multipart);
        return this;
    }

    private MimeBodyPart createMessageBodyPart(String emailMessage) throws MessagingException {
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(emailMessage, "text/html; charset=utf-8");
        return  messageBodyPart;
    }

    private MimeBodyPart createAttachmentPart(byte[] pdfByteArray) throws MessagingException {
        DataSource source = new ByteArrayDataSource(pdfByteArray, "application/pdf");
        MimeBodyPart attachmentPart = new MimeBodyPart();
        attachmentPart.setDataHandler(new DataHandler(source));
        attachmentPart.setFileName(PDF_NAME);
        return attachmentPart;
    }

    public Message build() {
        return this.message;
    }




}
