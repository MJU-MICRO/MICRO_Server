package mju.sw.micro.global.error.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(AbstractApiException.class)
	public ResponseEntity<ErrorResponse> handleGlobalException(AbstractApiException exception,
		HttpServletRequest request) {
		return ResponseEntity.status(exception.getHttpStatus())
			.body(ErrorResponse.createErrorResponse(exception, request.getRequestURI()));
	}

}
