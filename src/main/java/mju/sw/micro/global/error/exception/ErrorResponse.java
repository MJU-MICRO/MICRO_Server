package mju.sw.micro.global.error.exception;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {

	@JsonProperty("error_code")
	private ErrorCode errorCode;
	private String detail;

	@Builder
	private ErrorResponse(ErrorCode errorCode, String detail) {
		this.errorCode = errorCode;
		this.detail = detail;
	}

	public static ErrorResponse of(ErrorCode errorCode, String detail) {
		return ErrorResponse.builder()
			.errorCode(errorCode)
			.detail(detail)
			.build();
	}

}
