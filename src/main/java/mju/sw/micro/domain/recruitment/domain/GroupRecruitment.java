package mju.sw.micro.domain.recruitment.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mju.sw.micro.domain.BaseEntity;
import mju.sw.micro.domain.application.domain.Application;
import mju.sw.micro.domain.group.domain.StudentGroup;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GroupRecruitment extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private LocalDateTime startDateTime;
	private LocalDateTime endDateTime;
	private String title;
	private String description;
	private String content;

	@Enumerated(EnumType.STRING)
	private ActivityPeriod activityPeriod;

	@OneToMany(mappedBy = "recruitment", cascade = CascadeType.REMOVE,
		orphanRemoval = true, fetch = FetchType.EAGER)
	private List<RecruitmentImage> imageList = new ArrayList<>();

	@JsonIgnore
	@OneToMany(mappedBy = "recruitment", fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.REMOVE)
	private List<Application> applications = new ArrayList<>();

	@ManyToOne(fetch = FetchType.EAGER)
	private StudentGroup group;

	private boolean isSubmit;

	private String activePlace;
	@ElementCollection
	private List<String> applicationFields = new ArrayList<>();
	@ElementCollection
	private List<String> questions = new ArrayList<>();

	@ElementCollection
	private List<Integer> characterLimit = new ArrayList<>();

	@Builder
	public GroupRecruitment(LocalDateTime startDateTime, LocalDateTime endDateTime, String title, String description,
		String content, ActivityPeriod activityPeriod, List<RecruitmentImage> imageList,
		String activePlace, boolean isSubmit, List<String> applicationFields, List<String> questions,
		List<Integer> characterLimit) {
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.title = title;
		this.description = description;
		this.content = content;
		this.activityPeriod = activityPeriod;
		this.imageList = imageList;
		this.activePlace = activePlace;
		this.applicationFields = applicationFields;
		this.questions = questions;
		this.characterLimit = characterLimit;
		this.isSubmit = isSubmit;
	}

	public void clearGroup() {
		this.group = null;
	}

	public void setGroup(StudentGroup club) {
		this.group = club;
	}

	public void clearImageList() {
		this.imageList.forEach(RecruitmentImage::clearRecruitment);
		this.imageList.clear();
	}

	public void addImage(RecruitmentImage image) {
		this.imageList.add(image);
		image.setRecruitment(this);
	}

	public void addApplication(Application application) {
		applications.add(application);
		application.associateGroupRecruitment(this);
	}
}
