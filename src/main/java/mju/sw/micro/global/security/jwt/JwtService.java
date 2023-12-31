package mju.sw.micro.global.security.jwt;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mju.sw.micro.global.constants.JwtConstants;
import mju.sw.micro.global.security.CustomUserDetailService;
import mju.sw.micro.global.security.CustomUserDetails;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtService {
	@Value("${jwt.secret.key}")
	private String secretKey;
	private final CustomUserDetailService userDetailsService;

	public String createAccessToken(String email) {
		Date now = new Date();
		return JWT.create()
			.withSubject(JwtConstants.ACCESS_TOKEN_SUBJECT)
			.withExpiresAt(new Date(now.getTime() + JwtConstants.ACCESS_TOKEN_EXPIRATION_TIME))
			.withClaim(JwtConstants.CLAIM_EMAIL, email)
			.sign(Algorithm.HMAC512(secretKey));
	}

	public String createRefreshToken() {
		Date now = new Date();
		return JWT.create()
			.withSubject(JwtConstants.REFRESH_TOKEN_SUBJECT)
			.withExpiresAt(new Date(now.getTime() + JwtConstants.REFRESH_TOKEN_EXPIRATION_TIME))
			.sign(Algorithm.HMAC512(secretKey));
	}

	// Spring Security 인증과정에서 권한확인을 위한 기능
	public Authentication getAuthentication(String accessToken) {
		String email = extractEmail(accessToken).orElseThrow(() -> new IllegalArgumentException("액세스 토큰이 유효하지 않습니다."));
		CustomUserDetails customUserDetails = (CustomUserDetails)userDetailsService.loadUserByUsername(email);
		return new UsernamePasswordAuthenticationToken(customUserDetails, "", customUserDetails.getAuthorities());
	}

	public Optional<String> extractEmail(String accessToken) {
		return Optional.ofNullable(JWT.require(Algorithm.HMAC512(secretKey))
			.build()
			.verify(accessToken)
			.getClaim(JwtConstants.CLAIM_EMAIL)
			.asString());
	}

	public Optional<String> resolveToken(HttpServletRequest request) {
		return Optional.ofNullable(request.getHeader(JwtConstants.HEADER))
			.filter(token -> token.startsWith(JwtConstants.PREFIX_BEARER))
			.map(token -> token.replace(JwtConstants.PREFIX_BEARER, ""));
	}

	// 토큰 검증
	public boolean isTokenValid(String token) {
		try {
			JWT.require(Algorithm.HMAC512(secretKey)).build().verify(token);
			return true;
		} catch (Exception e) {
			log.error("유효하지 않은 토큰입니다. {}", e.getMessage());
			return false;
		}
	}

	public boolean isAccessToken(String token) {
		try {
			JWT.require(Algorithm.HMAC512(secretKey))
				.withSubject(JwtConstants.ACCESS_TOKEN_SUBJECT)
				.build()
				.verify(token);
			return true;
		} catch (JWTVerificationException e) {
			return false;
		}
	}

	public Long getExpirationTime(String token) {
		Date now = new Date();
		long expiration = JWT.require(Algorithm.HMAC512(secretKey))
			.build()
			.verify(token)
			.getExpiresAt()
			.getTime();
		return expiration - now.getTime();
	}

	public String createTokensAndAddHeaders(String email, HttpServletResponse response) {
		String accessToken = this.createAccessToken(email);
		response.addHeader(JwtConstants.ACCESS_KEY, accessToken);
		String refreshToken = this.createRefreshToken();
		response.addHeader(JwtConstants.REFRESH_KEY, refreshToken);
		return refreshToken;
	}
}
