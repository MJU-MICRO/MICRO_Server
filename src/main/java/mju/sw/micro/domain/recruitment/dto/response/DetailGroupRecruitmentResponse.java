package mju.sw.micro.domain.recruitment.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mju.sw.micro.domain.recruitment.domain.ActivityPeriod;
import mju.sw.micro.domain.recruitment.domain.GroupRecruitment;
import mju.sw.micro.domain.recruitment.domain.RecruitmentImage;

@NoArgsConstructor
@Getter
public class DetailGroupRecruitmentResponse {
	private Long recruitmentId;
	private LocalDateTime startDateTime;
	private LocalDateTime endDateTime;
	private String title;
	private String description;
	private String content;
	private String fields;
	private ActivityPeriod activityPeriod;
	private List<RecruitmentImage> imageList;

	@Builder
	public DetailGroupRecruitmentResponse(Long recruitmentId, LocalDateTime startDateTime, LocalDateTime endDateTime,
		String title, String description, String content, String fields, ActivityPeriod activityPeriod,
		List<RecruitmentImage> imageList) {
		this.recruitmentId = recruitmentId;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.title = title;
		this.description = description;
		this.content = content;
		this.fields = fields;
		this.activityPeriod = activityPeriod;
		this.imageList = imageList;
	}

	public static DetailGroupRecruitmentResponse of(GroupRecruitment recruitment) {
		return DetailGroupRecruitmentResponse.builder()
			.recruitmentId(recruitment.getId())
			.startDateTime(recruitment.getStartDateTime())
			.endDateTime(recruitment.getEndDateTime())
			.title(recruitment.getTitle())
			.description(recruitment.getDescription())
			.content(recruitment.getContent())
			.fields(recruitment.getFields())
			.activityPeriod(recruitment.getActivityPeriod())
			.imageList(recruitment.getImageList())
			.build();
	}
}
