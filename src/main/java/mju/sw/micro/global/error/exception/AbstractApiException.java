package mju.sw.micro.global.error.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AbstractApiException extends RuntimeException implements ErrorCode {

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String errorName;
    private final String errorMessage;

    public AbstractApiException(MicroErrorCode errorCode, String msg) {
        httpStatus = errorCode.getHttpStatus();
        this.errorCode = errorCode.getErrorCode();
        errorName = errorCode.getErrorName();
        errorMessage = msg;
    }
}
