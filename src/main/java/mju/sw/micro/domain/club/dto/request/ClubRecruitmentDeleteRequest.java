package mju.sw.micro.domain.club.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;

public record ClubRecruitmentDeleteRequest(
	@JsonProperty("club_id")
	@Schema(description = "모집 공고를 게시하는 학생 단체의 식별자 (0 이하의 값 허용 x)")
	@Positive(message = "모집 공고를 게시하는 학생 단체의 식별자는 양수여야 합니다.")
	Long clubId,

	@JsonProperty("recruitment_id")
	@Schema(description = "모집 공고의 식별자 (0 이하의 값 허용 x)")
	@Positive(message = "모집 공고의 식별자는 양수여야 합니다.")
	Long recruitmentId
) {
	public ClubRecruitmentDeleteServiceRequest toServiceRequest() {
		return new ClubRecruitmentDeleteServiceRequest(clubId(), recruitmentId());
	}
}
