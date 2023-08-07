package mju.sw.micro.domain.group.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GroupSimpleResponseDto {
	private Long id;
	private String groupName;
	private String logoImageUrl;
	private String introduction;
	private List<String> relationMajor;
	private List<String> relatedTag;
	private boolean isRecruit;
	private boolean isApprove;
	private String campus;
	private String subCategory;
}
