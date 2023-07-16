package mju.sw.micro.domain.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class LoginRequestDto {

	@NotBlank
	@Email
	@Pattern(regexp = "^[A-Za-z0-9._%+-]+@mju\\.ac\\.kr$", message = "이메일 도메인이 허용되지 않습니다.")
	private String email;
	@NotBlank
	private String password;
	@NotNull
	private Boolean isAutoLogin;

	public static LoginRequestDto of(String email, String password, Boolean isAutoLogin) {
		LoginRequestDto dto = new LoginRequestDto();
		dto.email = email;
		dto.password = password;
		dto.isAutoLogin = isAutoLogin;
		return dto;
	}

}
