package api.commandmodel;

import api.exception.command.CommandValidationException;
import api.exception.command.SynchronousCommandException;
import writemodel.Command;

import javax.ejb.Remote;

@Remote
public interface SynchronousCommandFacade<C extends Command> {

    void sendCommand(C command) throws CommandValidationException,SynchronousCommandException;
}
