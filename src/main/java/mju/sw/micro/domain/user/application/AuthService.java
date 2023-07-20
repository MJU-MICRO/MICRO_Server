package mju.sw.micro.domain.user.application;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import mju.sw.micro.domain.user.dao.EmailCodeRedisRepository;
import mju.sw.micro.domain.user.dao.RefreshTokenRedisRepository;
import mju.sw.micro.domain.user.dao.UserRepository;
import mju.sw.micro.domain.user.domain.EmailCode;
import mju.sw.micro.domain.user.domain.RefreshToken;
import mju.sw.micro.domain.user.domain.Role;
import mju.sw.micro.domain.user.domain.User;
import mju.sw.micro.domain.user.dto.request.CodeVerifyRequestDto;
import mju.sw.micro.domain.user.dto.request.EmailSendRequestDto;
import mju.sw.micro.domain.user.dto.request.LoginRequestDto;
import mju.sw.micro.domain.user.dto.request.RefreshTokenRequestDto;
import mju.sw.micro.domain.user.dto.request.SignUpRequestDto;
import mju.sw.micro.domain.user.dto.response.LoginResponseDto;
import mju.sw.micro.domain.user.dto.response.RefreshTokenResponseDto;
import mju.sw.micro.global.common.response.ApiResponse;
import mju.sw.micro.global.constants.EmailConstants.VerifyEmailConstants;
import mju.sw.micro.global.constants.JwtConstants;
import mju.sw.micro.global.error.exception.ErrorCode;
import mju.sw.micro.global.security.jwt.JwtService;
import mju.sw.micro.global.utils.CodeUtil;
import mju.sw.micro.global.utils.MailUtil;
import mju.sw.micro.global.utils.TimeUtil;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

	private final UserRepository userRepository;
	private final MailUtil mailUtil;
	private final RefreshTokenRedisRepository refreshTokenRedisRepository;
	private final EmailCodeRedisRepository emailCodeRedisRepository;
	private final PasswordEncoder encoder;
	private final JwtService jwtService;

	public ApiResponse<String> sendEmailAndSaveCode(EmailSendRequestDto dto) {
		String emailCode = CodeUtil.generateRandomCode();
		mailUtil.sendMessage(dto.getEmail(), VerifyEmailConstants.EMAIL_TITLE, VerifyEmailConstants.EMAIL_CONTENT_HTML,
			emailCode);
		String expirationDate = TimeUtil.generateExpiration(VerifyEmailConstants.EMAIL_TOKEN_EXPIRATION_TIME);
		EmailCode code = EmailCode.of(dto.getEmail(), emailCode, VerifyEmailConstants.EMAIL_TOKEN_EXPIRATION_TIME,
			expirationDate);
		emailCodeRedisRepository.save(code);
		return ApiResponse.ok("이메일을 성공적으로 보냈습니다.");
	}

	public ApiResponse<Boolean> verifyCode(CodeVerifyRequestDto dto) {
		Optional<EmailCode> optionalEmailCode = emailCodeRedisRepository.findById(dto.getEmail());
		if (optionalEmailCode.isEmpty()) {
			return ApiResponse.withError(ErrorCode.INVALID_TOKEN, false);
		}
		return ApiResponse.ok(optionalEmailCode.get().getVerificationCode().equals(dto.getEmailCode()));
	}

	@Transactional
	public ApiResponse<String> signUp(SignUpRequestDto dto) {
		if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
			return ApiResponse.withError(ErrorCode.ALREADY_SIGN_UP_EMAIL);
		}
		boolean isVerified = verifyCode(CodeVerifyRequestDto.of(dto.getEmail(), dto.getCode())).getData();
		if (!isVerified) {
			return ApiResponse.withError(ErrorCode.INVALID_TOKEN);
		}

		User user = User.builder()
			.email(dto.getEmail())
			.password(encoder.encode(dto.getPassword()))
			.name(dto.getName())
			.nickName(dto.getNickName())
			.studentId(dto.getStudentId())
			.major(dto.getMajor())
			.interest(dto.getInterest())
			.phoneNumber(dto.getPhoneNumber())
			.introduction(dto.getIntroduction())
			.notification(dto.getNotification())
			.build();
		user.addRole(Role.ROLE_USER);
		userRepository.save(user);
		return ApiResponse.ok("회원가입에 성공했습니다.");
	}

	public ApiResponse<LoginResponseDto> login(LoginRequestDto dto) {
		Optional<User> optionalUser = userRepository.findByEmail(dto.getEmail());
		if (optionalUser.isEmpty()) {
			return ApiResponse.withError(ErrorCode.AUTH_NOT_FOUND);
		}
		User user = optionalUser.get();
		if (!encoder.matches(dto.getPassword(), user.getPassword())) {
			return ApiResponse.withError(ErrorCode.INVALID_PASSWORD);
		}
		String accessToken = jwtService.createAccessToken(dto.getEmail());
		String refreshToken = jwtService.createRefreshToken();
		String expirationDate = TimeUtil.generateExpiration(JwtConstants.REFRESH_TOKEN_EXPIRATION_TIME);
		refreshTokenRedisRepository.save(
			RefreshToken.of(user.getEmail(), JwtConstants.REFRESH_TOKEN_EXPIRATION_TIME, refreshToken, expirationDate));
		return ApiResponse.ok("로그인에 성공했습니다.", LoginResponseDto.of(accessToken, refreshToken));
	}

	public ApiResponse<RefreshTokenResponseDto> reissueJwtTokens(RefreshTokenRequestDto dto) {
		if (!jwtService.isTokenValid(dto.getRefreshToken())) {
			return ApiResponse.withError(ErrorCode.INVALID_TOKEN);
		}
		Optional<RefreshToken> optionalToken = refreshTokenRedisRepository.findByRefreshToken(dto.getRefreshToken());
		if (optionalToken.isEmpty()) {
			return ApiResponse.withError(ErrorCode.TOKEN_NOT_FOUND);
		}
		RefreshToken refreshToken = optionalToken.get();
		String reIssuedAccessToken = jwtService.createAccessToken(refreshToken.getEmail());
		String reIssuedRefreshToken = jwtService.createRefreshToken();
		refreshTokenRedisRepository.delete(refreshToken);
		refreshTokenRedisRepository.save(
			RefreshToken.of(refreshToken.getEmail(), JwtConstants.REFRESH_TOKEN_EXPIRATION_TIME,
				reIssuedRefreshToken, TimeUtil.generateExpiration(JwtConstants.REFRESH_TOKEN_EXPIRATION_TIME)));
		return ApiResponse.ok("토큰 재발급에 성공했습니다.", RefreshTokenResponseDto.of(reIssuedAccessToken, reIssuedRefreshToken));
	}
}
