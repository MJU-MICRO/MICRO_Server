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
				User user = User.createUser("testUser", "testuser@gmail.com", "010-0000-0000", "반갑습니다. 테스트 유저입니다",
					"testuser", "testUserId", "소프트웨어", encoder.encode(userPw), false);
				user.addRole(Role.ROLE_USER);
				Field id = user.getClass().getDeclaredField("id");

				id.setAccessible(true);
				id.set(user, 341512512L);

				userRepository.save(user);

				// Init Admin
				User adminUser = User.createUser("testAdmin", "testadmin@gmail.com", "010-1234-6789",
					"반갑습니다. 테스트 관리자입니다",
					"testadmin", "testAdminId", "데이터테크놀리지", encoder.encode(adminPw), false);
				adminUser.addRole(Role.ROLE_USER);
				adminUser.addRole(Role.ROLE_ADMIN);
				Field adminId = adminUser.getClass().getDeclaredField("id");

				adminId.setAccessible(true);
				adminId.set(user, 15135181L);

				userRepository.save(adminUser);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
