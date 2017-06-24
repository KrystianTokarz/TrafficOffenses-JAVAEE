package cqrs.command.validator.helper;

import api.exception.repository.user.UserNotActiveException;
import api.exception.repository.user.UserNotFoundException;
import cqrs.command.api.UserValidatorHelper;
import domainmodel.domain.user.Role;
import domainmodel.domain.user.User;
import domainmodel.domain.user.UserRole;
import domainmodel.embaddable.DrivingLicense;
import error.codes.ErrorCode;
import infrastructure.repository.api.UserRepository;
import org.slf4j.Logger;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Stateless
@LocalBean
public class UserValidatorHelperBean implements UserValidatorHelper {

    private final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private final String PESEL_PATTERN =
            "^\\d{11}$";

    private final String DRIVING_LICENSE_NUMBER_PATTERN =
            "\\d{5}\\/\\d{2}\\/\\d{4}$";

    private Pattern pattern;
    private Matcher matcher;

    @Inject
    private UserRepository userRepository;

    @Inject
    private Logger logger;


    public ValidationResult validateUserFirstName(ValidationResult validationResult, String firstName){
        if(firstName.length()<3 && firstName.length()>15){
            validationResult.setValidationResult(false);
            validationResult.addErrorCode(ErrorCode.NON_VALIDATE_NAME);
        }
        return validationResult;
    }

    public ValidationResult validateUserLastName(ValidationResult validationResult, String lastName){
        if(lastName.length()<3 && lastName.length()>15){
            validationResult.setValidationResult(false);
            validationResult.addErrorCode(ErrorCode.NON_VALIDATE_SURNAME);
        }
        return validationResult;
    }

    public ValidationResult validateUserPassword(ValidationResult validationResult, String passsword){
        if(passsword.length()<3 && passsword.length()>15){
            validationResult.setValidationResult(false);
            validationResult.addErrorCode(ErrorCode.NON_VALIDATE_PASSWORD);
        }
        return validationResult;
    }

    public ValidationResult validateUserEmail(ValidationResult validationResult, String email){
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);

