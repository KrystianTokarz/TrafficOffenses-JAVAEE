package cqrs.command.validator.helper;

import error.codes.ErrorCode;

import java.util.ArrayList;
import java.util.List;

public class ValidationResult {

    private boolean validationResult = true;

    private List<ErrorCode> errorCodes = new ArrayList<>();

    public List<ErrorCode> getErrorCodes() {
        return errorCodes;
    }

    public void addErrorCode(ErrorCode errorCode){
        this.errorCodes.add(errorCode);
    }

    public boolean getValidationResult() {
        return validationResult;
    }

    public void setValidationResult(boolean validationResult) {
        this.validationResult = validationResult;
    }
}
