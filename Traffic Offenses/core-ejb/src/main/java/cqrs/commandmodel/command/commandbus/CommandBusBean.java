package cqrs.commandmodel.command.commandbus;

import cqrs.commandmodel.command.commands.Command;

import javax.ejb.Stateless;

@Stateless
public class CommandBusBean implements CommandBus{

    @Override
    public void send(Command command) {

    }
}
