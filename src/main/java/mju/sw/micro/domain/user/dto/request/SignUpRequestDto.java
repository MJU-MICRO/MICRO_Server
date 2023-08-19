package mju.sw.micro.domain.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class SignUpRequestDto {

	@NotBlank
	String code;
	@NotBlank
	@Email
	@Pattern(regexp = "^[A-Za-z0-9._%+-]+@mju\\.ac\\.kr$", message = "이메일 도메인이 허용되지 않습니다.")
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
	private String phoneNumber;
	private String introduction;
	private Boolean notification;

	public static SignUpRequestDto of(String email, String password, String name, String nickName, String studentId,
		String major, String phoneNumber, String introduction,
		Boolean notification, String code) {
		SignUpRequestDto dto = new SignUpRequestDto();
		dto.email = email;
		dto.password = password;
		dto.name = name;
		dto.nickName = nickName;
		dto.studentId = studentId;
		dto.major = major;
		dto.phoneNumber = phoneNumber;
		dto.introduction = introduction;
		dto.notification = notification;
		dto.code = code;
		return dto;
	}

}
