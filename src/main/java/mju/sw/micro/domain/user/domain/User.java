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
	private int phoneNumber;
	@Column(nullable = false, unique = true)
	private String nickName;
	@Column(nullable = false, unique = true)
	private String studentId;
	@Column(nullable = false)
	private String password;
	@Column(nullable = false)
	private int interest;
	@Column(nullable = false)
	private String major;
	@OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
	private List<UserRole> userRoles = new ArrayList<>();
	private String introduction;
	private Boolean activated;
	private String imageUrl;
	private Boolean notification;

	@Builder
	public User (Long id, String name, String email, int phoneNumber, int interest,
		String introduction, String nickName, String studentId, String major,
		Boolean activated, String password, String imageUrl, Boolean notification) {
		User user = new User();
		user.id = id;
		user.name = name;
		user.email = email;
		user.phoneNumber = phoneNumber;
		user.interest = interest;
		user.introduction = introduction;
		user.nickName = nickName;
		user.studentId = studentId;
		user.major = major;
		user.activated = activated;
		user.password = password;
		user.imageUrl = imageUrl;
		user.notification = notification;
	}


	public void addRole(Role role) {
		UserRole userRole = new UserRole();
		userRole.associate(this,role);
		userRoles.add(userRole);
	}
}
