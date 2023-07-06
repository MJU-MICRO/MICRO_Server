package mju.sw.micro.domain.club.dto.request;

import mju.sw.micro.domain.club.domain.ClubRecruitment;

public record ClubRecruitmentCreateServiceRequest(String title, String content, Long clubId) {

	public ClubRecruitment toEntity() {
		return ClubRecruitment
			.builder()
			.title(title())
			.content(content())
			.build();
	}
}
