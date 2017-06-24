package cqrs.command.validator.command;


import cqrs.command.api.UserAccountValidatorHelper;
import cqrs.command.validator.helper.ValidationResult;
import infrastructure.annotations.Validator;
import writemodel.SavePrivateOrAdministratorUserCommand;

import javax.ejb.EJB;
import javax.ejb.Stateless;

@Validator(name = "SavePrivateOrAdministratorUserCommand")
@Stateless
public class SavePrivateOrAdministratorUserValidatorBean implements CommandValidator<SavePrivateOrAdministratorUserCommand> {

    @EJB
    private UserAccountValidatorHelper userAccountValidatorBean;

    @Override
    public ValidationResult validate(SavePrivateOrAdministratorUserCommand command) {
        return userAccountValidatorBean.validatePrivateOrAdministratorUser(command);
    }
}
