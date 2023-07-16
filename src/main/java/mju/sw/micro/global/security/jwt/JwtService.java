package mju.sw.micro.global.security.jwt;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mju.sw.micro.domain.user.domain.User;
import mju.sw.micro.global.constants.JwtConstants;
import mju.sw.micro.global.security.MicroUserDetailService;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtService {

	@Value("${jwt.secret.key}")
	private String secretKey;
	private final MicroUserDetailService userDetailsService;

	public String createAccessToken(String email) {
		Date now = new Date();
		return JWT.create()
			.withSubject(JwtConstants.ACCESS_TOKEN_SUBJECT)
			.withExpiresAt(new Date(now.getTime() + JwtConstants.ACCESS_TOKEN_EXPIRATION_TIME))
			.withClaim(JwtConstants.CLAIM_EMAIL, email)
			.sign(Algorithm.HMAC512(secretKey));
	}

	public String createRefreshToken(String email) {
		Date now = new Date();
		return JWT.create()
			.withSubject(JwtConstants.REFRESH_TOKEN_SUBJECT)
			.withClaim(JwtConstants.CLAIM_EMAIL, email)
			.withExpiresAt(new Date(now.getTime() + JwtConstants.REFRESH_TOKEN_EXPIRATION_TIME))
			.sign(Algorithm.HMAC512(secretKey));
	}

	// Spring Security 인증과정에서 권한확인을 위한 기능
	public Authentication getAuthentication(String accessToken) {
		String email = extractEmail(accessToken).orElseThrow(() -> new IllegalArgumentException("액세스 토큰이 유효하지 않습니다."));
		UserDetails userDetails = userDetailsService.loadUserByUsername(email);
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}

	public Optional<String> extractEmail(String accessToken) {
		return Optional.ofNullable(JWT.require(Algorithm.HMAC512(secretKey))
			.build()
			.verify(accessToken)
			.getClaim(JwtConstants.CLAIM_EMAIL)
			.asString());
	}

	// Authorization Header를 통해 토큰을 가져온다.
	public String getToken(HttpServletRequest request) {
		return request.getHeader("Authorization");
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

	public String createTokensAndAddHeaders(User user, boolean isAutoLogin, HttpServletResponse response) {
		final String access = "access";
		final String refresh = "refresh";

		String accessToken = this.createAccessToken(user.getEmail());
		response.addHeader(access, JwtConstants.PREFIX_BEARER + accessToken);
		String refreshToken = null;
		if (isAutoLogin) {
			refreshToken = this.createRefreshToken(user.getEmail());
			response.addHeader(refresh, refreshToken);
		}
		return refreshToken;
	}
}
