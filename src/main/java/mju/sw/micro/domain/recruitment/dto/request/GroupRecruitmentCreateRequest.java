package mju.sw.micro.domain.recruitment.dto.request;

import java.time.LocalDateTime;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import mju.sw.micro.domain.recruitment.domain.ActivityPeriod;

public record GroupRecruitmentCreateRequest(
	@Schema(description = "모집 공고를 게시하는 학생 단체의 식별자 (0 이하의 값 허용 x)")
	@Positive(message = "모집/ 공고를 게시하는 학생 단체의 식별자는 양수여야 합니다.")
	Long groupId,

	@Schema(description = "모집 공고 제목")
	@NotBlank(message = "모집 공고의 제목은 필수 값입니다.")
	String title,

	@Schema(description = "모집 설명글")
	@NotBlank(message = "모집 설명글은 필수 값입니다.")
	String description,

	@Schema(description = "활동 내용")
	@NotBlank(message = "활동 내용은 필수 값입니다.")
	String content,

	@Schema(description = "관심 분야(콤마로 구분해서 넘겨주세요)")
	@NotBlank(message = "관심 분야를 하나 이상 설정해주세요.")
	String fields,
	ActivityPeriod activityPeriod,
	LocalDateTime startDateTime,
	LocalDateTime endDateTime,
	@Schema(description = "사진에 대한 설명이 없는 경우 빈 문자열로 채워서 files의 길이와 fileDescriptions의 길이를 동일하게 맞춰 주세요.")
	List<String> fileDescriptions,

	@Schema(description = "활동 장소")
	@NotBlank(message = "활동 내용은 필수 값입니다.")
	String activePlace,

	@Schema(description = "제출 여부")
	boolean isSubmit,

	@Schema(description = "질문")
	List<String> questions,

	@Schema(description = "글자 수 제한")
	List<Integer> characterLimit
) {

	public GroupRecruitmentCreateServiceRequest toServiceRequest() {
		return new GroupRecruitmentCreateServiceRequest(groupId, title, description, content, fields, activityPeriod,
			startDateTime, endDateTime, fileDescriptions, activePlace, isSubmit, questions, characterLimit);
	}
}
