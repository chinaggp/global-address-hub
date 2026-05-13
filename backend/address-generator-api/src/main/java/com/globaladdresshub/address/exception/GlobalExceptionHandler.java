package com.globaladdresshub.address.exception;

import com.globaladdresshub.address.model.ApiErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiErrorResponse> handleApiException(ApiException exception, HttpServletRequest request) {
        String requestId = requestId(request);
        if (exception.getCause() == null) {
            log.warn("API error requestId={} code={}", requestId, exception.code().name());
        } else {
            log.warn("API error requestId={} code={}", requestId, exception.code().name(), exception);
        }
        return ResponseEntity.status(exception.code().status())
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ApiErrorResponse(false, exception.code().name(), responseMessage(exception), requestId));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiErrorResponse> handleMissingParameter(
            MissingServletRequestParameterException exception,
            HttpServletRequest request
    ) {
        ApiErrorCode code = "country".equals(exception.getParameterName())
                ? ApiErrorCode.INVALID_COUNTRY
                : ApiErrorCode.INVALID_REGION;
        return handleApiException(new ApiException(code), request);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleNoResourceFound(
            NoResourceFoundException exception,
            HttpServletRequest request
    ) {
        return handleApiException(new ApiException(ApiErrorCode.NOT_FOUND), request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleUnexpected(Exception exception, HttpServletRequest request) {
        String requestId = requestId(request);
        log.error("Unexpected API error requestId={}", requestId, exception);
        ApiErrorCode code = ApiErrorCode.GENERATION_FAILED;
        return ResponseEntity.status(code.status())
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ApiErrorResponse(false, code.name(), code.defaultMessage(), requestId));
    }

    private String requestId(HttpServletRequest request) {
        Object existing = request.getAttribute("requestId");
        return existing == null ? "req-" + System.currentTimeMillis() : existing.toString();
    }

    private String responseMessage(ApiException exception) {
        return exception.getCause() == null ? exception.getMessage() : exception.code().defaultMessage();
    }
}
