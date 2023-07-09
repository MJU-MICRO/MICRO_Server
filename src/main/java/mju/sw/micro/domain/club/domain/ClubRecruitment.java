package mju.sw.micro.domain.club.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mju.sw.micro.domain.BaseEntity;
import mju.sw.micro.domain.club.dto.request.ClubRecruitmentUpdateServiceRequest;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClubRecruitment extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;

	private String content;

	@ManyToOne(fetch = FetchType.EAGER)
	private Club club;

	@Builder
	public ClubRecruitment(String title, String content) {
		this.title = title;
		this.content = content;
	}

	public void clearClub() {
		this.club = null;
	}

	public void setClub(Club club) {
		this.club = club;
	}

	public void update(ClubRecruitmentUpdateServiceRequest request) {
		this.title = request.title();
		this.content = request.content();
	}
}
