package com.project.graduation.bkmangasvc.exception;

import com.project.graduation.bkmangasvc.constant.ErrorCode;
import com.project.graduation.bkmangasvc.model.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {
    private final Log logger = LogFactory.getLog(CustomExceptionHandler.class);

    @ExceptionHandler(value = CustomException.class)
    public ApiResponse<String> handleError(HttpServletRequest request, CustomException customException) {
        logExceptionRaise(request, customException);
        return ApiResponse.failureWithCode(customException.getErrorCode(), customException.getMessage());
    }

    @ExceptionHandler(value = ApiException.class)
    public ApiResponse<String> handleError(HttpServletRequest request, ApiException apiException) {
        logExceptionRaise(request, apiException);
        return ApiResponse.failureWithCode(apiException.getHttpStatus().toString(), apiException.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    public ApiResponse<String> handleError(HttpServletRequest request, Exception exception) {
        logExceptionRaise(request, exception.getClass().getName());
        return ApiResponse.failureWithCode(ErrorCode.UNKNOWN_ERROR.toString(), exception.getMessage());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ApiResponse<String> handleError(
            HttpServletRequest request,
            MethodArgumentNotValidException methodArgumentNotValidException
    ) {
        logExceptionRaise(request, methodArgumentNotValidException);
        return ApiResponse.failureWithCode(
                ErrorCode.NOT_VALIDATED_DATA.toString(),
                ErrorCode.NOT_VALIDATED_DATA.getMessage()
        );
    }

    private void logExceptionRaise(HttpServletRequest request, Object exception) {
        logger.error("Request: " + request.getRequestURL() + " raised " + exception);
    }

}
