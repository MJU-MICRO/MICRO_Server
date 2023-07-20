package mju.sw.micro.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class LogoutRequestDto {
	@NotBlank
	private String accessToken;

}
