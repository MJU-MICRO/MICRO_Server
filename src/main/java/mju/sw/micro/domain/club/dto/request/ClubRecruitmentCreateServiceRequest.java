package mju.sw.micro.domain.club.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mju.sw.micro.domain.club.domain.ClubRecruitment;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClubRecruitmentCreateServiceRequest {

	private String title;
	private String content;
	private Long clubId;

	@Builder
	private ClubRecruitmentCreateServiceRequest(String title, String content, Long clubId) {
		this.title = title;
		this.content = content;
		this.clubId = clubId;
	}

	public static ClubRecruitmentCreateServiceRequest of(String title, String content, Long clubId) {
		return ClubRecruitmentCreateServiceRequest.builder()
			.title(title)
			.content(content)
			.clubId(clubId)
			.build();
	}

	public ClubRecruitment toEntity() {
		return ClubRecruitment
			.builder()
			.title(this.getTitle())
			.content(this.getContent())
			.build();
	}
}
