package mju.sw.micro.domain.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class SignUpRequestDto {
	@NotBlank
	@Email
	private String email;
	@NotBlank
	private String password;
	@NotBlank
	private String name;
	@NotBlank
	private String nickName;
	@NotBlank
	private String studentId;
	@NotBlank
	private String major;
	@NotBlank
	private String interest;
	@NotBlank
	private String phoneNumber;
	private String introduction;
	private Boolean notification;
	@NotBlank
	String code;

}
