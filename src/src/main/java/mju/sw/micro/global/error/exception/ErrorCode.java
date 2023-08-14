package mju.sw.micro.global.error.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

	// Common
	INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "Request Body를 통해 전달된 값이 유효하지 않습니다."),
	BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 에러가 발생했습니다"),
	NOT_FOUND(HttpStatus.NOT_FOUND, "요청한 리소스를 찾을 수 없습니다."),
	FORBIDDEN(HttpStatus.FORBIDDEN, "권한이 없는 접근입니다."),
	UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "인증이 필요한 접근입니다."),

	// Club
	INVALID_CLUB_ID(HttpStatus.BAD_REQUEST, "Request Body를 통해 전달된 학생 단체의 식별자가 유효하지 않습니다."),
	INVALID_CLUB_RECRUITMENT_ID(HttpStatus.BAD_REQUEST, "Request Body를 통해 전달된 학생 단체 모집 공고의 식별자가 유효하지 않습니다."),

	//User
	ALREADY_SIGN_UP_EMAIL(HttpStatus.BAD_REQUEST, "이미 가입된 이메일 입니다."),
	INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "인증 토큰이 유효하지 않습니다."),
	AUTH_NOT_FOUND(HttpStatus.UNAUTHORIZED, "등록되지 않은 계정입니다."),
	INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "회원 정보가 일치하지 않습니다."),
	TOKEN_NOT_EXPIRED(HttpStatus.BAD_REQUEST, "잘못된 토큰 정보이거나 토큰이 만료되지 않았습니다."),
	TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 토큰을 찾을 수 없습니다.");
	// Other Domain
	// ...

	private final HttpStatus status;
	private final String message;

}
