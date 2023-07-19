package mju.sw.micro.domain.user.application;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import mju.sw.micro.domain.user.dao.BlackListTokenRedisRepository;
import mju.sw.micro.domain.user.dao.RefreshTokenRedisRepository;
import mju.sw.micro.domain.user.domain.BlackListAccessToken;
import mju.sw.micro.domain.user.domain.RefreshToken;
import mju.sw.micro.domain.user.dto.request.LogoutRequestDto;
import mju.sw.micro.global.common.response.ApiResponse;
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

	public ApiResponse<String> logout(LogoutRequestDto dto, CustomUserDetails userDetails) {
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
}
