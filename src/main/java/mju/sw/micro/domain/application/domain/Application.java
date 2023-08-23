package mju.sw.micro.domain.application.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mju.sw.micro.domain.application.dto.ApplicationRequestDto;
import mju.sw.micro.domain.recruitment.domain.GroupRecruitment;
import mju.sw.micro.domain.user.domain.User;

@Entity
@Table(name = "applications")
@Getter
@NoArgsConstructor
public class Application {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@JsonIgnore
	@ManyToOne(optional = false)
	@JoinColumn(name = "user_id")
	private User user;

	@JsonIgnore
	@ManyToOne(optional = false)
	@JoinColumn(name = "recruitment_id")
	private GroupRecruitment recruitment;
	@ElementCollection
	private List<String> answers;
	private Boolean passStatus;
	private String grade;
	private String supportField;
	private Boolean isAttending;
	private Boolean isSubmit;

	public Application(ApplicationRequestDto requestDto) {
		this.answers = requestDto.getAnswers();
		this.grade = requestDto.getGrade();
		this.supportField = requestDto.getSupportField();
		this.isAttending = requestDto.getIsAttending();
		this.isSubmit = requestDto.getIsSubmit();
	}

	public void associateUser(User user) {
		this.user = user;
	}

	public void associateGroupRecruitment(GroupRecruitment groupRecruitment) {
		this.recruitment = groupRecruitment;
	}

	public void updateApplication(ApplicationRequestDto requestDto) {
		this.answers = requestDto.getAnswers();
		this.grade = requestDto.getGrade();
		this.supportField = requestDto.getSupportField();
		this.isAttending = requestDto.getIsAttending();
	}

	public void updatePassStatus(Boolean passStatus) {
		this.passStatus = passStatus;
	}

	public void updateIsSubmit(Boolean isSubmit) {
		this.isSubmit = isSubmit;
	}
}

