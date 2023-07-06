package mju.sw.micro.domain.user.api;

import lombok.RequiredArgsConstructor;
import mju.sw.micro.domain.user.application.UserService;
import mju.sw.micro.domain.user.dto.request.EmailSendRequestDto;
import mju.sw.micro.domain.user.dto.request.SignUpRequestDto;
import mju.sw.micro.domain.user.dto.request.CodeVerifyRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@PostMapping("/email")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Void> sendEmailAndSaveCode(@RequestBody EmailSendRequestDto dto) {
		userService.sendEmailAndSaveCode(dto);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/email/auth")
	@ResponseStatus(HttpStatus.OK)
	public boolean verifyCode(@RequestBody CodeVerifyRequestDto dto) {
		return userService.verifyCode(dto);
	}

	@PostMapping("/sign-up")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Void> signUp(
		@Validated @RequestBody SignUpRequestDto dto) {
		userService.signUp(dto);
		return ResponseEntity.ok().build();
	}

}
