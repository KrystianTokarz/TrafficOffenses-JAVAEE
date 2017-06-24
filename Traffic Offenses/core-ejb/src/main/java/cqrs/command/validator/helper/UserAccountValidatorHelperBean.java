package cqrs.command.validator.helper;

import cqrs.command.api.UserAccountValidatorHelper;
import domainmodel.domain.user.User;
import domainmodel.embaddable.DrivingLicense;
import writemodel.EditUserCommand;
import writemodel.SavePrivateOrAdministratorUserCommand;
import writemodel.SavePublicUserCommand;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.Date;

@Stateless
public class UserAccountValidatorHelperBean implements UserAccountValidatorHelper {

    @EJB
    private UserValidatorHelperBean userValidatorHelper;


    @Override
    public ValidationResult validatePublicUser(SavePublicUserCommand command){

        ValidationResult validationResult = new ValidationResult();
        validationResult =validateUserAccountWithoutPassword(validationResult,
                command.getFirstName(), command.getLastName(), command.getPesel(), command.getEmail());

        validationResult = validateDrivingLicense(validationResult, command.getDrivingLicenseNumber(),
                command.getDrivingLicenseCreationDate(), command.getDrivingLicenseStatus());

        validationResult = userValidatorHelper.validateUserAccountIsNotIntoSystemByPesel(validationResult,command.getPesel());
        validationResult = userValidatorHelper.validateUserAccountIsNotIntoSystemByDrivingLicense(validationResult,command.getDrivingLicenseNumber());
        return validationResult;
    }

    @Override
    public ValidationResult validatePrivateOrAdministratorUser(SavePrivateOrAdministratorUserCommand command){
        ValidationResult validationResult = new ValidationResult();

        validationResult =validateUserAccountWithPassword(validationResult,
                command.getFirstName(), command.getLastName(), command.getPesel(), command.getEmail(),command.getPassword());
        validationResult = userValidatorHelper.validateUserAccountIsNotIntoSystemByPesel(validationResult,command.getPesel());

        if(command.isHasDrivingLicenseNumber()){
            validationResult = validateDrivingLicense(validationResult, command.getDrivingLicenseNumber(),
                    command.getDrivingLicenseCreationDate(), command.getDrivingLicenseStatus());
            validationResult = userValidatorHelper.validateUserAccountIsNotIntoSystemByDrivingLicense(validationResult,command.getDrivingLicenseNumber());
        }
        return  validationResult;

    }

    @Override
    public ValidationResult validateUserEditAccount(EditUserCommand command) {
        ValidationResult validationResult = new ValidationResult();

        User user = command.getUser();
        if(user.getPassword() != null)
            validationResult = validateUserAccountWithPassword(validationResult, user.getFirstName(), user.getLastName(), user.getPesel(), user.getEmail(), user.getPassword());
        else
            validationResult = validateUserAccountWithoutPassword(validationResult, user.getFirstName(), user.getLastName(), user.getPesel(), user.getEmail());
        return validationResult;
    }

    private ValidationResult validateUserAccountWithoutPassword(ValidationResult validationResult, String firstName, String lastName, String pesel, String email){
        validationResult = userValidatorHelper.validateUserFirstName(validationResult,firstName);
        validationResult = userValidatorHelper.validateUserLastName(validationResult,lastName);
        validationResult = userValidatorHelper.validateUserPesel(validationResult,pesel);
        validationResult = userValidatorHelper.validateUserEmail(validationResult,email);
        return validationResult;
    }

    private ValidationResult validateUserAccountWithPassword(ValidationResult validationResult,String firstName, String lastName, String pesel, String email, String password){
        validationResult = validateUserAccountWithoutPassword(validationResult, firstName, lastName, pesel, email);
        validationResult = userValidatorHelper.validateUserPassword(validationResult,password);
     return validationResult;
    }

    private ValidationResult validateDrivingLicense(ValidationResult validationResult, String number, Date creationDate, DrivingLicense.DrivingLicenseStatus status){
        validationResult = userValidatorHelper.validateUserCreationLicenseDate(validationResult, creationDate);
        validationResult = userValidatorHelper.validateUserDrivingLicenseNumber(validationResult,number);
        validationResult = userValidatorHelper.validateUserDrivingLicenseStatus(validationResult, status);
        return validationResult;
    }
}
