package mju.sw.micro.domain.recruitment.dto.request;

import java.time.LocalDateTime;
import java.util.List;

import mju.sw.micro.domain.recruitment.domain.ActivityPeriod;
import mju.sw.micro.domain.recruitment.domain.GroupRecruitment;

public record GroupRecruitmentCreateServiceRequest(
	Long groupId,
	String title,
	String description,
	String content,
	String fields,
	ActivityPeriod activityPeriod,
	LocalDateTime startDateTime,
	LocalDateTime endDateTime,
	List<String> fileDescriptions,
	String activePlace,
	boolean isSubmit,
	List<String> questions,
	List<Integer> characterLimit) {

	public GroupRecruitment toEntity() {
		return GroupRecruitment.builder()
			.title(title)
			.description(description)
			.content(content)
			.fields(fields)
			.activityPeriod(activityPeriod)
			.startDateTime(startDateTime)
			.endDateTime(endDateTime)
			.isSubmit(isSubmit)
			.questions(questions)
			.characterLimit(characterLimit)
			.build();
	}
}