        if(!matcher.matches()){
            validationResult.setValidationResult(false);
            validationResult.addErrorCode(ErrorCode.NON_VALIDATE_EMAIL);
        }
        return validationResult;
    }

    public ValidationResult validateUserRole(ValidationResult validationResult, String role){
        if(!(Role.ROLE_ADMINISTRATOR.toString().equals(role) || Role.ROLE_PRIVATE.toString().equals(role) )) {
            validationResult.setValidationResult(false);
            validationResult.addErrorCode(ErrorCode.NON_VALIDATE_ROLE);
        }
        return validationResult;
    }

    public ValidationResult  validateUserPesel(ValidationResult validationResult, String pesel) {
        pattern = Pattern.compile(PESEL_PATTERN);
        matcher = pattern.matcher(pesel);

        if(!matcher.matches()){
            validationResult.setValidationResult(false);
            validationResult.addErrorCode(ErrorCode.NON_VALIDATE_PESEL);
        }
        return validationResult;
    }



    public ValidationResult validateUserDrivingLicenseNumber(ValidationResult validationResult, String number) {
        pattern = Pattern.compile(DRIVING_LICENSE_NUMBER_PATTERN);
        matcher = pattern.matcher(number);
        if(!matcher.matches()){
            validationResult.setValidationResult(false);
            validationResult.addErrorCode(ErrorCode.DRIVING_LICENSE_NUMBER);
        }
        return validationResult;
    }


    public ValidationResult validateUserCreationLicenseDate(ValidationResult validationResult, Date date) {
        if(!date.before(new Date())){
            validationResult.setValidationResult(false);
            validationResult.addErrorCode(ErrorCode.DRIVING_LICENSE_NUMBER);
        }
        return validationResult;
    }

    public ValidationResult validateUserDrivingLicenseStatus(ValidationResult validationResult, DrivingLicense.DrivingLicenseStatus status) {
        if(!(DrivingLicense.DrivingLicenseStatus.ACTIVE.equals(status) ||
                DrivingLicense.DrivingLicenseStatus.PERMANENT_INACTIVE.equals(status) ||
                DrivingLicense.DrivingLicenseStatus.TEMPORARY_INACTIVE.equals(status))){
            validationResult.setValidationResult(false);
            validationResult.addErrorCode(ErrorCode.DRIVING_LICENSE_NUMBER);
        }
        return validationResult;
    }


    public ValidationResult validateUserAccountIsInSystemByPesel(ValidationResult validationResult, String pesel){
        try {
            userRepository.findUserByPesel(pesel);
        } catch (UserNotFoundException e) {
            validationResult.setValidationResult(false);
            validationResult.addErrorCode(ErrorCode.USER_NOT_FOUND);
            logger.info("Validator User Account - user is not found ");
        } catch (UserNotActiveException e) {
            logger.info("Validator User Account - user is not active ");
            validationResult.setValidationResult(false);
            validationResult.addErrorCode(ErrorCode.USER_NOT_ACTIVE);
        }
        return validationResult;
    }

    public ValidationResult validateUserAccountIsNotIntoSystemByPesel(ValidationResult validationResult, String pesel){
        try {
            userRepository.findUserByPesel(pesel);
            validationResult.setValidationResult(false);
            validationResult.addErrorCode(ErrorCode.USER_IS_FOUND);
        } catch (UserNotFoundException e) {
            logger.info("Validator User Account - user is not found ");
        } catch (UserNotActiveException e) {
            logger.info("Validator User Account - user is not active ");
            validationResult.setValidationResult(false);
            validationResult.addErrorCode(ErrorCode.USER_NOT_ACTIVE);
        }
        return validationResult;
    }

    public ValidationResult validateUserAccountIsInSystemByDrivingLicense(ValidationResult validationResult, String drivingLicenseNumber){
        try {
            userRepository.findUserByDrivingLicenseNumber(drivingLicenseNumber);
        } catch (UserNotFoundException e) {
            logger.info("Validator User Account - user is not found ");
            validationResult.setValidationResult(false);
            validationResult.addErrorCode(ErrorCode.USER_NOT_FOUND);
        } catch (UserNotActiveException e){
            logger.info("Validator User Account - user is not active ");
            validationResult.setValidationResult(false);
            validationResult.addErrorCode(ErrorCode.USER_NOT_ACTIVE);
        }
        return validationResult;
    }

    public ValidationResult validateUserAccountIsNotIntoSystemByDrivingLicense(ValidationResult validationResult, String drivingLicenseNumber){
        try {
            userRepository.findUserByDrivingLicenseNumber(drivingLicenseNumber);
            validationResult.setValidationResult(false);
            validationResult.addErrorCode(ErrorCode.USER_IS_FOUND);
        } catch (UserNotFoundException e) {
            logger.info("Validator User Account - user is not found ");
        } catch (UserNotActiveException e){
            logger.info("Validator User Account - user is not active ");
            validationResult.setValidationResult(false);
            validationResult.addErrorCode(ErrorCode.USER_NOT_ACTIVE);
        }
        return validationResult;
    }

    public ValidationResult validateUserNotHaveSelectedRole(ValidationResult validationResult, String pesel, String roleName){
        try {
             User user = userRepository.findUserByPesel(pesel);
             if(checkUserHasSelectedRole(user, roleName)){
                 validationResult.setValidationResult(false);
                 validationResult.addErrorCode(ErrorCode.CAN_NOT_DELETE_SELECTED_ROLE);
             }
        } catch (UserNotFoundException e) {
            logger.info("Validator User Account - user is not found ");
            validationResult.setValidationResult(false);
            validationResult.addErrorCode(ErrorCode.USER_NOT_FOUND);
        } catch (UserNotActiveException e){
            logger.info("Validator User Account - user is not active ");
            validationResult.setValidationResult(false);
            validationResult.addErrorCode(ErrorCode.USER_NOT_ACTIVE);
        }
        return validationResult;
    }

    public ValidationResult validateUserHaveSelectedRole(ValidationResult validationResult, String pesel, String roleName){
        try {
            User user = userRepository.findUserByPesel(pesel);
            if(!checkUserHasSelectedRole(user, roleName)){
                validationResult.setValidationResult(false);
                validationResult.addErrorCode(ErrorCode.CAN_NOT_ADD_SELECTED_ROLE);
            }
        } catch (UserNotFoundException e) {
            logger.info("Validator User Account - user is not found ");
            validationResult.setValidationResult(false);
            validationResult.addErrorCode(ErrorCode.USER_NOT_FOUND);
        } catch (UserNotActiveException e){
            logger.info("Validator User Account - user is not active ");
            validationResult.setValidationResult(false);
            validationResult.addErrorCode(ErrorCode.USER_NOT_ACTIVE);
        }
        return validationResult;
    }

    private boolean checkUserHasSelectedRole(User user, String role){
        boolean result = false;
        ArrayList<UserRole> userRoles = new ArrayList<>(user.getUserRoles());
        for (UserRole userRole: userRoles) {
            if(userRole.getRole().toString().equals(role)){
                result = true;
            }
        }
        return result;
    }
}
