package cqrs.command.validator.command;

import cqrs.command.validator.helper.UserValidatorHelperBean;
import cqrs.command.validator.helper.ValidationResult;
import infrastructure.annotations.Validator;
import writemodel.AddNewRoleForUserCommand;
import writemodel.DeleteRoleForUserCommand;

import javax.ejb.EJB;
import javax.ejb.Stateless;

@Validator(name = "DeleteRoleForUserCommand")
@Stateless
public class DeleteRoleForUserValidatorBean implements CommandValidator<DeleteRoleForUserCommand> {

    @EJB
    private UserValidatorHelperBean userAccountValidatorBean;

    @Override
    public ValidationResult validate(DeleteRoleForUserCommand command) {
        ValidationResult validationResult = new ValidationResult();
        validationResult = userAccountValidatorBean.validateUserPesel(validationResult, command.getPesel());
        validationResult = userAccountValidatorBean.validateUserRole(validationResult, command.getRoleName());
        return userAccountValidatorBean.validateUserHaveSelectedRole(validationResult, command.getPesel(), command.getRoleName());
    }
}
