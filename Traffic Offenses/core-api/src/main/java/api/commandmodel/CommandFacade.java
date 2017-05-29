package api.commandmodel;

import api.exception.command.CommandNullException;
import api.exception.command.CommandValidationException;
import writemodel.Command;


public interface CommandFacade<C extends Command> {

    void sendCommand(C command) throws CommandNullException, CommandValidationException;
}
