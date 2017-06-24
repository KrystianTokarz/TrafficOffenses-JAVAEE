package cqrs.command.validator.command;

import api.exception.repository.user.UserNotActiveException;
import api.exception.repository.user.UserNotFoundException;
import api.querymodel.AdministratorDataFinder;
import cqrs.command.validator.helper.ValidationResult;
import cqrs.command.validator.helper.UserValidatorHelperBean;
import infrastructure.annotations.Validator;
import readmodel.SimpleUserDataVO;
import readmodel.UserDataWithMissingRolesVO;
import writemodel.AddNewRoleForUserCommand;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;

@Validator(name = "AddNewRoleForUserCommand")
@Stateless
public class AddNewRoleForUserValidatorBean implements CommandValidator<AddNewRoleForUserCommand> {

    @EJB
    private UserValidatorHelperBean userAccountValidatorHelper;

    @Override
    public ValidationResult validate(AddNewRoleForUserCommand command) {
        ValidationResult validationResult = new ValidationResult();
        validationResult = userAccountValidatorHelper.validateUserPesel(validationResult, command.getPesel());
        validationResult = userAccountValidatorHelper.validateUserRole(validationResult, command.getRoleName());
        return userAccountValidatorHelper.validateUserNotHaveSelectedRole(validationResult, command.getPesel(), command.getRoleName());
    }
}
