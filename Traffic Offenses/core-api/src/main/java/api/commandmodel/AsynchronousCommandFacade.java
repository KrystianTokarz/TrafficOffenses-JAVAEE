package api.commandmodel;

import api.exception.command.CommandValidationException;
import writemodel.Command;

import javax.ejb.Remote;

@Remote
public interface AsynchronousCommandFacade<C extends Command> {

    void sendCommand(C command) throws CommandValidationException;
}
