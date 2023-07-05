package mju.sw.micro.domain.club.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ClubRecruitmentCreateRequest {

	@Schema(description = "모집 공고 제목 (빈 문자열 허용 x)")
	@NotBlank(message = "모집 공고의 제목은 필수 값입니다.")
	private String title;

	@Schema(description = "모집 공고 내용 (빈 문자열 허용 x)")
	@NotBlank(message = "모집 공고의 내용은 필수 값입니다.")
	private String content;

	@Schema(description = "모집 공고를 게시하는 학생 단체의 식별자 (0 이하의 값 허용 x)")
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
