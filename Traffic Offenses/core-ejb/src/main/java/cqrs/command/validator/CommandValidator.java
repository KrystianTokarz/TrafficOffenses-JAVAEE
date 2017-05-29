package cqrs.command.validator;


import writemodel.Command;

public interface CommandValidator<C extends  Command> {

    ValidationResult validate(C command);
}
