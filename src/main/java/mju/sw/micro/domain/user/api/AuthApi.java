package mju.sw.micro.domain.user.api;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import mju.sw.micro.domain.user.application.AuthService;
import mju.sw.micro.domain.user.dto.request.CodeVerifyRequestDto;
import mju.sw.micro.domain.user.dto.request.EmailSendRequestDto;
import mju.sw.micro.domain.user.dto.request.SignUpRequestDto;
import mju.sw.micro.global.common.response.ApiResponse;

@Tag(name = "인증 API", description = "이메일 송신, 이메일 인증, 회원 가입, 로그인을 합니다.")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthApi {

	private final AuthService authService;

	@Operation(summary = "이메일 송신 및 인증 코드 저장")
	@PostMapping("/email")
	@ResponseStatus(HttpStatus.OK)
	public ApiResponse<String> sendEmailAndSaveCode(@Validated @RequestBody EmailSendRequestDto dto) {
		return authService.sendEmailAndSaveCode(dto);
	}

	@Operation(summary = "이메일 인증 코드 검증")
	@PostMapping("/email/auth")
	@ResponseStatus(HttpStatus.OK)
	public ApiResponse<Boolean> verifyCode(@Validated @RequestBody CodeVerifyRequestDto dto) {
		return authService.verifyCode(dto);
	}

	@Operation(summary = "회원가입")
	@PostMapping("/sign-up")
	@ResponseStatus(HttpStatus.CREATED)
	public ApiResponse<String> signUp(@Validated @RequestBody SignUpRequestDto dto) {
		return authService.signUp(dto);
	}

}
