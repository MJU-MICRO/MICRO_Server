package mju.sw.micro.domain.recruitment.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
	private String fields;

	@Enumerated(EnumType.STRING)
	private ActivityPeriod activityPeriod;

	@OneToMany(mappedBy = "recruitment", cascade = CascadeType.REMOVE,
		orphanRemoval = true, fetch = FetchType.EAGER)
	private List<RecruitmentImage> imageList = new ArrayList<>();

	@ManyToOne(fetch = FetchType.EAGER)
	private StudentGroup group;

	private boolean isSubmit;

	private String activePlace;

	@ElementCollection
	private List<String> questions = new ArrayList<>();

	@ElementCollection
	private List<Integer> characterLimit = new ArrayList<>();

	@Builder
	public GroupRecruitment(LocalDateTime startDateTime, LocalDateTime endDateTime, String title, String description,
		String content, String fields, ActivityPeriod activityPeriod, List<RecruitmentImage> imageList,
		String activePlace, boolean isSubmit, List<String> questions, List<Integer> characterLimit) {
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.title = title;
		this.description = description;
		this.content = content;
		this.fields = fields;
		this.activityPeriod = activityPeriod;
		this.imageList = imageList;
		this.activePlace = activePlace;
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
}
