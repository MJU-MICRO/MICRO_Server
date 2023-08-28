package mju.sw.micro.domain.application.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class ApplicationRequestDto {
	@NotNull
	private Long recruitmentId;
	@NotBlank
	private String grade;
	@NotNull
	private Boolean isAttending;
	@NotNull
	private Boolean isSubmit;
	private List<String> answers;
	private String supportField;
}
