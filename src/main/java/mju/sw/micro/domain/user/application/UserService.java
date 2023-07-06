package mju.sw.micro.domain.user.application;

import lombok.RequiredArgsConstructor;
import mju.sw.micro.domain.user.dao.UserRepository;
import mju.sw.micro.domain.user.domain.Role;
import mju.sw.micro.domain.user.domain.User;
import mju.sw.micro.domain.user.dto.request.EmailSendRequestDto;
import mju.sw.micro.domain.user.dto.request.SignUpRequestDto;
import mju.sw.micro.domain.user.dto.request.CodeVerifyRequestDto;
import mju.sw.micro.global.common.response.ApiResponse;
import mju.sw.micro.global.constants.EmailConstants.VerifyEmailConstants;
import mju.sw.micro.global.error.exception.ErrorCode;
import mju.sw.micro.global.utils.CodeUtil;
import mju.sw.micro.global.utils.MailUtil;
//import org.springframework.security.crypto.password.PasswordEncoder;
import mju.sw.micro.global.utils.RedisUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

	private final UserRepository userRepository;
	private final MailUtil mailUtil;
	private final RedisUtil redisUtil;
//	private final PasswordEncoder encoder;


	public ApiResponse<String> sendEmailAndSaveCode(EmailSendRequestDto dto) {
		if (redisUtil.existData(dto.getEmail())) {
			redisUtil.deleteData(dto.getEmail());
		}
		String code = CodeUtil.generateRandomCode();
		mailUtil.sendMessage(dto.getEmail(), VerifyEmailConstants.EMAIL_TITLE,
			VerifyEmailConstants.EMAIL_CONTENT_HTML, code);
		redisUtil.setDataExpire(dto.getEmail(), code, 60 * 5L);
		return ApiResponse.ok("이메일을 성공적으로 보냈습니다.");
	}

	public ApiResponse<Boolean> verifyCode(CodeVerifyRequestDto dto) {
		String emailCode = redisUtil.getData(dto.getEmail());
		if (emailCode == null) {
			return ApiResponse.ok(false);
		}
		return ApiResponse.ok(redisUtil.getData(dto.getEmail()).equals(dto.getCode()));
	}
	@Transactional
	public ApiResponse<String> signUp(SignUpRequestDto dto) {
		if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
			return ApiResponse.withError(ErrorCode.INVALID_EMAIL);
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
			.build();
		user.addRole(Role.USER);
		userRepository.save(user);
		return ApiResponse.ok("회원가입에 성공했습니다.");
	}

}
