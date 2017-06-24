package cqrs.command.resolver;

import cqrs.command.api.ValidatorResolver;
import cqrs.command.validator.command.CommandValidator;
import infrastructure.annotations.Validator;
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
public class ValidatorResolverBean implements ValidatorResolver {

    private static final String REFLECTIONS_PACKAGE = "cqrs.command";

    private static final String CONTEXT_LOOKUP_PREFIX = "java:module/";

    @Inject
    private Logger logger;


    @Override
    public CommandValidator resolve(Command command) {

        Class foundCommandClass = null;
        try {
            foundCommandClass = Class.forName(command.getClass().getName());
        } catch (ClassNotFoundException e) {
            logger.info("Command validator not found");
            return null;
        }

        String commandName = foundCommandClass.getSimpleName();
        Reflections reflections = new Reflections(REFLECTIONS_PACKAGE);

        Set<Class<?>> annotatedValidatorClasses = reflections.getTypesAnnotatedWith(Validator.class);
        CommandValidator result = null;

        for (Class<?> annotatedValidator : annotatedValidatorClasses) {
            Validator annotation = annotatedValidator.getAnnotation(Validator.class);
            String name = annotation.name();

            if(name.equals(commandName)) {
                result = createValidatorForSelectedCommand(annotatedValidator.getSimpleName());
                break;
            }
        }
        return result;
    }


    public CommandValidator createValidatorForSelectedCommand(String validatorClassName){

        CommandValidator commandValidator = null;

        try {
            Context context = new InitialContext();
            commandValidator = (CommandValidator) context.lookup(CONTEXT_LOOKUP_PREFIX + validatorClassName);
        } catch (NamingException e) {
            logger.error("Can not create Command Handler for selected class name");
            e.printStackTrace();
        }

        return commandValidator;
    }
}
