package mju.sw.micro.domain.application.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Getter
@Data
public class ApplicationRequestDto {
	@NotBlank
	private Long recruitmentId;
	private List<String> answers;
	private String grade;
	private String supportField;
	private Boolean isAttending;
	private Boolean isSubmit;
}
