package api.exception.command;

import error.codes.ErrorCode;

import java.util.ArrayList;
import java.util.List;

public class CommandValidationException extends Exception {

    public List<ErrorCode> errorCodes = new ArrayList<>();

    public CommandValidationException(String message,List<ErrorCode> errorCodes){
        super(message);
        this.errorCodes = errorCodes;
    }

    public List<ErrorCode> getErrorCodes() {
        return errorCodes;
    }
}
