package mju.sw.micro.domain.application.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class ApplicationRequestDto {
	private List<String> answers;
	private String grade;
	private String supportField;
	private Boolean isAttending;
	private Boolean isSubmit;
}
