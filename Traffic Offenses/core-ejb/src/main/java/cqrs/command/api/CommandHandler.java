package cqrs.command.api;


import writemodel.Command;

import javax.ejb.Local;
import javax.ejb.Remote;

@Remote
public interface CommandHandler<C extends Command> {

    public void handle(C command);
}
