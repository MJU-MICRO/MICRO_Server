package mju.sw.micro.domain.application.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ApplicationResponseDto {
	private Long id;
	private Long userId;
	private String recruitmentId;
	private List<String> answers;
	private Boolean passStatus;
	private String grade;
	private String supportField;
	private Boolean isAttending;
	private Boolean isSubmit;
}
