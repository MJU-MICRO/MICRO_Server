package mju.sw.micro.global.error.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

	// Common
	INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "Request Body를 통해 전달된 값이 유효하지 않습니다."),

	// Club
	INVALID_CLUB_ID(HttpStatus.BAD_REQUEST, "Request Body를 통해 전달된 학생 단체의 식별자가 유효하지 않습니다."),
	INVALID_CLUB_RECRUITMENT_ID(HttpStatus.BAD_REQUEST, "Request Body를 통해 전달된 학생 단체 모집 공고의 식별자가 유효하지 않습니다."),

	//User
	ALREADY_SIGN_UP_EMAIL(HttpStatus.BAD_REQUEST, "이미 가입된 이메일 입니다."),
	INVALID_CODE(HttpStatus.UNAUTHORIZED, "인증 코드가 유효하지 않습니다.");
	// Other Domain
	// ...

	private final HttpStatus status;
	private final String message;

}
