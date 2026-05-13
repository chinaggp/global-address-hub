package com.globaladdresshub.address.exception;

import org.springframework.http.HttpStatus;

public enum ApiErrorCode {
    INVALID_COUNTRY(HttpStatus.BAD_REQUEST, "Country is required or has an invalid format."),
    UNSUPPORTED_COUNTRY(HttpStatus.BAD_REQUEST, "Country is not supported."),
    INVALID_REGION(HttpStatus.BAD_REQUEST, "Region has an invalid format."),
    UNSUPPORTED_REGION(HttpStatus.BAD_REQUEST, "Region is not supported for the selected country."),
    GENERATION_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "Address generation failed."),
    RATE_LIMITED(HttpStatus.TOO_MANY_REQUESTS, "Too many address generation requests. Please try again later."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "Requested API resource was not found.");

    private final HttpStatus status;
    private final String defaultMessage;

    ApiErrorCode(HttpStatus status, String defaultMessage) {
        this.status = status;
        this.defaultMessage = defaultMessage;
    }

    public HttpStatus status() {
        return status;
    }

    public String defaultMessage() {
        return defaultMessage;
    }
}
