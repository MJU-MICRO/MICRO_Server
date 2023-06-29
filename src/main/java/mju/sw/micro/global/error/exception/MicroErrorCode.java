package mju.sw.micro.global.error.exception;


import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR),
	NOT_FOUND(HttpStatus.NOT_FOUND),
	FORBIDDEN(HttpStatus.FORBIDDEN),
	BAD_REQUEST(HttpStatus.BAD_REQUEST),
	UNAUTHORIZED(HttpStatus.UNAUTHORIZED);

	private final HttpStatus httpStatus;
	private final String errorMessage;

	private final String errorCode = "M-" + "0".repeat(Math.max(4-String.valueOf(this.ordinal() + 1).length(), 0)) + (this.ordinal() + 1);

	ErrorCode(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
		this.errorMessage = "";
	}

	public String getErrorName() {
		return this.name();
	}
}
