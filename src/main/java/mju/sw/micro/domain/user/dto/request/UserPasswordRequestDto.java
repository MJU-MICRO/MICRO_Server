package mju.sw.micro.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserPasswordRequestDto {
	@NotBlank
	private String originPassword;
	@NotBlank
	private String updatePassword;
}
