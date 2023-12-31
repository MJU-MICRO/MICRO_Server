// package mju.sw.micro.global.config;
//
// import java.lang.reflect.Field;
//
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.stereotype.Component;
// import org.springframework.transaction.annotation.Transactional;
//
// import jakarta.annotation.PostConstruct;
// import lombok.RequiredArgsConstructor;
// import mju.sw.micro.domain.user.dao.UserRepository;
// import mju.sw.micro.domain.user.domain.Role;
// import mju.sw.micro.domain.user.domain.User;
//
// @Component
// @RequiredArgsConstructor
// public class DBInitConfig {
// 	private final DBInitService initService;
//
// 	@PostConstruct
// 	public void init() {
// 		initService.init();
// 	}
//
// 	@Component
// 	@Transactional
// 	@RequiredArgsConstructor
// 	public static class DBInitService {
// 		private final UserRepository userRepository;
// 		private final PasswordEncoder encoder;
//
// 		public void init() {
//
// 			final String adminPw = "admintester";
//
// 			try {
// 				// Init Admin
// 				User adminUser = User.createUser("admintester", "admintester@mju.ac.kr", "adminphoneNumber",
// 					"반갑습니다. 테스트 관리자입니다", "admintesterId", "데이터테크놀리지", encoder.encode(adminPw), false);
// 				adminUser.addRole(Role.ROLE_ADMIN);
// 				Field adminId = adminUser.getClass().getDeclaredField("id");
//
// 				adminId.setAccessible(true);
// 				adminId.set(adminUser, 9L);
//
// 				userRepository.save(adminUser);
//
// 				User user = User.createUser("coldroad", "coldroad@mju.ac.kr", "adminphoneNumber", "반갑습니다. 테스트 관리자입니다",
// 					"60181894", "데이터테크놀리지", encoder.encode("asdasd123"), false);
// 				user.addRole(Role.ROLE_USER);
// 				Field userId = user.getClass().getDeclaredField("id");
//
// 				userId.setAccessible(true);
// 				userId.set(user, 8L);
//
// 				userRepository.save(user);
//
// 			} catch (Exception e) {
// 				e.printStackTrace();
// 			}
// 		}
// 	}
// }
