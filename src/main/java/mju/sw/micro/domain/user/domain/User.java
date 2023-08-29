package mju.sw.micro.domain.user.domain;

import java.util.ArrayList;
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
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mju.sw.micro.domain.BaseEntity;
import mju.sw.micro.domain.application.domain.Application;
import mju.sw.micro.domain.user.dto.request.UserModifyRequestDto;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false, unique = true)
	private String email;
	@Column(nullable = false, unique = true)
	private String phoneNumber;
	@Column(nullable = false, unique = true)
	private String studentId;
	@Column(nullable = false)
	private String password;
	@Column(nullable = false)
	private String major;
	@JsonIgnore
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
	private List<UserRole> userRoles = new ArrayList<>();
	@JsonIgnore
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.REMOVE)
	private List<Application> applications = new ArrayList<>();
	private String introduction;
	private String profileImageUrl;
	private boolean notification;
	@ElementCollection
	private List<String> bookmark;

	@Builder
	public User(String name, String email, String phoneNumber, String introduction, String studentId,
		String major, String password, boolean notification, String profileImageUrl) {
		this.email = email;
		this.password = password;
		this.name = name;
		this.studentId = studentId;
		this.major = major;
		this.phoneNumber = phoneNumber;
		this.introduction = introduction;
		this.notification = notification;
		this.profileImageUrl = profileImageUrl;
	}

	public static User createUser(String name, String email, String phoneNumber, String introduction,
		String studentId, String major, String password, boolean notification) {
		return User.builder()
			.name(name)
			.email(email)
			.phoneNumber(phoneNumber)
			.introduction(introduction)
			.studentId(studentId)
			.major(major)
			.password(password)
			.notification(notification)
			.profileImageUrl(null)
			.build();
	}

	public void addRole(Role role) {
		if (validateRole(role))
			return;

		UserRole userRole = new UserRole();
		userRole.associate(this, role);
		userRoles.add(userRole);
	}

	private boolean validateRole(Role role) {
		for (UserRole userRole : userRoles) {
			if (userRole.getRole().equals(role)) {
				return true;
			}
		}
		return false;
	}

	public void addApplication(Application application) {
		applications.add(application);
		application.associateUser(this);
	}

	public void deleteRole(Role role) {
		userRoles.removeIf(userRole -> userRole.getRole().equals(role));
	}

	public void updatePassword(String password) {
		this.password = password;
	}

	public void updateUserProfile(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}

	public void updateUser(UserModifyRequestDto dto) {
		this.name = dto.getName();
		this.major = dto.getMajor();
	}

	public void updateIntroduction(String updatedIntroduction) {
		this.introduction = updatedIntroduction;
	}

	public boolean isAdmin() {
		return userRoles.stream().anyMatch(userRole -> userRole.getRole().equals(Role.ROLE_ADMIN));
	}

	public boolean isBanned() {
		return userRoles.stream().anyMatch(userRole -> userRole.getRole().equals(Role.ROLE_BANNED));
	}

	public void setBookmark(List<String> collect) {
		this.bookmark = new ArrayList<>(collect);
	}
}
