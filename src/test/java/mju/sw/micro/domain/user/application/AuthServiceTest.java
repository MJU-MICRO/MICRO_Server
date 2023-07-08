package mju.sw.micro.domain.user.application;

import mju.sw.micro.IntegrationTestSupporter;
import mju.sw.micro.domain.user.domain.Token;
import mju.sw.micro.domain.user.domain.User;
import mju.sw.micro.domain.user.dto.request.SignUpRequestDto;
import mju.sw.micro.global.common.response.ApiResponse;
import mju.sw.micro.global.config.TestContainerConfig;
import mju.sw.micro.global.utils.CodeUtil;
import mju.sw.micro.global.utils.MockConstants;
import mju.sw.micro.global.utils.MockFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@ExtendWith(TestContainerConfig.class)
@TestPropertySource(locations = "classpath:application.yml")
@ActiveProfiles("test")
class AuthServiceTest extends IntegrationTestSupporter {

	String verificationCode = CodeUtil.generateRandomCode();
	SignUpRequestDto signUpRequestDto;

	@BeforeEach
	void setUp() {
		signUpRequestDto = SignUpRequestDto.of(MockConstants.MOCK_USER_EMAIL,
			MockConstants.MOCK_USER_PASSWORD,
			MockConstants.MOCK_USER_NAME, MockConstants.MOCK_USER_NICKNAME,
			MockConstants.MOCK_STUDENT_ID,
			MockConstants.MOCK_MAJOR, MockConstants.MOCK_INTEREST, MockConstants.MOCK_PHONE_NUMBER,
			MockConstants.MOCK_INTRODUCTION, false, verificationCode);
	}

	@AfterEach
	void deleteUser() {
		userRepository.deleteAll();
	}

	@DisplayName("회원 가입을 성공한다")
	@Test
	void signUp() {
		//given
		Token token = Token.of(MockConstants.MOCK_USER_EMAIL, verificationCode,
			MockConstants.MOCK_TOKEN_EXPIRATION_TIME);
		tokenRedisRepository.save(token);
		// when
		ApiResponse<String> response = authService.signUp(signUpRequestDto);
		User user = userRepository.findByEmail(MockConstants.MOCK_USER_EMAIL).get();
		// then
		Assertions.assertEquals(MockConstants.MOCK_USER_EMAIL, user.getEmail());
		Assertions.assertEquals(MockConstants.MOCK_USER_NAME, user.getName());
		Assertions.assertEquals(MockConstants.MOCK_PHONE_NUMBER, user.getPhoneNumber());
		Assertions.assertEquals(response.getMessage(), "회원가입에 성공했습니다.");

	}

	@DisplayName("이미 가입한 회원이어서 회원가입을 실패한다")
	@Test
	void signUpWithAlreadySignedUpUser() {
		//given
		User user = MockFactory.createMockUser();
		userRepository.save(user);
		// when
		ApiResponse<String> response = authService.signUp(signUpRequestDto);
		// then
		Assertions.assertEquals(response.getMessage(), "이미 가입된 이메일 입니다.");
	}

	@DisplayName("이메일 인증 코드가 옳지 않아 회원가입을 실패한다")
	@Test
	void signUpWithWrongVerificationCode() {
		//given
		Token token = Token.of(MockConstants.MOCK_USER_EMAIL, verificationCode,
			MockConstants.MOCK_TOKEN_EXPIRATION_TIME);
		tokenRedisRepository.save(token);
		SignUpRequestDto dto = SignUpRequestDto.of(MockConstants.MOCK_USER_EMAIL,
			MockConstants.MOCK_USER_PASSWORD,
			MockConstants.MOCK_USER_NAME, MockConstants.MOCK_USER_NICKNAME,
			MockConstants.MOCK_STUDENT_ID,
			MockConstants.MOCK_MAJOR, MockConstants.MOCK_INTEREST, MockConstants.MOCK_PHONE_NUMBER,
			MockConstants.MOCK_INTRODUCTION, false, "wrongCode");
		// when
		ApiResponse<String> response = authService.signUp(dto);
		// then
		Assertions.assertEquals(response.getMessage(), "인증 코드가 유효하지 않습니다.");
	}


}
