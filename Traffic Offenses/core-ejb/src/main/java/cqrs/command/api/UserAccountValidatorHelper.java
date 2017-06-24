package cqrs.command.api;

import cqrs.command.validator.helper.ValidationResult;
import writemodel.EditUserCommand;
import writemodel.SavePrivateOrAdministratorUserCommand;
import writemodel.SavePublicUserCommand;

import javax.ejb.Local;

@Local
public interface UserAccountValidatorHelper {
    ValidationResult validatePublicUser(SavePublicUserCommand command);

    ValidationResult validatePrivateOrAdministratorUser(SavePrivateOrAdministratorUserCommand command);

    ValidationResult validateUserEditAccount(EditUserCommand command);
}
