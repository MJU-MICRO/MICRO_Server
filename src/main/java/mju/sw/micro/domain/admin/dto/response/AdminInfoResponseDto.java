package mju.sw.micro.domain.admin.dto.response;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AdminInfoResponseDto {
	private String name;
	private LocalDate registrationDate;
	private String phoneNumber;
	private String email;
	private String profileImageUrl;
}
