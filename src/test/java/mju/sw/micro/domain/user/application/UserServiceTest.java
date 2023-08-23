package mju.sw.micro.domain.user.application;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import mju.sw.micro.IntegrationTestSupporter;
import mju.sw.micro.domain.user.domain.User;
import mju.sw.micro.domain.user.dto.request.UserModifyRequestDto;
import mju.sw.micro.domain.user.dto.request.UserPasswordRequestDto;
import mju.sw.micro.domain.user.dto.response.UserInfoResponseDto;
import mju.sw.micro.global.common.response.ApiResponse;
import mju.sw.micro.global.utils.MockConstants;
import mju.sw.micro.global.utils.MockFactory;

class UserServiceTest extends IntegrationTestSupporter {
	private final String updatePassword = "testPassword";
	private final String updateName = "testName";
	private final String updateMajor = "testMajor";
	private final String updateIntroduction = "testIntroduction";

	@AfterEach
	void tearDown() {
		userRepository.deleteAllInBatch();
	}

	@DisplayName("회원 정보를 가져온다.")
	@Test
	void getUserInfo() {
		// given
		User user = MockFactory.createMockUser();
		userRepository.save(user);
		//when
		ApiResponse<UserInfoResponseDto> userResponse = userService.getUserInfo(user.getEmail());
		// then
		UserInfoResponseDto userInfoResponseDto = userResponse.getData();

		Assertions.assertNull(user.getProfileImageUrl(), userInfoResponseDto.getProfileImageUrl());
		Assertions.assertEquals(user.getName(), userInfoResponseDto.getName());
		Assertions.assertEquals(user.getMajor(), userInfoResponseDto.getMajor());
		Assertions.assertEquals(user.getIntroduction(), userInfoResponseDto.getIntroduction());

	}

	@DisplayName("존재하지 않는 이메일을 입력하여 회원 정보를 가져오지 못한다.")
	@Test
	void getUserInfoWithInvalidEmail() {
		// given
		User user = MockFactory.createMockUser();
		userRepository.save(user);
		//when
		ApiResponse<UserInfoResponseDto> userResponse = userService.getUserInfo("invalidEmail");
		// then
		HttpStatus status = userResponse.getStatus();
		Assertions.assertEquals(HttpStatus.NOT_FOUND, status);
	}

	@DisplayName("회원 정보를 수정한다")
	@Test
	void modifyUserInfo() {
		// given
		User user = User.createUser(MockConstants.MOCK_USER_NAME, MockConstants.MOCK_USER_EMAIL,
			MockConstants.MOCK_PHONE_NUMBER, MockConstants.MOCK_INTRODUCTION,
			MockConstants.MOCK_STUDENT_ID, MockConstants.MOCK_MAJOR, encoder.encode(MockConstants.MOCK_USER_PASSWORD),
			false);
		userRepository.save(user);
		//when
		UserModifyRequestDto dto = new UserModifyRequestDto(updateName, updateMajor, updateIntroduction);
		ApiResponse<Void> userResponse = userService.modifyUserInfo(dto, null, user.getEmail());
		User updatedUser = userRepository.findByEmail(MockConstants.MOCK_USER_EMAIL).get();
		// then
		Assertions.assertEquals(HttpStatus.OK, userResponse.getStatus());
		Assertions.assertEquals("회원 정보 수정 완료", userResponse.getMessage());
		Assertions.assertEquals(updateMajor, updatedUser.getMajor());
		Assertions.assertEquals(updateIntroduction, updatedUser.getIntroduction());
		Assertions.assertEquals(updateName, updatedUser.getName());
	}

