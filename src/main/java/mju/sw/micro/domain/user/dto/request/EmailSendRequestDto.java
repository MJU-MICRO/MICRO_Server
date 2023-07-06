package mju.sw.micro.domain.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class EmailSendRequestDto {

	@NotBlank
	@Email
	String email;
}
