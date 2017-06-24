package api.exception.command;

import error.codes.ErrorCode;

import java.util.ArrayList;
import java.util.List;

public class SynchronousCommandException extends Exception {

    public ErrorCode errorCode;

    public SynchronousCommandException(String message,ErrorCode errorCode){
        super(message);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
