package cqrs.command.facade;

import api.commandmodel.AsynchronousCommandFacade;
import api.exception.command.CommandValidationException;
import cqrs.command.api.ValidatorResolver;
import cqrs.command.validator.helper.ValidationResult;
import cqrs.command.validator.command.CommandValidator;
import writemodel.Command;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.*;


@Stateless
public class AsynchronousCommandFacadeBean implements AsynchronousCommandFacade {

    @EJB
    private ValidatorResolver validatorResolver;

    @Resource(mappedName = "java:/jms/queue/ProjectQ")
    private Queue systemQueue;

    @Inject
    private JMSContext context;

    @Override
    public void sendCommand(Command command) throws CommandValidationException {

        CommandValidator commandValidator = validatorResolver.resolve(command);
         if(commandValidator!=null) {
             ValidationResult validateResult = commandValidator.validate(command);
             if (!validateResult.getValidationResult()) {
                 throw new CommandValidationException("Command is not validate",validateResult.getErrorCodes());
             }
         }
        sendMessage(command);
    }


    private void sendMessage(Command command){
            Destination destination = systemQueue;
            context.createProducer().send(destination, command);
    }

}
