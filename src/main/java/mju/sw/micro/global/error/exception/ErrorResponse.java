package mju.sw.micro.global.error.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorResponse {

    private String timeStamp;
    private HttpStatus httpStatus;
    private String errorCode;
    private String errorName;
    private String errorMessage;
    private String path;

    public static ErrorResponse createErrorResponse(ErrorCode errorCode, String path) {
        return new ErrorResponse(LocalDateTime.now().toString(),errorCode.getHttpStatus(),errorCode.getErrorCode()
                , errorCode.getErrorName(), errorCode.getErrorMessage(),path);
    }

    @Override
    public String toString() {
        return "{" +
                "timeStamp=" + timeStamp +
                ", httpStatus=" + httpStatus +
                ", errorCode='" + errorCode + '\'' +
                ", errorName='" + errorName + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
