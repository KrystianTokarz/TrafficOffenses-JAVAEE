package cqrs.command.api;

import cqrs.command.validator.command.CommandValidator;
import writemodel.Command;

import javax.ejb.Local;

@Local
public interface ValidatorResolver {

    CommandValidator resolve(Command command);
}
