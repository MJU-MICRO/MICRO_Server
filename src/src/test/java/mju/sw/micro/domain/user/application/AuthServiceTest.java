package mju.sw.micro.domain.user.application;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.TestPropertySource;

import mju.sw.micro.IntegrationTestSupporter;
import mju.sw.micro.domain.user.domain.EmailCode;
import mju.sw.micro.domain.user.domain.User;
import mju.sw.micro.domain.user.dto.request.SignUpRequestDto;
import mju.sw.micro.global.common.response.ApiResponse;
import mju.sw.micro.global.config.TestContainerConfig;
import mju.sw.micro.global.utils.CodeUtil;
import mju.sw.micro.global.utils.MockConstants;
import mju.sw.micro.global.utils.MockFactory;
import mju.sw.micro.global.utils.TimeUtil;

@ExtendWith(TestContainerConfig.class)
@TestPropertySource(locations = "classpath:application-test.yml")
class AuthServiceTest extends IntegrationTestSupporter {

	String verificationCode;
	SignUpRequestDto signUpRequestDto;

	@BeforeEach
	void setUp() {
		verificationCode = CodeUtil.generateRandomCode();
		signUpRequestDto = SignUpRequestDto.of(MockConstants.MOCK_USER_EMAIL, MockConstants.MOCK_USER_PASSWORD,
			MockConstants.MOCK_USER_NAME, MockConstants.MOCK_USER_NICKNAME, MockConstants.MOCK_STUDENT_ID,
			MockConstants.MOCK_MAJOR, MockConstants.MOCK_PHONE_NUMBER,
			MockConstants.MOCK_INTRODUCTION, false, verificationCode);
	}

	@AfterEach
	void tearDown() {
		userRepository.deleteAll();
	}

	// @DisplayName("회원 가입을 성공한다.")
	// @Test
	// void signUp() {
	// 	//given
	// 	EmailCode emailCode = EmailCode.of(MockConstants.MOCK_USER_EMAIL, verificationCode,
	// 		MockConstants.MOCK_TOKEN_EXPIRATION_TIME,
	// 		TimeUtil.generateExpiration(MockConstants.MOCK_TOKEN_EXPIRATION_TIME));
	// 	emailCodeRedisRepository.save(emailCode);
	// 	// when
	// 	ApiResponse<String> response = authService.signUp(signUpRequestDto, null);
	// 	Optional<User> optionalUser = userRepository.findByEmail(MockConstants.MOCK_USER_EMAIL);
	// 	Assertions.assertTrue(optionalUser.isPresent(), "회원가입에 실패했습니다.");
	// 	User user = optionalUser.get();
	// 	// then
	// 	Assertions.assertEquals(MockConstants.MOCK_USER_EMAIL, user.getEmail());
	// 	Assertions.assertEquals(MockConstants.MOCK_USER_NAME, user.getName());
	// 	Assertions.assertEquals(MockConstants.MOCK_PHONE_NUMBER, user.getPhoneNumber());
	// 	Assertions.assertEquals("회원가입에 성공했습니다.", response.getMessage());
	//
	// }

	@DisplayName("이미 가입한 회원이어서 회원가입을 실패한다.")
	@Test
	void signUpWithAlreadySignedUpUser() {
		//given
		User user = MockFactory.createMockUser();
		userRepository.save(user);
		// when
		ApiResponse<Void> response = authService.signUp(signUpRequestDto, null);
		// then
		Assertions.assertEquals("이미 가입된 이메일 입니다.", response.getMessage());
	}

	@DisplayName("이메일 인증 코드가 옳지 않아 회원가입을 실패한다.")
	@Test
	void signUpWithWrongVerificationCode() {
		//given
		EmailCode emailCode = EmailCode.of(MockConstants.MOCK_USER_EMAIL, verificationCode,
			MockConstants.MOCK_TOKEN_EXPIRATION_TIME,
			TimeUtil.generateExpiration(MockConstants.MOCK_TOKEN_EXPIRATION_TIME));
		emailCodeRedisRepository.save(emailCode);
		SignUpRequestDto dto = SignUpRequestDto.of(MockConstants.MOCK_USER_EMAIL, MockConstants.MOCK_USER_PASSWORD,
			MockConstants.MOCK_USER_NAME, MockConstants.MOCK_USER_NICKNAME, MockConstants.MOCK_STUDENT_ID,
			MockConstants.MOCK_MAJOR, MockConstants.MOCK_PHONE_NUMBER,
			MockConstants.MOCK_INTRODUCTION, false, "wrongCode");
		// when
		ApiResponse<Void> response = authService.signUp(dto, null);
		// then
		Assertions.assertEquals("인증 토큰이 유효하지 않습니다.", response.getMessage());
	}

}
