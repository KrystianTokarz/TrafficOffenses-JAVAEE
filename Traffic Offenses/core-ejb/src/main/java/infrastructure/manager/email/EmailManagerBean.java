package infrastructure.manager.email;

import domainmodel.embaddable.UserData;
import infrastructure.api.*;
import domainmodel.domain.event.Event;
import infrastructure.creation.MessageEmailBuilder;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.slf4j.Logger;

import java.io.IOException;
import java.io.StringWriter;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;


@Stateless
public class EmailManagerBean implements EmailManager {


    @EJB
    private PdfManager pdfManager;

    @EJB
    private PointsMandateCalculator pointsMandateCalculator;
    
    @EJB
    private AmountMandateCalculator amountMandateCalculator;

    @EJB
    private EmailConfigurator emailConfigurator;

    @EJB
    private EmailMessageTemplateManager emailMessageTemplateManager;

    @Inject
    private Logger logger;

    private Session session;

    private static final String TEMPLATE_PATH = "/email/email-html.vm";

    @PostConstruct
    private void init(){
        session = loadEmailSession();
    }

    private Session loadEmailSession(){
        Session session = null;
        try {
            session = emailConfigurator.loadEmailSession();
        } catch (IOException e) {
            logger.debug("Email Session configurator failed");
            e.printStackTrace();
        }
        return session;
    }

    @Override
    public void sendEmail(Event event) {
        try {
            Message message = createEmailMessage(event);
            Transport.send(message);
        } catch (MessagingException e) {
            logger.debug("Send email failed");
            e.printStackTrace();
        }
    }


    private Message createEmailMessage(Event event){

        UserData userData = event.getUserData();
        String messageBody = createMessageBody(userData);
        byte[] pdfAttachmentBody = createPdfAttachmentBody(event);
        Message message = null;

        MessageEmailBuilder messageEmailBuilder = new MessageEmailBuilder(session);
        try {
            message = messageEmailBuilder
                    .recipient(userData.getEmail())
                    .subject("Mandat")
                    .content(messageBody,pdfAttachmentBody)
                    .build();
        } catch (MessagingException e) {
            logger.debug("Email message build failed");
            e.printStackTrace();
        }
        return message;
    }

    private String createMessageBody(UserData userData){
        Template template = emailMessageTemplateManager.loadTemplate(TEMPLATE_PATH);
        VelocityContext velocityContext = addContextToTemplate(userData);
        return mergeContextWithTemplate(template, velocityContext);
    }

    private VelocityContext addContextToTemplate(UserData userData){
        VelocityContext velocityContext = new VelocityContext();
        velocityContext.put("firstName", userData.getFirstName());
        velocityContext.put("lastName", userData.getLastName());
        return velocityContext;
    }

    private String mergeContextWithTemplate(Template template, VelocityContext velocityContext){
        StringWriter writer = new StringWriter();
        template.merge( velocityContext, writer );
        return writer.toString();
    }


    private byte[] createPdfAttachmentBody(Event event){
        int mandateAmount = amountMandateCalculator.calculateMandateAmount(event.getEventItemList());
        int numberOfPointsInThisEvent = pointsMandateCalculator.calculateUserPoints(event);
        return pdfManager.createPdf(event,numberOfPointsInThisEvent,mandateAmount);
    }

}
