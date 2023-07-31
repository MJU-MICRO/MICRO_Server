package mju.sw.micro.domain.group.dto;

import lombok.Getter;
import mju.sw.micro.domain.group.domain.Campus;

@Getter
public class StudentGroupRequestDto {
	private String groupName;
	private String logoImageUrl;
	private int establishedYear;
	private String numOfMember;
	private String introduction;
	private String[] relationMajor;
	private String[] relatedTag;
	private String[] activityTitle;
	private String[] activityContent;
	private boolean isRecruit;
	private boolean isApprove;
	private Campus campus;
	private String largeCategory;
	private String mediumCategory;
	private String smallCategory;
	private String subCategory;
}
