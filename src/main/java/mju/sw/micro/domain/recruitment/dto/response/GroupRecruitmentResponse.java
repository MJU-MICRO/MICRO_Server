package mju.sw.micro.domain.recruitment.dto.response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mju.sw.micro.domain.recruitment.domain.ActivityPeriod;
import mju.sw.micro.domain.recruitment.domain.GroupRecruitment;
import mju.sw.micro.domain.recruitment.domain.RecruitmentImage;

@NoArgsConstructor
@Getter
public class GroupRecruitmentResponse {
	private Long recruitmentId;
	private LocalDateTime startDateTime;
	private LocalDateTime endDateTime;
	private String title;
	private String description;
	private String content;
	private String fields;
	private ActivityPeriod activePeriod;
	private List<String> recruitmentImageUrl;
	private List<String> captions;

	private String activePlace;
	private boolean isSubmit;
	private List<String> questions;
	private List<Integer> characterLimit;
	private Long groupId;

	@Builder
	public GroupRecruitmentResponse(Long recruitmentId, LocalDateTime startDateTime, LocalDateTime endDateTime,
		String title, String description, String content, String fields, ActivityPeriod activePeriod,
		List<String> recruitmentImageUrl, List<String> captions, String activePlace, boolean isSubmit,
		List<String> questions, List<Integer> characterLimit, Long groupId) {
		this.recruitmentId = recruitmentId;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.title = title;
		this.description = description;
		this.content = content;
		this.fields = fields;
		this.activePeriod = activePeriod;
		this.recruitmentImageUrl = recruitmentImageUrl;
		this.captions = captions;
		this.activePlace = activePlace;
		this.isSubmit = isSubmit;
		this.questions = questions;
		this.characterLimit = characterLimit;
		this.groupId = groupId;
	}

	public static GroupRecruitmentResponse of(GroupRecruitment recruitment) {
		List<String> recruitmentImageUrl = new ArrayList<>();
		List<String> captions = new ArrayList<>();

		for (RecruitmentImage image : recruitment.getImageList()) {
			recruitmentImageUrl.add(image.getImageUrl());
			captions.add(image.getDescription());
		}

		return GroupRecruitmentResponse.builder()
			.recruitmentId(recruitment.getId())
			.startDateTime(recruitment.getStartDateTime())
			.endDateTime(recruitment.getEndDateTime())
			.title(recruitment.getTitle())
			.description(recruitment.getDescription())
			.content(recruitment.getContent())
			.fields(recruitment.getFields())
			.activePeriod(recruitment.getActivityPeriod())
			.recruitmentImageUrl(recruitmentImageUrl)
			.captions(captions)
			.groupId(recruitment.getGroup().getId())
			.isSubmit(recruitment.isSubmit())
			.activePlace(recruitment.getActivePlace())
			.questions(recruitment.getQuestions())
			.characterLimit(recruitment.getCharacterLimit())
			.build();
	}
}
