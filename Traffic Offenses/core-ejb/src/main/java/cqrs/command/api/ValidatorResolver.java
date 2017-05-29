package cqrs.command.api;

import cqrs.command.validator.CommandValidator;
import writemodel.Command;

import javax.ejb.Local;

@Local
public interface ValidatorResolver {

    CommandValidator resolve(Command command) throws ClassNotFoundException, IllegalAccessException, InstantiationException;
}
