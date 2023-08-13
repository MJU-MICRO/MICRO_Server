package mju.sw.micro.domain.admin.application;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import mju.sw.micro.IntegrationTestSupporter;
import mju.sw.micro.domain.admin.dto.response.AdminInfoResponseDto;
import mju.sw.micro.domain.user.domain.Role;
import mju.sw.micro.domain.user.domain.User;
import mju.sw.micro.global.common.response.ApiResponse;
import mju.sw.micro.global.utils.MockConstants;
import mju.sw.micro.global.utils.MockFactory;

class AdminServiceTest extends IntegrationTestSupporter {
	@BeforeEach
	void tearDownBefore() {
		userRepository.deleteAll();
	}

	@DisplayName("관리자 권한을 등록한다")
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
		Assertions.assertThat(userResponse.getMessage()).isEqualTo("관리자 권한 등록에 성공했습니다.");
	}

	@DisplayName("존재하지 않는 이메일을 입력하여 관리자 권한 등록을 실패한다")
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

	@DisplayName("관리자 권한 등록을 하기 전에 이미 관리자인 경우 등록을 실패한다")
	@Test
	void registerAdminWithAlreadyIsAdmin() {
		// given
		User user = MockFactory.createMockUser();
		user.addRole(Role.ROLE_ADMIN);
		userRepository.save(user);
		// when
		ApiResponse<Void> userResponse = adminService.registerAdmin(user.getEmail());
		// then
		Assertions.assertThat(userResponse.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
	}

	@DisplayName("관리자 권한을 해지한다")
	@Test
	void revokeAdmin() {
		// given
		User adminUser = MockFactory.createMockAdminUser();
		adminUser.addRole(Role.ROLE_ADMIN);
		userRepository.save(adminUser);

		User user = MockFactory.createMockUser();
		user.addRole(Role.ROLE_ADMIN);
		userRepository.save(user);
		// when
		ApiResponse<Void> userResponse = adminService.revokeAdmin(adminUser.getEmail(), user.getEmail());
		User updatedUser = userRepository.findByEmail(MockConstants.MOCK_USER_EMAIL).get();
		// then
		Assertions.assertThat(updatedUser.isAdmin()).isFalse();
		Assertions.assertThat(userResponse.getMessage()).isEqualTo("관리자 권한 해지에 성공했습니다.");
	}

	@DisplayName("관리자 권한 해지 전 이미 관리자가 아닌 경우 해지를 실패한다")
	@Test
	void revokeAdminWithAlreadyIsNotAdmin() {
		// given
		User adminUser = MockFactory.createMockAdminUser();
		adminUser.addRole(Role.ROLE_ADMIN);
		userRepository.save(adminUser);

		User user = MockFactory.createMockUser();
		user.addRole(Role.ROLE_USER);
		userRepository.save(user);
		// when
		ApiResponse<Void> userResponse = adminService.revokeAdmin(adminUser.getEmail(), user.getEmail());
		// then
		Assertions.assertThat(userResponse.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
	}

	@DisplayName("존재하지 않는 이메일을 입력하여 관리자 권한 해지를 실패한다")
	@Test
	void revokeAdminWithInvalidEmail() {
		// given
		User adminUser = MockFactory.createMockAdminUser();
		adminUser.addRole(Role.ROLE_ADMIN);
		userRepository.save(adminUser);

		User user = MockFactory.createMockUser();
		user.addRole(Role.ROLE_ADMIN);
		userRepository.save(user);
		// when
		ApiResponse<Void> userResponse = adminService.revokeAdmin(adminUser.getEmail(), "invalidEmail");
		// then
		Assertions.assertThat(userResponse.getStatus()).isEqualTo(HttpStatus.NOT_FOUND);
	}

	@DisplayName("관리자 자신은 자신의 계정 권한을 해지할 수 없다.")
	@Test
	void revokeAdminWithAdminCannotRevokeMyself() {
		// given
		User adminUser = MockFactory.createMockAdminUser();
		adminUser.addRole(Role.ROLE_ADMIN);
		userRepository.save(adminUser);
		// when
		ApiResponse<Void> userResponse = adminService.revokeAdmin(adminUser.getEmail(),
			MockConstants.MOCK_ADMIN_USER_EMAIL);
		// then
		Assertions.assertThat(userResponse.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
		Assertions.assertThat(userResponse.getMessage()).isEqualTo("관리자 자신의 권한은 해지할 수 없습니다.");
	}

	@DisplayName("관리자가 계정을 삭제한다")
	@Test
	void deleteUserByAdmin() {
		// given
		User adminUser = MockFactory.createMockAdminUser();
		adminUser.addRole(Role.ROLE_ADMIN);
		userRepository.save(adminUser);

		User user = MockFactory.createMockUser();
		user.addRole(Role.ROLE_USER);
		userRepository.save(user);
		//when
		ApiResponse<Void> userResponse = adminService.deleteUserByAdmin(adminUser.getEmail(), user.getEmail());
		Optional<User> optionalUser = userRepository.findByEmail(user.getEmail());
		// then
		Assertions.assertThat(userResponse.getMessage()).isEqualTo("관리자가 회원을 삭제했습니다.");
		Assertions.assertThat(optionalUser).isEmpty();
	}

	@DisplayName("존재하지 않는 이메일을 입력하여 관리자가 계정 삭제를 실패한다")
	@Test
	void deleteUserByAdminWithInvalidEmail() {
		// given
		User adminUser = MockFactory.createMockAdminUser();
		adminUser.addRole(Role.ROLE_ADMIN);
		userRepository.save(adminUser);

		User user = MockFactory.createMockUser();
		user.addRole(Role.ROLE_USER);
		userRepository.save(user);
		//when
		ApiResponse<Void> userResponse = adminService.deleteUserByAdmin(adminUser.getEmail(), "invalidEmail");
		// then
		Assertions.assertThat(userResponse.getStatus()).isEqualTo(HttpStatus.NOT_FOUND);
	}

	@DisplayName("관리자 자신은 자신의 계정을 삭제할 수 없다.")
	@Test
	void deleteUserByAdminWithAdminCannotDeleteMyself() {
		// given
		User adminUser = MockFactory.createMockAdminUser();
		adminUser.addRole(Role.ROLE_ADMIN);
		userRepository.save(adminUser);
		//when
		ApiResponse<Void> userResponse = adminService.deleteUserByAdmin(adminUser.getEmail(),
			MockConstants.MOCK_ADMIN_USER_EMAIL);
		// then
		Assertions.assertThat(userResponse.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
		Assertions.assertThat(userResponse.getMessage()).isEqualTo("관리자 자신의 계정은 삭제할 수 없습니다.");
	}

	@DisplayName("관리자 권한을 가진 모든 계정을 조회한다")
	@Test
	void getUsersByAdminRole() {
		// given
		User user = MockFactory.createMockUser();
		user.addRole(Role.ROLE_ADMIN);
		userRepository.save(user);
		// when
		ApiResponse<List<AdminInfoResponseDto>> response = adminService.getUsersByAdminRole();
		List<AdminInfoResponseDto> adminList = response.getData();
		// then
		Assertions.assertThat(response.getMessage()).isEqualTo("관리자 권한을 가진 모든 계정을 조회했습니다.");
		Assertions.assertThat(adminList).hasSize(1);
		Assertions.assertThat(adminList.get(0).getEmail()).isEqualTo(MockConstants.MOCK_USER_EMAIL);
		Assertions.assertThat(adminList.get(0).getName()).isEqualTo(MockConstants.MOCK_USER_NAME);
		Assertions.assertThat(adminList.get(0).getPhoneNumber()).isEqualTo(MockConstants.MOCK_PHONE_NUMBER);
	}

	@DisplayName("관리자 권한을 가진 모든 계정을 조회했지만 관리자가 없다면 빈 리스트를 반환한다")
	@Test
	void getUsersByAdminRoleWithNoAdmin() {
		// given
		// when
		ApiResponse<List<AdminInfoResponseDto>> response = adminService.getUsersByAdminRole();
		// then
		Assertions.assertThat(response.getMessage()).isEqualTo("관리자 권한을 가진 계정이 없습니다.");
		Assertions.assertThat(response.getData()).isEqualTo(Collections.emptyList());
	}

	@DisplayName("관리자 권한을 갖지 않은 모든 계정을 조회한다")
	@Test
	void getUsersWithoutAdminRole() {
		// given
		User user = MockFactory.createMockUser();
		user.addRole(Role.ROLE_USER);
		userRepository.save(user);
		// when
		ApiResponse<List<User>> response = adminService.getUsersWithoutAdminRole();
		List<User> userList = response.getData();
		// then
		Assertions.assertThat(response.getMessage()).isEqualTo("관리자 권한을 갖지 않은 모든 계정을 조회했습니다.");
		Assertions.assertThat(userList).hasSize(1);
		Assertions.assertThat(userList.get(0).getEmail()).isEqualTo(MockConstants.MOCK_USER_EMAIL);
		Assertions.assertThat(userList.get(0).getMajor()).isEqualTo(MockConstants.MOCK_MAJOR);
	}
}
