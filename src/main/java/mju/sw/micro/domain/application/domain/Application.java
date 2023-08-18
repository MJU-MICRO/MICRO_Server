package mju.sw.micro.domain.application.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import mju.sw.micro.domain.application.dto.ApplicationRequestDto;
import mju.sw.micro.global.security.CustomUserDetails;

import java.util.List;

@Entity
@Table(name = "applications")
@Getter
@Setter
public class Application {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private Long userId;
	@Column(nullable = false)
	private String recruitmentId;
	@ElementCollection
	private List<String> answers;
	private Boolean passStatus;
	private String grade;
	private String supportField;
	private Boolean isAttending;
	private Boolean isSubmit;

	public Application(ApplicationRequestDto requestDto, CustomUserDetails userDetails) {
		this.userId = userDetails.getUserId();
//		this.recruitmentId = requestDto.recruitmentId();
		this.answers = requestDto.getAnswers();
		this.passStatus = false;
		this.grade = requestDto.getGrade();
		this.supportField = requestDto.getSupportField();
		this.isAttending = requestDto.getIsAttending();
		this.isSubmit = requestDto.getIsSubmit();
	}

	public Application() {
	}
}

