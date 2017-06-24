package cqrs.command.validator.command;

import cqrs.command.api.UserAccountValidatorHelper;
import cqrs.command.validator.helper.ValidationResult;
import infrastructure.annotations.Validator;
import writemodel.EditUserCommand;

import javax.ejb.EJB;
import javax.ejb.Stateless;

@Validator(name = "EditUserCommand")
@Stateless
public class EditUserCommandValidatorBean implements CommandValidator<EditUserCommand> {


    @EJB
    private UserAccountValidatorHelper userAccountValidatorBean;

    @Override
    public ValidationResult validate(EditUserCommand command) {
        return userAccountValidatorBean.validateUserEditAccount(command);
    }
}
