package mju.sw.micro.domain.group.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import mju.sw.micro.domain.group.domain.Campus;

@Getter
public class StudentGroupRequestDto {
	@NotBlank
	private String groupName;
	private String logoImageUrl;
	private int establishedYear;
	private String numOfMember;
	@NotBlank
	private String introduction;
	private String[] relationMajor;
	private String[] relatedTag;
	private String[] activityTitle;
	private String[] activityContent;
	private boolean isRecruit = false;
	private boolean isApprove = false;
	@NotBlank
	private String campus;
	@NotBlank
	private String largeCategory;
	@NotBlank
	private String mediumCategory;
	@NotBlank
	private String smallCategory;
	@NotBlank
	private String subCategory;
}
