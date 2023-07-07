package mju.sw.micro.domain.user.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mju.sw.micro.domain.BaseEntity;

@Entity
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
	@Column(nullable = false)
	private String phoneNumber;
	@Column(nullable = false, unique = true)
	private String nickName;
	@Column(nullable = false, unique = true)
	private String studentId;
	@Column(nullable = false)
	private String password;
	@Column(nullable = false)
	private String interest;
	@Column(nullable = false)
	private String major;
	@OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
	private List<UserRole> userRoles = new ArrayList<>();
	private String introduction;
	private Boolean activated = false;
	private String imageUrl;
	private Boolean notification;

	@Builder
	public User (String name, String email, String phoneNumber, String interest,
		String introduction, String nickName, String studentId, String major,
		String password, Boolean notification) {
		this.email = email;
		this.password = password;
		this.name = name;
		this.nickName = nickName;
		this.studentId = studentId;
		this.major = major;
		this.interest = interest;
		this.phoneNumber = phoneNumber;
		this.introduction = introduction;
		this.notification = notification;
//		this.imageUrl = null;
	}

	public static User createUser(String name, String email, String phoneNumber,
		String interest, String introduction, String nickName, String studentId,
		String major, String password, Boolean notification) {
		return User.builder()
			.name(name)
			.email(email)
			.phoneNumber(phoneNumber)
			.interest(interest)
			.introduction(introduction)
			.nickName(nickName)
			.studentId(studentId)
			.major(major)
			.password(password)
			.notification(notification)
//			.imageUrl(null)
			.build();
	}

	public void addRole(Role role) {
		UserRole userRole = new UserRole();
		userRole.associate(this,role);
		userRoles.add(userRole);
	}
}
