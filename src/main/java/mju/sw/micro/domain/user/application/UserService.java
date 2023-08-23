package mju.sw.micro.domain.user.application;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import mju.sw.micro.domain.user.dao.BlackListTokenRedisRepository;
import mju.sw.micro.domain.user.dao.RefreshTokenRedisRepository;
import mju.sw.micro.domain.user.dao.UserRepository;
import mju.sw.micro.domain.user.domain.BlackListAccessToken;
import mju.sw.micro.domain.user.domain.RefreshToken;
import mju.sw.micro.domain.user.domain.User;
import mju.sw.micro.domain.user.dto.request.LogoutRequestDto;
import mju.sw.micro.domain.user.dto.request.UserModifyRequestDto;
import mju.sw.micro.domain.user.dto.request.UserPasswordRequestDto;
import mju.sw.micro.domain.user.dto.response.UserInfoResponseDto;
import mju.sw.micro.global.adapter.MailService;
import mju.sw.micro.global.adapter.S3Uploader;
import mju.sw.micro.global.common.response.ApiResponse;
import mju.sw.micro.global.constants.EmailConstants;
import mju.sw.micro.global.error.exception.ErrorCode;
import mju.sw.micro.global.security.CustomUserDetails;
import mju.sw.micro.global.security.jwt.JwtService;
import mju.sw.micro.global.utils.TimeUtil;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
	private final RefreshTokenRedisRepository refreshTokenRedisRepository;
	private final BlackListTokenRedisRepository blackListTokenRedisRepository;
	private final JwtService jwtService;
	private final UserRepository userRepository;
	private final PasswordEncoder encoder;
	private final S3Uploader s3Uploader;
	private final MailService mailService;

	public ApiResponse<Void> logout(LogoutRequestDto dto, CustomUserDetails userDetails) {
		String accessToken = dto.getAccessToken();
		if (!jwtService.isTokenValid(accessToken)) {
			return ApiResponse.withError(ErrorCode.INVALID_TOKEN);
		}
		Optional<RefreshToken> optionalToken = refreshTokenRedisRepository.findById(userDetails.getEmail());
		if (optionalToken.isEmpty()) {
			return ApiResponse.withError(ErrorCode.TOKEN_NOT_FOUND);
		}
		refreshTokenRedisRepository.delete(optionalToken.get());
		Long expiration = jwtService.getExpirationTime(accessToken);
		blackListTokenRedisRepository.save(
			BlackListAccessToken.of(expiration, accessToken, TimeUtil.generateExpiration(expiration)));
		return ApiResponse.ok("로그아웃 되었습니다.");
	}

	public ApiResponse<UserInfoResponseDto> getUserInfo(String email) {
		Optional<User> optionalUser = userRepository.findByEmail(email);
		if (optionalUser.isEmpty()) {
			return ApiResponse.withError(ErrorCode.NOT_FOUND);
		}
		User user = optionalUser.get();
		return ApiResponse.ok("회원 정보 조회 완료",
			UserInfoResponseDto.builder()
				.id(user.getId())
				.email(user.getEmail())
				.profileImageUrl(user.getProfileImageUrl())
				.name(user.getName())
				.major(user.getMajor())
				.introduction(user.getIntroduction())
				.build());
	}

	@Transactional
	public ApiResponse<Void> modifyUserInfo(UserModifyRequestDto dto, MultipartFile imageFile, String email) {
		Optional<User> optionalUser = userRepository.findByEmail(email);
		if (optionalUser.isEmpty()) {
			return ApiResponse.withError(ErrorCode.NOT_FOUND);
		}
		User user = optionalUser.get();
		String profileImageUrl = uploadImage(imageFile).getData();
		if (profileImageUrl != null) {
			user.updateUserProfile(profileImageUrl);
		}
		String updatedIntroduction = dto.getIntroduction();
		if (!StringUtils.isBlank(updatedIntroduction)) {
			user.updateIntroduction(updatedIntroduction);
		}
		user.updateUser(dto);
		return ApiResponse.ok("회원 정보 수정 완료");
	}

	@Transactional
	public ApiResponse<Void> modifyUserPassword(UserPasswordRequestDto dto, String email) {
		Optional<User> optionalUser = userRepository.findByEmail(email);
		if (optionalUser.isEmpty()) {
			return ApiResponse.withError(ErrorCode.NOT_FOUND);
		}
		User user = optionalUser.get();
		if (!encoder.matches(dto.getOriginPassword(), user.getPassword())) {
			return ApiResponse.withError(ErrorCode.UNAUTHORIZED);
		}
		user.updatePassword(encoder.encode(dto.getUpdatePassword()));
		return ApiResponse.ok("회원 비밀번호 변경 완료");
	}

	@Transactional
	public ApiResponse<Void> deleteUser(String email) {
		Optional<User> optionalUser = userRepository.findByEmail(email);
		if (optionalUser.isEmpty()) {
			return ApiResponse.withError(ErrorCode.NOT_FOUND);
		}
		User user = optionalUser.get();
		userRepository.delete(user);
		mailService.sendMessage(user.getEmail(), EmailConstants.EMAIL_WITHDRAWAL_TITLE,
			EmailConstants.EMAIL_WITHDRAWAL_CONTENT_HTML, user.getEmail());
		return ApiResponse.ok("회원 탈퇴 완료");
	}

	private ApiResponse<String> uploadImage(MultipartFile multipartFile) {
		if (multipartFile == null) {
			return ApiResponse.ok("이미지가 없습니다", null);
		}
		return s3Uploader.uploadFile(multipartFile);
	}

}
