package cqrs.command.api;


import writemodel.Command;

import javax.ejb.Local;
import javax.ejb.Remote;

@Local
public interface AsynchronousCommandHandler<C extends Command> extends CommandHandler{

    void handle(C command);
}
