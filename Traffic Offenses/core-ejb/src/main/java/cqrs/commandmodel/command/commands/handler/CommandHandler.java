package cqrs.commandmodel.command.commands.handler;


public interface CommandHandler<C> {

    public void handle(C command);
}

