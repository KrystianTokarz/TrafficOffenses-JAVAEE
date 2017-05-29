package cqrs.command.facade;

import api.commandmodel.CommandFacade;
import api.exception.command.CommandNullException;
import api.exception.command.CommandValidationException;
import cqrs.command.api.ValidatorResolver;
import cqrs.command.validator.ValidationResult;
import cqrs.command.validator.CommandValidator;
import writemodel.Command;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.*;


@Stateless
public class CommandFacadeBean implements CommandFacade {

    @EJB
    private ValidatorResolver validatorResolver;

    @Resource(mappedName = "java:/jms/queue/ProjectQ")
    private Queue systemQueue;

    @Inject
    private JMSContext context;

//    @Resource(mappedName = "java:/JmsXA")
//    private ConnectionFactory connectionFactory;
//
//    private Connection connection;
//    private MessageProducer publisher;
//    private Session session;

//    @PostConstruct
//    private void initialize(){
//        try {
//            connection = connectionFactory.createConnection();
//            session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
//            publisher = session.createProducer(systemQueue);
//        } catch (JMSException e) {
//            e.printStackTrace();
//        }
//
//    }

    @Override
    public void sendCommand(Command command) throws CommandNullException, CommandValidationException {

        if(command == null)
            throw new CommandNullException("Command is null");

        try {
            CommandValidator commandValidator = validatorResolver.resolve(command);
            ValidationResult validateResult = commandValidator.validate(command);
            if(!validateResult.getValidationResult()){
                throw new CommandValidationException("Command is not validate");
            }

        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            //zalogowac
        }finally {
            sendMessage(command);
        }
    }


    private void sendMessage(Command command){
//        try {
//            connection = connectionFactory.createConnection();
//            session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
//            publisher = session.createProducer(systemQueue);
            Destination destination = systemQueue;

            context.createProducer().send(destination, command);
//            connection.start();
//            ObjectMessage objectMessage = session.createObjectMessage(command);
//            publisher.send(objectMessage);
//
//        } catch (JMSException e) {
//            e.printStackTrace();
//        }

    }

}
