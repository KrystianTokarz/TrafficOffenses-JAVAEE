package cqrs.command.api;


import api.exception.command.SynchronousCommandException;
import writemodel.Command;

import javax.ejb.Local;
import javax.ejb.Remote;

@Local
public interface SynchronousCommandHandler<C extends Command> extends CommandHandler{

    void handle(C command) throws SynchronousCommandException;
}
