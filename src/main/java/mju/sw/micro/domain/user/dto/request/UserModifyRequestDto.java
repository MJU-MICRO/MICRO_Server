package mju.sw.micro.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserModifyRequestDto {
	@NotBlank
	private String originPassword;
	private String updatePassword;
	@NotBlank
	private String phoneNumber;
	@NotBlank
	private String name;
	@NotBlank
	private String nickName;
	@NotBlank
	private String major;
	private String introduction;
}
