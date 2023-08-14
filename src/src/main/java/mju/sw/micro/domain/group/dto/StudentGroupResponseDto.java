package mju.sw.micro.domain.group.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StudentGroupResponseDto {
	private Long id;
	private String groupName;
	private Long presidentId;
	private String logoImageUrl;
	private int establishedYear;
	private String numOfMember;
	private String introduction;
	private List<String> relationMajor;
	private List<String> relatedTag;
	private List<String> activityTitle;
	private List<String> activityContent;
	private boolean isRecruit;
	private boolean isApprove;
	private String campus;
	private String largeCategory;
	private String mediumCategory;
	private String smallCategory;
	private String subCategory;
}
