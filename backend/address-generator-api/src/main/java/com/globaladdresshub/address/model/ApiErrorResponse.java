package com.globaladdresshub.address.model;

public record ApiErrorResponse(boolean success, String code, String message, String requestId) {
}
