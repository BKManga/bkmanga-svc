package com.project.graduation.bkmangasvc.exception;

import com.project.graduation.bkmangasvc.constant.ErrorCode;
import com.project.graduation.bkmangasvc.model.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {
    private final Log logger = LogFactory.getLog(CustomExceptionHandler.class);

    @ExceptionHandler(value = CustomException.class)
    public ApiResponse<String> handleError(HttpServletRequest request, CustomException customException) {
        logger.error("Request: " + request.getRequestURL() + " raised " + customException);
        return ApiResponse.failureWithCode(customException.getErrorCode(), customException.getMessage());
    }

    @ExceptionHandler(value = ApiException.class)
    public ApiResponse<String> handleError(HttpServletRequest request, ApiException apiException) {
        logger.error("Request: " + request.getRequestURL() + " raised " + apiException);
        return ApiResponse.failureWithCode(apiException.getHttpStatus().toString(), apiException.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    public ApiResponse<String> handleError(HttpServletRequest req, Exception exception) {
        logger.error("Request: " + req.getRequestURL() + " raised " + exception.getClass().getName());
        return ApiResponse.failureWithCode(ErrorCode.UNKNOWN_ERROR.toString(), exception.getMessage());
    }
}
