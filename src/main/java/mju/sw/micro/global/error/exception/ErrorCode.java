package mju.sw.micro.global.error.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ErrorCode {

	// Common
	INVALID_INPUT_VALUE(400, "C001", "Request Body를 통해 전달된 값이 유효하지 않습니다.");

	// Other Domain
	// ...

	private final int code;
	private final String status;
	private final String message;

}
