package cqrs.command.bus;

import cqrs.command.api.CommandHandler;
import cqrs.command.api.CommandHandlerResolver;
import writemodel.Command;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import java.io.Serializable;

@MessageDriven(name = "ProjectQ",
        activationConfig = {
                @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
                @ActivationConfigProperty(propertyName = "destination", propertyValue = "queue/ProjectQ"),
                @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge") })
public class CommandBusMD implements MessageListener{

    @EJB
    private CommandHandlerResolver commandHandlerResolver;

    @Override
    public void onMessage(Message message) {
        ObjectMessage msg = (ObjectMessage) message;
        try {
            Command command = (Command) msg.getObject();
            try {
                CommandHandler commandHandler = commandHandlerResolver.resolve(command);
                commandHandler.handle(command);
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
               //zalogowaac
            }
        } catch (JMSException e) {
            e.printStackTrace();
            //zalogowac
        }

    }
}