	@DisplayName("존재하지 않는 이메일을 입력하여 회원 정보를 수정하지 못한다.")
	@Test
	void modifyUserInfoWithInvalidEmail() {
		// given
		User user = User.createUser(MockConstants.MOCK_USER_NAME, MockConstants.MOCK_USER_EMAIL,
			MockConstants.MOCK_PHONE_NUMBER, MockConstants.MOCK_INTRODUCTION,
			MockConstants.MOCK_STUDENT_ID, MockConstants.MOCK_MAJOR, encoder.encode(MockConstants.MOCK_USER_PASSWORD),
			false);
		userRepository.save(user);
		//when
		UserModifyRequestDto dto = new UserModifyRequestDto(updateName, updateMajor, updateIntroduction);
		ApiResponse<Void> userResponse = userService.modifyUserInfo(dto, null, "InvalidEmail");
		// then
		Assertions.assertEquals(HttpStatus.NOT_FOUND, userResponse.getStatus());
		Assertions.assertEquals("요청한 리소스를 찾을 수 없습니다.", userResponse.getMessage());
	}

	@DisplayName("회원 비밀번호를 수정한다")
	@Test
	void modifyPassword() {
		// given
		String originPw = encoder.encode(MockConstants.MOCK_USER_PASSWORD);
		User user = User.createUser(MockConstants.MOCK_USER_NAME, MockConstants.MOCK_USER_EMAIL,
			MockConstants.MOCK_PHONE_NUMBER, MockConstants.MOCK_INTRODUCTION,
			MockConstants.MOCK_STUDENT_ID, MockConstants.MOCK_MAJOR, originPw, false);
		userRepository.save(user);
		//when
		UserPasswordRequestDto dto = new UserPasswordRequestDto(MockConstants.MOCK_USER_PASSWORD, updatePassword);
		ApiResponse<Void> userResponse = userService.modifyUserPassword(dto, user.getEmail());
		User updatedUser = userRepository.findByEmail(MockConstants.MOCK_USER_EMAIL).get();
		// then
		Assertions.assertEquals(HttpStatus.OK, userResponse.getStatus());
		Assertions.assertEquals("회원 비밀번호 변경 완료", userResponse.getMessage());
		Assertions.assertTrue(encoder.matches(updatePassword, updatedUser.getPassword()));
	}

	@DisplayName("틀린 비밀번호를 입력하여 회원 비밀번호를 수정하지 못한다.")
	@Test
	void modifyPasswordWithInvalidPassword() {
		// given
		User user = User.createUser(MockConstants.MOCK_USER_NAME, MockConstants.MOCK_USER_EMAIL,
			MockConstants.MOCK_PHONE_NUMBER, MockConstants.MOCK_INTRODUCTION,
			MockConstants.MOCK_STUDENT_ID, MockConstants.MOCK_MAJOR, encoder.encode(MockConstants.MOCK_USER_PASSWORD),
			false);
		userRepository.save(user);
		//when
		UserPasswordRequestDto dto = new UserPasswordRequestDto("invalidPw", updatePassword);
		ApiResponse<Void> userResponse = userService.modifyUserPassword(dto, user.getEmail());
		// then
		Assertions.assertEquals(HttpStatus.UNAUTHORIZED, userResponse.getStatus());
		Assertions.assertEquals("인증이 필요한 접근입니다.", userResponse.getMessage());
	}

	@DisplayName("회원 탈퇴를 한다.")
	@Test
	void deleteUser() {
		// given
		User user = MockFactory.createMockUser();
		userRepository.save(user);
		//when
		ApiResponse<Void> userResponse = userService.deleteUser(user.getEmail());
		Optional<User> optionalUser = userRepository.findByEmail(user.getEmail());
		// then
		Assertions.assertEquals(HttpStatus.OK, userResponse.getStatus());
		Assertions.assertEquals("회원 탈퇴 완료", userResponse.getMessage());
		Assertions.assertTrue(optionalUser.isEmpty());
	}

	@DisplayName("잘못된 이메일을 입력하여 회원 탈퇴를 실패한다.")
	@Test
	void deleteUserWithInvalidEmail() {
		// given
		User user = MockFactory.createMockUser();
		userRepository.save(user);
		//when
		ApiResponse<Void> userResponse = userService.deleteUser("invalidEmail");
		// then
		Assertions.assertEquals(HttpStatus.NOT_FOUND, userResponse.getStatus());
		Assertions.assertEquals("요청한 리소스를 찾을 수 없습니다.", userResponse.getMessage());
	}
}
