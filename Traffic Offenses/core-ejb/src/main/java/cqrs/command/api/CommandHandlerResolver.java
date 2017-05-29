package cqrs.command.api;

import writemodel.Command;

import javax.ejb.Local;

@Local
public interface CommandHandlerResolver {
    CommandHandler resolve(Command command) throws ClassNotFoundException, IllegalAccessException, InstantiationException;
}
