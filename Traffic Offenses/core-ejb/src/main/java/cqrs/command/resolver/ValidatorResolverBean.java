package cqrs.command.resolver;

import cqrs.command.api.ValidatorResolver;
import cqrs.command.validator.CommandValidator;
import infrastructure.annotations.Validator;
import org.reflections.Reflections;
import writemodel.Command;

import javax.ejb.Stateful;
import java.util.Set;


@Stateful
public class ValidatorResolverBean implements ValidatorResolver {


    @Override
    public CommandValidator resolve(Command command) throws ClassNotFoundException, IllegalAccessException, InstantiationException {

        Class foundCommandClass = Class.forName(command.getClass().getName());
        String commandName = foundCommandClass.getSimpleName();
        Reflections reflections = new Reflections("cqrs.command");

        Set<Class<?>> annotatedValidatorClasses = reflections.getTypesAnnotatedWith(Validator.class);
        CommandValidator result = null;

        for (Class<?> annotatedValidator : annotatedValidatorClasses) {
            Validator annotation = annotatedValidator.getAnnotation(Validator.class);
            String name = annotation.name();

            if(name.equals(commandName)) {
                result = createValidatorForSelectedCommand(annotatedValidator.getCanonicalName());
                break;
            }
        }
        return result;
    }


    public CommandValidator createValidatorForSelectedCommand(String validatorClassName) throws ClassNotFoundException, IllegalAccessException, InstantiationException {

        return (CommandValidator) Class.forName(validatorClassName).newInstance();
    }
}
