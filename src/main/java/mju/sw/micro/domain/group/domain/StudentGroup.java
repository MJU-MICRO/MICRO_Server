package mju.sw.micro.domain.group.domain;

import jakarta.persistence.*;
import lombok.Getter;
import mju.sw.micro.domain.group.dto.StudentGroupRequestDto;
import mju.sw.micro.global.security.CustomUserDetails;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name = "student_groups")
@Getter
public class StudentGroup {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long presidentId;
	@Column(nullable = false)
	private String groupName;
	private String logoImageUrl;
	private int establishedYear;
	private String numOfMember;
	@Column(nullable = false)
	private String introduction;
	private String[] relationMajor;
	private String[] relatedTag;
	private String[] activityTitle;
	private String[] activityContent;
	private boolean isRecruit;
	private boolean isApprove;
	@Column(nullable = false)
	private String campus;
	@Column(nullable = false)
	private String largeCategory;
	@Column(nullable = false)
	private String mediumCategory;
	@Column(nullable = false)
	private String smallCategory;
	@Column(nullable = false)
	private String subCategory;

	public StudentGroup(StudentGroupRequestDto requestDto, CustomUserDetails userDetails, String imageUrl) {
		this.presidentId = userDetails.getUserId();
		this.groupName = requestDto.getGroupName();
		this.logoImageUrl = imageUrl;
		this.establishedYear = requestDto.getEstablishedYear();
		this.numOfMember = requestDto.getNumOfMember();
		this.introduction = requestDto.getIntroduction();
		this.relationMajor = requestDto.getRelationMajor();
		this.relatedTag = requestDto.getRelatedTag();
		this.activityTitle = requestDto.getActivityTitle();
		this.activityContent = requestDto.getActivityContent();
		this.isRecruit = requestDto.isRecruit();
		this.isApprove = requestDto.isApprove();
		this.campus = requestDto.getCampus();
		this.largeCategory = requestDto.getLargeCategory();
		this.mediumCategory = requestDto.getMediumCategory();
		this.smallCategory = requestDto.getSmallCategory();
		this.subCategory = requestDto.getSubCategory();
	}

	public StudentGroup() {
	}
}

