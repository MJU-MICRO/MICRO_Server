package mju.sw.micro.global.config;

import java.lang.reflect.Field;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import mju.sw.micro.domain.user.dao.UserRepository;
import mju.sw.micro.domain.user.domain.Role;
import mju.sw.micro.domain.user.domain.User;

@Component
@RequiredArgsConstructor
public class DBInitConfig {
	private final DBInitService initService;

	@PostConstruct
	public void init() {
		initService.init();
	}

	@Component
	@Transactional
	@RequiredArgsConstructor
	public static class DBInitService {
		private final UserRepository userRepository;
		private final PasswordEncoder encoder;

		public void init() {

			final String userPw = "testuser";
			final String adminPw = "testadmin";

			try {
				User user = User.createUser("testUser", "testuser@mju.ac.kr", "010-0000-0000", "반갑습니다. 테스트 유저입니다",
					"testuser", "testUserId", "소프트웨어", encoder.encode(userPw), false);
				user.addRole(Role.ROLE_USER);
				Field id = user.getClass().getDeclaredField("id");

				id.setAccessible(true);
				id.set(user, 341512512L);

				userRepository.save(user);

				User user2 = User.createUser("testUser2", "testuser2@mju.ac.kr", "011-0000-0000", "반갑습니다. 테스트 유저입니다",
					"testuser2", "testUserId2", "소프트웨어", encoder.encode(userPw), false);
				user2.addRole(Role.ROLE_USER);
				Field id2 = user.getClass().getDeclaredField("id");

				id2.setAccessible(true);
				id2.set(user, 5152161L);

				userRepository.save(user2);

				// Init Admin
				User adminUser = User.createUser("testAdmin", "testadmin@mju.ac.kr", "010-1234-6789",
					"반갑습니다. 테스트 관리자입니다",
					"testadmin", "testAdminId", "데이터테크놀리지", encoder.encode(adminPw), false);
				adminUser.addRole(Role.ROLE_USER);
				adminUser.addRole(Role.ROLE_ADMIN);
				Field adminId = adminUser.getClass().getDeclaredField("id");

				adminId.setAccessible(true);
				adminId.set(user, 15135181L);

				userRepository.save(adminUser);

				User adminUser2 = User.createUser("testAdmin2", "testadmin2@mju.ac.kr", "011-1234-6789",
					"반갑습니다. 테스트 관리자입니다!",
					"testadmin2", "testAdminId2", "데이터테크놀리지", encoder.encode(adminPw), false);
				adminUser2.addRole(Role.ROLE_USER);
				adminUser2.addRole(Role.ROLE_ADMIN);
				Field adminId2 = adminUser2.getClass().getDeclaredField("id");

				adminId2.setAccessible(true);
				adminId2.set(user, 5123123L);

				userRepository.save(adminUser2);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
