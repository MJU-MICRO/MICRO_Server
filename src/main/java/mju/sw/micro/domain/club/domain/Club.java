package mju.sw.micro.domain.club.domain;

import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mju.sw.micro.domain.BaseEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Club extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "club_id")
	private Long id;

	private String establishedYear;

	private int numberOfMember;

	private String description;

	private String content;

	@Enumerated(EnumType.STRING)
	private ClubClassification classification;

	private String relatedMajors;

	private String interests;

	@JsonIgnore
	@OneToMany(mappedBy = "club", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<ClubRecruitment> recruitmentList = new LinkedList<>();

	@Builder
	public Club(String establishedYear, int numberOfMember, String description, String content,
		ClubClassification classification, String relatedMajors, String interests) {
		this.establishedYear = establishedYear;
		this.numberOfMember = numberOfMember;
		this.description = description;
		this.content = content;
		this.classification = classification;
		this.relatedMajors = relatedMajors;
		this.interests = interests;
	}

	public void clearRecruitments() {
		this.recruitmentList.forEach(ClubRecruitment::clearClub);
		this.recruitmentList.clear();
	}

	public void addRecruitment(ClubRecruitment recruitment) {
		this.recruitmentList.add(recruitment);
		recruitment.setClub(this);
	}

	// TODO : 학생 단체 로고 이미지 처리(S3 / DB 저장), 단체 회장 연관 관계
}
