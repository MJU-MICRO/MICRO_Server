package mju.sw.micro.domain.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class CodeVerifyRequestDto {

	@NotBlank
	@Email
	@Pattern(regexp = "^[A-Za-z0-9._%+-]+@mju\\.ac\\.kr$", message = "이메일 도메인이 허용되지 않습니다.")
	String email;
	@NotBlank
	String emailCode;

	public static CodeVerifyRequestDto of(String email, String emailCode) {
		CodeVerifyRequestDto dto = new CodeVerifyRequestDto();
		dto.email = email;
		dto.emailCode = emailCode;
		return dto;
	}
}
