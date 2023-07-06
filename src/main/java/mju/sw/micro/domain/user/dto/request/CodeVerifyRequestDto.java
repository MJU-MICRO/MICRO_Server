package mju.sw.micro.domain.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CodeVerifyRequestDto {

	@NotBlank
	@Email
	String email;
	@NotBlank
	String code;

	public static CodeVerifyRequestDto of(String email, String code) {
		CodeVerifyRequestDto dto = new CodeVerifyRequestDto();
		dto.email = email;
		dto.code = code;
		return dto;
	}
}
