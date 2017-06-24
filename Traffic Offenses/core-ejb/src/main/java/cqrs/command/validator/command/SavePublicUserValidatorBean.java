package cqrs.command.validator.command;


import cqrs.command.api.UserAccountValidatorHelper;
import cqrs.command.validator.helper.ValidationResult;
import infrastructure.annotations.Validator;
import writemodel.SavePublicUserCommand;

import javax.ejb.EJB;
import javax.ejb.Stateless;

@Validator(name = "SavePublicUserCommand")
@Stateless
public class SavePublicUserValidatorBean implements CommandValidator<SavePublicUserCommand> {

    @EJB
    private UserAccountValidatorHelper userAccountValidatorBean;

    @Override
    public ValidationResult validate(SavePublicUserCommand command) {
        return userAccountValidatorBean.validatePublicUser(command);
    }
}
