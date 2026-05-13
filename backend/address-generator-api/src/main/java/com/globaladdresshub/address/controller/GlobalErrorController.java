package com.globaladdresshub.address.controller;

import com.globaladdresshub.address.exception.ApiErrorCode;
import com.globaladdresshub.address.model.ApiErrorResponse;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class GlobalErrorController implements ErrorController {

    @RequestMapping(value = "/error", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiErrorResponse> error(HttpServletRequest request) {
        ApiErrorCode code = resolveCode(request);
        return ResponseEntity.status(code.status())
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ApiErrorResponse(false, code.name(), code.defaultMessage(), requestId(request)));
    }

    private ApiErrorCode resolveCode(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (status instanceof Integer statusCode && statusCode == 404) {
            return ApiErrorCode.NOT_FOUND;
        }
        return ApiErrorCode.GENERATION_FAILED;
    }

    private String requestId(HttpServletRequest request) {
        Object existing = request.getAttribute("requestId");
        return existing == null ? "req-" + System.currentTimeMillis() : existing.toString();
    }
}
