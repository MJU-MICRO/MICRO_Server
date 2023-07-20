package mju.sw.micro.domain.user.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginResponseDto {

	private String accessToken;
	private String refreshToken;

	public static LoginResponseDto of(String accessToken, String refreshToken) {
		return new LoginResponseDto(accessToken, refreshToken);
	}
}
