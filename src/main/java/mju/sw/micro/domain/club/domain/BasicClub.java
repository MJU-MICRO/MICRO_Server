package mju.sw.micro.domain.club.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import mju.sw.micro.domain.club.dto.request.ClubRequestDto;

@Entity
public class BasicClub extends Club {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String clubName;
	private String imageUrl;
	private Object classification;
	private String establishedYear;
	private String[] relatedTag;
	private String relationMajor;
	private String introduction;
	private String numberOfMember;
	private Campus campus;
	private String[] activityTitle;
	private String[] activityContent;

	// 기본 생성자
	public BasicClub() {
	}

	public BasicClub(ClubRequestDto requestDto) {
		this.clubName = (String) requestDto.getClubName();
		this.imageUrl = requestDto.getImageUrl();
		this.classification = requestDto.getClassification();
		this.establishedYear = requestDto.getEstablishedYear();
		this.relatedTag = (String[]) requestDto.getRelatedTag();
		this.relationMajor = requestDto.getRelationMajor();
		this.introduction = (String) requestDto.getIntroduction();
		this.numberOfMember = requestDto.getNumberOfMember();
		this.campus = (Campus) requestDto.getCampus();
		this.activityTitle = requestDto.getActivityTitle();
		this.activityContent = requestDto.getActivityContent();
	}

}
