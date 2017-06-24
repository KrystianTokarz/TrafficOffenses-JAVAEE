package cqrs.command.bus;

import cqrs.command.api.AsynchronousCommandHandler;
import cqrs.command.api.CommandHandlerResolver;
import org.slf4j.Logger;
import writemodel.Command;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;


@MessageDriven(name = "ProjectQ",
        activationConfig = {
                @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
                @ActivationConfigProperty(propertyName = "destination", propertyValue = "queue/ProjectQ"),
                @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge") })
public class AsynchronousCommandBus implements MessageListener{

    @EJB
    private CommandHandlerResolver commandHandlerResolver;

    @Inject
    private Logger logger;

    @Override
    public void onMessage(Message message) {
        ObjectMessage msg = (ObjectMessage) message;
        try {
            Command command = (Command) msg.getObject();
            handleCommand(command);
        } catch (JMSException e) {
            logger.warn("Asynchronous command bus JSM Exception");
        }
    }

    private void handleCommand(Command command){
        try {
            AsynchronousCommandHandler commandHandler = (AsynchronousCommandHandler) commandHandlerResolver.resolve(command);
            commandHandler.handle(command);
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            logger.warn("Asynchronous command bus catch handler exception");
        }
    }
}
