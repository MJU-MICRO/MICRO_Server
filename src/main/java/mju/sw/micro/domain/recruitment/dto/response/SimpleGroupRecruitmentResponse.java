package mju.sw.micro.domain.recruitment.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mju.sw.micro.domain.group.domain.StudentGroup;
import mju.sw.micro.domain.recruitment.domain.ActivityPeriod;
import mju.sw.micro.domain.recruitment.domain.GroupRecruitment;

@NoArgsConstructor
@Getter
public class SimpleGroupRecruitmentResponse {
	@Schema(description = "thumbnailUrl이 빈 값일 경우, 클라이언트에서 기본 이미지로 대체해주세요.")
	private String thumbnailUrl;
	private String title;
	private String description;
	private ActivityPeriod activityPeriod;

	private String groupName;
	private String numOfMember;
	private int establishedYear;
	private String campus;
	private boolean isRecruit;
	private String largeCategory;
	private String mediumCategory;
	private String smallCategory;
	private String subCategory;

	@Builder
	public SimpleGroupRecruitmentResponse(String thumbnailUrl, String title, String description,
		ActivityPeriod activityPeriod, String groupName, String numOfMember, int establishedYear, String campus,
		boolean isRecruit, String largeCategory, String mediumCategory, String smallCategory, String subCategory) {
		this.thumbnailUrl = thumbnailUrl;
		this.title = title;
		this.description = description;
		this.activityPeriod = activityPeriod;
		this.groupName = groupName;
		this.numOfMember = numOfMember;
		this.establishedYear = establishedYear;
		this.campus = campus;
		this.isRecruit = isRecruit;
		this.largeCategory = largeCategory;
		this.mediumCategory = mediumCategory;
		this.smallCategory = smallCategory;
		this.subCategory = subCategory;
	}

	public static SimpleGroupRecruitmentResponse of(GroupRecruitment recruitment, StudentGroup group) {
		String thumbnailUrl = "";
		if (!recruitment.getImageList().isEmpty()) {
			thumbnailUrl = recruitment.getImageList().get(0).getImageUrl();
		}

		return SimpleGroupRecruitmentResponse.builder()
			.thumbnailUrl(thumbnailUrl)
			.title(recruitment.getTitle())
			.description(recruitment.getDescription())
			.activityPeriod(recruitment.getActivityPeriod())
			.groupName(group.getGroupName())
			.numOfMember(group.getNumOfMember())
			.establishedYear(group.getEstablishedYear())
			.campus(group.getCampus())
			.isRecruit(group.isRecruit())
			.largeCategory(group.getLargeCategory())
			.mediumCategory(group.getMediumCategory())
			.subCategory(group.getSubCategory())
			.build();
	}
}
