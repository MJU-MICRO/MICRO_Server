package mju.sw.micro.domain.recruitment.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecruitmentImage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String imageUrl;
	private String description;

	@ManyToOne(fetch = FetchType.EAGER)
	@JsonIgnore
	private GroupRecruitment recruitment;

	private int file_number;

	public RecruitmentImage(String imageUrl, String description, GroupRecruitment recruitment, int file_number) {
		this.imageUrl = imageUrl;
		this.description = description;
		this.recruitment = recruitment;
		this.file_number = file_number;
	}

	public void clearRecruitment() {
		this.recruitment = null;
	}

	public void setRecruitment(GroupRecruitment recruitment) {
		this.recruitment = recruitment;
	}
}
