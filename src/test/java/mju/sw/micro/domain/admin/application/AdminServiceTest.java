package mju.sw.micro.domain.admin.application;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import mju.sw.micro.IntegrationTestSupporter;
import mju.sw.micro.domain.user.domain.Role;
import mju.sw.micro.domain.user.domain.User;
import mju.sw.micro.global.common.response.ApiResponse;
import mju.sw.micro.global.utils.MockConstants;
import mju.sw.micro.global.utils.MockFactory;

class AdminServiceTest extends IntegrationTestSupporter {

	@AfterEach
	void tearDown() {
		userRepository.deleteAll();
	}

	@DisplayName("관리자를 등록한다")
	@Test
	void registerAdmin() {
		// given
		User user = MockFactory.createMockUser();
		user.addRole(Role.ROLE_USER);
		userRepository.save(user);
		// when
		ApiResponse<Void> userResponse = adminService.registerAdmin(user.getEmail());
		User updatedUser = userRepository.findByEmail(MockConstants.MOCK_USER_EMAIL).get();
		// then
		Assertions.assertThat(updatedUser.isAdmin()).isTrue();
		Assertions.assertThat(userResponse.getMessage()).isEqualTo("관리자 등록에 성공했습니다.");
	}

	@DisplayName("존재하지 않는 이메일을 입력하여 관리자 등록을 실패한다")
	@Test
	void registerAdminWithInvalidEmail() {
		// given
		User user = MockFactory.createMockUser();
		user.addRole(Role.ROLE_USER);
		userRepository.save(user);
		// when
		ApiResponse<Void> userResponse = adminService.registerAdmin("invalidEmail");
		// then
		Assertions.assertThat(userResponse.getStatus()).isEqualTo(HttpStatus.NOT_FOUND);
	}

	@DisplayName("관리자를 해지한다")
	@Test
	void revokeAdmin() {
		// given
		User user = MockFactory.createMockUser();
		user.addRole(Role.ROLE_ADMIN);
		userRepository.save(user);
		// when
		ApiResponse<Void> userResponse = adminService.revokeAdmin(user.getEmail());
		User updatedUser = userRepository.findByEmail(MockConstants.MOCK_USER_EMAIL).get();
		// then
		Assertions.assertThat(updatedUser.isAdmin()).isFalse();
		Assertions.assertThat(userResponse.getMessage()).isEqualTo("관리자 해지에 성공했습니다.");
	}

	@DisplayName("존재하지 않는 이메일을 입력하여 관리자 해지를 실패한다")
	@Test
	void revokeAdminWithInvalidEmail() {
		// given
		User user = MockFactory.createMockUser();
		user.addRole(Role.ROLE_ADMIN);
		userRepository.save(user);
		// when
		ApiResponse<Void> userResponse = adminService.revokeAdmin("invalidEmail");
		// then
		Assertions.assertThat(userResponse.getStatus()).isEqualTo(HttpStatus.NOT_FOUND);
	}

}
