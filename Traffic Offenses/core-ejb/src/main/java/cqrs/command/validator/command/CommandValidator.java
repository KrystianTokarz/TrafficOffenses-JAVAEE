package cqrs.command.validator.command;


import cqrs.command.validator.helper.ValidationResult;
import writemodel.Command;

public interface CommandValidator<C extends  Command> {

    ValidationResult validate(C command);
}
