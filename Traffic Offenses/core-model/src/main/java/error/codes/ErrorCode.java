package error.codes;

public enum ErrorCode {
    USER_NOT_FOUND(0),
    USER_NOT_ACTIVE(1),
    DRIVING_LICENSE_NUMBER(2),
    NON_VALIDATE_NAME(3),
    NON_VALIDATE_SURNAME(4),
    NON_VALIDATE_EMAIL(5),
    NON_VALIDATE_PESEL(6),
    NON_VALIDATE_PASSWORD(7),
    USER_IS_FOUND(8),
    SAVE_PUBLIC_USER(9),
    SAVE_PRIVATE_USER(10),
    SAVE_ADMINISTRATOR_USER(11),
    CAN_NOT_ADD_SELECTED_ROLE(12),
    CAN_NOT_DELETE_SELECTED_ROLE(13),
    NON_VALIDATE_ROLE(14);


    private final int code;

    ErrorCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }


}
