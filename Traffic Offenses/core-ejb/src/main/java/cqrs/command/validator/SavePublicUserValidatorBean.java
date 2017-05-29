package cqrs.command.validator;


import infrastructure.annotations.Validator;
import writemodel.SavePublicUserCommand;

@Validator(name = "SavePublicUserCommand")
public class SavePublicUserValidatorBean implements CommandValidator<SavePublicUserCommand> {


    @Override
    public ValidationResult validate(SavePublicUserCommand command) {
        ValidationResult validationResult = new ValidationResult();
        validationResult.setValidationResult(true);
        //tutaj walidacja kazdego commanda
        return validationResult;
    }
}
