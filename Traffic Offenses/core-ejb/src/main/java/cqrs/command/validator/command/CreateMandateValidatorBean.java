package cqrs.command.validator.command;

import cqrs.command.validator.helper.UserValidatorHelperBean;
import cqrs.command.validator.helper.ValidationResult;
import infrastructure.annotations.Validator;
import infrastructure.repository.api.UserRepository;
import writemodel.CreateMandateCommand;

import javax.ejb.EJB;
import javax.ejb.Stateless;

@Validator(name = "CreateMandateCommand")
@Stateless
public class CreateMandateValidatorBean implements CommandValidator<CreateMandateCommand>  {

    @EJB
    private UserValidatorHelperBean userAccountValidatorHelper;


    @EJB
    private UserRepository userRepository;


    @Override
    public ValidationResult validate(CreateMandateCommand command) {

        ValidationResult validationResult = new ValidationResult();
        validationResult = userAccountValidatorHelper.validateUserDrivingLicenseNumber(validationResult,command.getDrivingLicenseNumber());
        validationResult = userAccountValidatorHelper.validateUserAccountIsInSystemByDrivingLicense(validationResult, command.getDrivingLicenseNumber());
        return validationResult;

    }
}
