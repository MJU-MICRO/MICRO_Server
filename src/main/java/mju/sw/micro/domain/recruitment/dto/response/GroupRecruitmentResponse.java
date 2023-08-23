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
	private Long id;
	private LocalDateTime startDateTime;
	private LocalDateTime endDateTime;
	private String title;
	private String description;
	private String content;
	private String fields;
	private ActivityPeriod activityPeriod;
	private List<String> recruitmentImageUrl;
	private List<String> captions;

	@Builder
	public GroupRecruitmentResponse(Long id, LocalDateTime startDateTime, LocalDateTime endDateTime, String title,
		String description, String content, String fields, ActivityPeriod activityPeriod,
		List<String> recruitmentImageUrl, List<String> captions) {
		this.id = id;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.title = title;
		this.description = description;
		this.content = content;
		this.fields = fields;
		this.activityPeriod = activityPeriod;
		this.recruitmentImageUrl = recruitmentImageUrl;
		this.captions = captions;
	}

	public static GroupRecruitmentResponse of(GroupRecruitment recruitment) {
		List<String> recruitmentImageUrl = new ArrayList<>();
		List<String> captions = new ArrayList<>();

		for (RecruitmentImage image : recruitment.getImageList()) {
			recruitmentImageUrl.add(image.getImageUrl());
			captions.add(image.getDescription());
		}

		return GroupRecruitmentResponse.builder()
			.id(recruitment.getId())
			.startDateTime(recruitment.getStartDateTime())
			.endDateTime(recruitment.getEndDateTime())
			.title(recruitment.getTitle())
			.description(recruitment.getDescription())
			.content(recruitment.getContent())
			.fields(recruitment.getFields())
			.activityPeriod(recruitment.getActivityPeriod())
			.recruitmentImageUrl(recruitmentImageUrl)
			.captions(captions)
			.build();
	}
}
