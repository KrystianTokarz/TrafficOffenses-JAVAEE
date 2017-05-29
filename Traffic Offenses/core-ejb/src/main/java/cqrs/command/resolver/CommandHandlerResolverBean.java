package cqrs.command.resolver;

import cqrs.command.api.CommandHandler;
import cqrs.command.api.CommandHandlerResolver;
import infrastructure.annotations.Handler;
import org.reflections.Reflections;
import writemodel.Command;

import javax.ejb.Stateful;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Set;

@Stateful
public class CommandHandlerResolverBean implements CommandHandlerResolver {


    @Override
    public CommandHandler resolve(Command command) throws ClassNotFoundException, IllegalAccessException, InstantiationException {

        Class foundCommandClass = Class.forName(command.getClass().getName());
        String commandName = foundCommandClass.getSimpleName();
        Reflections reflections = new Reflections("cqrs.command");

        Set<Class<?>> annotatedValidatorClasses = reflections.getTypesAnnotatedWith(Handler.class);
        CommandHandler result = null;

        for (Class<?> annotatedValidator : annotatedValidatorClasses) {
            Handler annotation = annotatedValidator.getAnnotation(Handler.class);
            String name = annotation.name();



            if(name.equals(commandName)) {
                result = createCommandHandlerForSelectedCommand(annotatedValidator.getSimpleName());
                break;
            }
        }
        return result;
    }


    private CommandHandler createCommandHandlerForSelectedCommand(String commandHandlerClassName) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
  //default lookup pulls from jndi properties file
        CommandHandler commandHandler = null;

        try {
            Context  context = new InitialContext();
            commandHandler = (CommandHandler) context.lookup("java:module/" +commandHandlerClassName);
        } catch (NamingException e) {
            e.printStackTrace();
        }

        return commandHandler;
//        return (CommandHandler) Class.forName(validatorClassName).newInstance();
    }
}
