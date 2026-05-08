package com.globaladdresshub.address.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.ResponseEntity;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

import com.globaladdresshub.address.model.ApiErrorResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(OutputCaptureExtension.class)
class GlobalExceptionHandlerTest {

    @Test
    void apiExceptionWithCauseLogsCauseStack(CapturedOutput output) {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getAttribute("requestId")).thenReturn("req-test");

        handler.handleApiException(
                new ApiException(ApiErrorCode.GENERATION_FAILED, new IllegalStateException("broken-data")),
                request
        );

        assertThat(output).contains("broken-data");
    }

    @Test
    void apiExceptionWithCauseReturnsSafeDefaultMessage() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getAttribute("requestId")).thenReturn("req-test");

        ResponseEntity<ApiErrorResponse> response = handler.handleApiException(
                new ApiException(
                        ApiErrorCode.GENERATION_FAILED,
                        "Invalid address data in data/us-address.json: region.phonePrefixes must not be empty",
                        new IllegalStateException("region.phonePrefixes must not be empty")
                ),
                request
        );

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().message()).isEqualTo(ApiErrorCode.GENERATION_FAILED.defaultMessage());
        assertThat(response.getBody().message()).doesNotContain("data/us-address.json");
        assertThat(response.getBody().message()).doesNotContain("region.phonePrefixes");
    }
}
