package mju.sw.micro.domain.group.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupSimpleResponseDto {
	private Long id;
	private Long presidentId;
	private String groupName;
	private String logoImageUrl;
	private String introduction;
	private List<String> relationMajor;
	private List<String> relatedTag;
	private boolean isRecruit;
	private boolean isApprove;
	private String campus;
	private String largeCategory;
	private String mediumCategory;
	private String smallCategory;
	private String subCategory;
	private String numOfMember;
	private int establishedYear;

}
