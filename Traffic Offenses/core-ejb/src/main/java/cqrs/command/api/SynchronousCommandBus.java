package cqrs.command.api;

import api.exception.command.SynchronousCommandException;
import writemodel.Command;

import javax.ejb.Local;

@Local
public interface SynchronousCommandBus {
    void sendCommand(Command command) throws SynchronousCommandException;
}
