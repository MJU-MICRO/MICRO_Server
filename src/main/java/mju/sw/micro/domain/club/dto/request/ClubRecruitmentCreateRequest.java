package mju.sw.micro.domain.club.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ClubRecruitmentCreateRequest {

	@NotBlank(message = "모집 공고의 제목은 필수 값입니다.")
	private String title;

	@NotBlank(message = "모집 공고의 내용은 필수 값입니다.")
	private String content;

	@Positive(message = "모집 공고를 게시하는 학생 단체의 식별자는 양수여야 합니다.")
	private Long clubId;

	@Builder
	private ClubRecruitmentCreateRequest(String title, String content, Long clubId) {
		this.title = title;
		this.content = content;
		this.clubId = clubId;
	}

	public static ClubRecruitmentCreateRequest of(String title, String content, Long clubId) {
		return ClubRecruitmentCreateRequest.builder()
			.title(title)
			.content(content)
			.clubId(clubId)
			.build();
	}

	public ClubRecruitmentCreateServiceRequest toServiceRequest() {
		return ClubRecruitmentCreateServiceRequest
			.builder()
			.title(title)
			.content(content)
			.clubId(clubId)
			.build();
	}
}
