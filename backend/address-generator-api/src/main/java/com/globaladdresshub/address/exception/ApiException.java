package com.globaladdresshub.address.exception;

public class ApiException extends RuntimeException {

    private final ApiErrorCode code;

    public ApiException(ApiErrorCode code) {
        super(code.defaultMessage());
        this.code = code;
    }

    public ApiException(ApiErrorCode code, Throwable cause) {
        super(code.defaultMessage(), cause);
        this.code = code;
    }

    public ApiException(ApiErrorCode code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public ApiErrorCode code() {
        return code;
    }
}
