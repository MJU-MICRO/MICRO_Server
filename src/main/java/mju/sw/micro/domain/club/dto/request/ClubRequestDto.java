package mju.sw.micro.domain.club.dto.request;

import mju.sw.micro.domain.club.domain.Campus;
import mju.sw.micro.domain.club.domain.ClubClassification;

public class ClubRequestDto {
	private String clubName;
	private String imageUrl;
	private ClubClassification classification;
	private String establishedYear;
	private String[] relatedTag;
	private String relationMajor;
	private String introduction;
	private String numberOfMember;
	private Campus campus;
	private String[] activityTitle;
	private String[] activityContent;

	public String getClubName() {
		return clubName;
	}

	public void setClubName(String clubName) {
		this.clubName = clubName;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public ClubClassification getClassification() {
		return classification;
	}

	public void setClassification(ClubClassification classification) {
		this.classification = classification;
	}

	public String getEstablishedYear() {
		return establishedYear;
	}

	public void setEstablishedYear(String establishedYear) {
		this.establishedYear = establishedYear;
	}

	public String[] getRelatedTag() {
		return relatedTag;
	}

	public void setRelatedTag(String[] relatedTag) {
		this.relatedTag = relatedTag;
	}

	public String getRelationMajor() {
		return relationMajor;
	}

	public void setRelationMajor(String relationMajor) {
		this.relationMajor = relationMajor;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getNumberOfMember() {
		return numberOfMember;
	}

	public void setNumberOfMember(String numberOfMember) {
		this.numberOfMember = numberOfMember;
	}

	public Campus getCampus() {
		return campus;
	}

	public void setCampus(Campus campus) {
		this.campus = campus;
	}

	public String[] getActivityTitle() {
		return activityTitle;
	}

	public void setActivityTitle(String[] activityTitle) {
		this.activityTitle = activityTitle;
	}

	public String[] getActivityContent() {
		return activityContent;
	}

	public void setActivityContent(String[] activityContent) {
		this.activityContent = activityContent;
	}

}
