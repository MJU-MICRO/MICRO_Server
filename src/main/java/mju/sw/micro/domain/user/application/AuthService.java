package mju.sw.micro.domain.user.application;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import mju.sw.micro.domain.user.dao.TokenRedisRepository;
import mju.sw.micro.domain.user.dao.UserRepository;
import mju.sw.micro.domain.user.domain.Role;
import mju.sw.micro.domain.user.domain.Token;
import mju.sw.micro.domain.user.domain.User;
import mju.sw.micro.domain.user.dto.request.CodeVerifyRequestDto;
import mju.sw.micro.domain.user.dto.request.EmailSendRequestDto;
import mju.sw.micro.domain.user.dto.request.SignUpRequestDto;
import mju.sw.micro.global.common.response.ApiResponse;
import mju.sw.micro.global.constants.EmailConstants.VerifyEmailConstants;
import mju.sw.micro.global.error.exception.ErrorCode;
import mju.sw.micro.global.utils.CodeUtil;
import mju.sw.micro.global.utils.MailUtil;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

	private final UserRepository userRepository;
	private final MailUtil mailUtil;
	private final TokenRedisRepository tokenRedisRepository;
	//	private final PasswordEncoder encoder;

	public ApiResponse<String> sendEmailAndSaveCode(EmailSendRequestDto dto) {
		Optional<Token> optionalToken = tokenRedisRepository.findByEmail(dto.getEmail());
		optionalToken.ifPresent(token -> tokenRedisRepository.deleteById(token.getId()));

		String code = CodeUtil.generateRandomCode();
		mailUtil.sendMessage(dto.getEmail(), VerifyEmailConstants.EMAIL_TITLE,
			VerifyEmailConstants.EMAIL_CONTENT_HTML, code);
		Token token = Token.of(dto.getEmail(), code, VerifyEmailConstants.EMAIL_TOKEN_EXPIRATION_TIME);
		tokenRedisRepository.save(token);
		return ApiResponse.ok("이메일을 성공적으로 보냈습니다.");
	}

	public ApiResponse<Boolean> verifyCode(CodeVerifyRequestDto dto) {
		Optional<Token> optionalToken = tokenRedisRepository.findByEmail(dto.getEmail());
		return optionalToken.map(
				token -> ApiResponse.ok(token.getVerificationCode().equals(dto.getCode())))
			.orElseGet(() -> ApiResponse.withError(ErrorCode.INVALID_CODE, false));
	}

	@Transactional
	public ApiResponse<String> signUp(SignUpRequestDto dto) {
		if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
			return ApiResponse.withError(ErrorCode.ALREADY_SIGN_UP_EMAIL);
		}
		boolean isVerified = verifyCode(CodeVerifyRequestDto.of(dto.getEmail(), dto.getCode())).getData();
		if (!isVerified) {
			return ApiResponse.withError(ErrorCode.INVALID_CODE);
		}

		User user = User.builder()
			.email(dto.getEmail())
			.password(dto.getPassword())
			.name(dto.getName())
			.nickName(dto.getNickName())
			.studentId(dto.getStudentId())
			.major(dto.getMajor())
			.interest(dto.getInterest())
			.phoneNumber(dto.getPhoneNumber())
			.introduction(dto.getIntroduction())
			.notification(dto.getNotification())
			.activated(false)
			.build();
		user.addRole(Role.USER);
		userRepository.save(user);
		return ApiResponse.ok("회원가입에 성공했습니다.");
	}

}
