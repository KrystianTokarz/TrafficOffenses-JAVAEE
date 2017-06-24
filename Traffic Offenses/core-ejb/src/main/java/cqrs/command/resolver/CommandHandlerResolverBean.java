package cqrs.command.resolver;

import cqrs.command.api.AsynchronousCommandHandler;
import cqrs.command.api.CommandHandler;
import cqrs.command.api.CommandHandlerResolver;
import infrastructure.annotations.Handler;
import org.reflections.Reflections;
import org.slf4j.Logger;
import writemodel.Command;

import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Set;

@Stateful
public class CommandHandlerResolverBean implements CommandHandlerResolver {

    private static final String REFLECTIONS_PACKAGE = "cqrs.command";

    private static final String CONTEXT_LOOKUP_PREFIX = "java:module/";

    @Inject
    private Logger logger;


    @Override
    public CommandHandler resolve(Command command) throws ClassNotFoundException {

        Class foundCommandClass = Class.forName(command.getClass().getName());
        String commandName = foundCommandClass.getSimpleName();
        Reflections reflections = new Reflections(REFLECTIONS_PACKAGE);

        Set<Class<?>> annotatedHandlerClasses = reflections.getTypesAnnotatedWith(Handler.class);
        CommandHandler result = null;

        for (Class<?> annotatedHandler : annotatedHandlerClasses) {
            Handler annotation = annotatedHandler.getAnnotation(Handler.class);
            String name = annotation.name();
            
            if(name.equals(commandName)) {
                result = createCommandHandlerForSelectedCommand(annotatedHandler.getSimpleName());
                break;
            }
        }
        return result;
    }


    private CommandHandler createCommandHandlerForSelectedCommand(String commandHandlerClassName){
        CommandHandler commandHandler = null;

        try {
            Context  context = new InitialContext();
            commandHandler = (CommandHandler) context.lookup(CONTEXT_LOOKUP_PREFIX + commandHandlerClassName);
        } catch (NamingException e) {
            logger.error("Can not create Command Handler for selected class name");
            e.printStackTrace();
        }

        return commandHandler;
    }
}
