package cqrs.commandmodel.command.commandbus;

import cqrs.commandmodel.command.commands.Command;

public interface CommandBus {

    void send(Command command);
}
