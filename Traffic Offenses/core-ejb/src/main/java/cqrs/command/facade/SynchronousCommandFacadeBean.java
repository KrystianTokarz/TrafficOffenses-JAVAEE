package cqrs.command.facade;

import api.commandmodel.SynchronousCommandFacade;
import api.exception.command.CommandValidationException;
import api.exception.command.SynchronousCommandException;
import cqrs.command.api.SynchronousCommandBus;
import cqrs.command.api.ValidatorResolver;
import cqrs.command.bus.SynchronousCommandBusBean;
import cqrs.command.validator.command.CommandValidator;
import cqrs.command.validator.helper.ValidationResult;
import writemodel.Command;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jms.Destination;

@Stateless
public class SynchronousCommandFacadeBean implements SynchronousCommandFacade {

    @EJB
    private ValidatorResolver validatorResolver;

    @EJB
    private SynchronousCommandBus synchronousCommandBus;

    @Override
    public void sendCommand(Command command) throws CommandValidationException, SynchronousCommandException {

        CommandValidator commandValidator = validatorResolver.resolve(command);
        if(commandValidator!=null) {
            ValidationResult validateResult = commandValidator.validate(command);
            if (!validateResult.getValidationResult()) {
                throw new CommandValidationException("Command is not validate",validateResult.getErrorCodes());
            }
        }
        sendMessage(command);
    }


    private void sendMessage(Command command) throws SynchronousCommandException {
        synchronousCommandBus.sendCommand(command);
    }
}
