package mju.sw.micro.domain.user.application;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import mju.sw.micro.IntegrationTestSupporter;
import mju.sw.micro.domain.user.domain.User;
import mju.sw.micro.domain.user.dto.response.UserInfoResponseDto;
import mju.sw.micro.global.common.response.ApiResponse;
import mju.sw.micro.global.utils.MockFactory;

class UserServiceTest extends IntegrationTestSupporter {

	@AfterEach
	void tearDown() {
		userRepository.deleteAllInBatch();
	}

	@DisplayName("회원 정보를 가져온다.")
	@Test
	void getUserInfoTest() {
		// given
		User user = MockFactory.createMockUser();
		userRepository.save(user);
		//when
		ApiResponse<UserInfoResponseDto> userResponse = userService.getUserInfo(user.getEmail());
		// then
		UserInfoResponseDto userInfoResponseDto = userResponse.getData();

		Assertions.assertNull(user.getProfileImageUrl(), userInfoResponseDto.getProfileImageUrl());
		Assertions.assertEquals(user.getName(), userInfoResponseDto.getName());
		Assertions.assertEquals(user.getNickName(), userInfoResponseDto.getNickName());
		Assertions.assertEquals(user.getMajor(), userInfoResponseDto.getMajor());
		Assertions.assertEquals(user.getIntroduction(), userInfoResponseDto.getIntroduction());

	}
}
