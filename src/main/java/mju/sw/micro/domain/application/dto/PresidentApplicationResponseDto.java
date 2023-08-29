package mju.sw.micro.domain.application.dto;

import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import mju.sw.micro.domain.application.domain.Application;

@Getter
@Setter
@Data
public class PresidentApplicationResponseDto {
	private Long id;
	private Long recruitmentId;
	private List<String> answers;
	private Boolean passStatus;
	private String grade;
	private String supportField;
	private Boolean isAttending;
	private Boolean isSubmit;
	private String name;
	private String phoneNumber;
	private String studentId;
	private String major;

	public static PresidentApplicationResponseDto fromApplication(Application application) {
		PresidentApplicationResponseDto dto = new PresidentApplicationResponseDto();
		dto.setId(application.getId());
		dto.setRecruitmentId(application.getRecruitment().getId());
		dto.setAnswers(application.getAnswers());
		dto.setPassStatus(application.getPassStatus());
		dto.setGrade(application.getGrade());
		dto.setSupportField(application.getSupportField());
		dto.setIsAttending(application.getIsAttending());
		dto.setIsSubmit(application.getIsSubmit());
		dto.setName(application.getUser().getName());
		dto.setPhoneNumber(application.getUser().getPhoneNumber());
		dto.setStudentId(application.getUser().getStudentId());
		dto.setMajor(application.getUser().getMajor());
		return dto;
	}
}
