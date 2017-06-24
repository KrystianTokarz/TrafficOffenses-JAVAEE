package cqrs.command.bus;

import api.exception.command.SynchronousCommandException;
import cqrs.command.api.SynchronousCommandBus;
import cqrs.command.api.AsynchronousCommandHandler;
import cqrs.command.api.CommandHandlerResolver;
import cqrs.command.api.SynchronousCommandHandler;
import org.slf4j.Logger;
import writemodel.Command;

import javax.ejb.*;
import javax.inject.Inject;


@Stateless
public class SynchronousCommandBusBean implements SynchronousCommandBus{

    @EJB
    private CommandHandlerResolver commandHandlerResolver;

    @Inject
    private Logger logger;


    @Override
    public void sendCommand(Command command) throws SynchronousCommandException {
            handleCommand(command);
    }

    private void handleCommand(Command command) throws SynchronousCommandException {
        try {
            SynchronousCommandHandler commandHandler = (SynchronousCommandHandler) commandHandlerResolver.resolve(command);
            commandHandler.handle(command);
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            logger.warn("Synchronous command bus catch handler exception");
        }
    }
}
