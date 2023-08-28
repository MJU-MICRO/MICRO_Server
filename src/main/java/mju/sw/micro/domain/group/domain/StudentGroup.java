package mju.sw.micro.domain.group.domain;

import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import mju.sw.micro.domain.group.dto.StudentGroupRequestDto;
import mju.sw.micro.domain.recruitment.domain.GroupRecruitment;
import mju.sw.micro.global.security.CustomUserDetails;

@Entity
@Table(name = "student_groups")
@Getter
@Setter
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
	@ElementCollection
	private List<String> relationMajor;
	@ElementCollection
	private List<String> relatedTag;
	@ElementCollection
	private List<String> activityTitle;
	@ElementCollection
	private List<String> activityContent;
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
	@JsonIgnore
	@OneToMany(mappedBy = "group", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.EAGER)
	private List<GroupRecruitment> recruitmentList = new LinkedList<>();

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
		this.isRecruit = false;
		this.isApprove = false;
		this.campus = requestDto.getCampus();
		this.largeCategory = requestDto.getLargeCategory();
		this.mediumCategory = requestDto.getMediumCategory();
		this.smallCategory = requestDto.getSmallCategory();
		this.subCategory = requestDto.getSubCategory();
	}

	public StudentGroup() {
	}

	public void clearRecruitments() {
		this.recruitmentList.forEach(GroupRecruitment::clearGroup);
		this.recruitmentList.clear();
	}

	public void addRecruitment(GroupRecruitment recruitment) {
		this.recruitmentList.add(recruitment);
		recruitment.setGroup(this);
	}

	public void updateIsRecruit(boolean isRecruit) {
		this.isRecruit = isRecruit;
	}
}

