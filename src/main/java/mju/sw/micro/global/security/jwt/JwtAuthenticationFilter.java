package mju.sw.micro.global.security.jwt;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mju.sw.micro.domain.user.dao.BlackListTokenRedisRepository;
import mju.sw.micro.domain.user.domain.Role;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	private final JwtService jwtService;
	private final BlackListTokenRedisRepository blackListTokenRedisRepository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		Optional<String> optionalToken = jwtService.resolveToken(request);
		log.warn(optionalToken + "optionalToken");
		if (optionalToken.isEmpty()) {
			log.warn("isEmpty");
			filterChain.doFilter(request, response);
			return;
		}
		String token = optionalToken.get();
		if (!jwtService.isTokenValid(token)) {
			log.warn("isTokenValid");
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired token");
			return;
		}
		if (blackListTokenRedisRepository.findByBlackListAccessToken(token).isPresent()) {
			log.warn("Access Token is in BlackList");
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access Token is in BlackList");
			return;
		}
		if (!jwtService.isAccessToken(token)) {
			log.warn("isAccessToken");
			filterChain.doFilter(request, response);
			return;
		}
		log.info("Authentication is ready");
		Authentication authentication = jwtService.getAuthentication(token);
		if (authentication == null || hasBannedRole(authentication.getAuthorities())) {
			return;
		}
		log.info("Authentication is ready!");
		SecurityContextHolder.getContext().setAuthentication(authentication);
		filterChain.doFilter(request, response);
	}

	private boolean hasBannedRole(Collection<? extends GrantedAuthority> authorities) {
		return authorities.stream().anyMatch(authority -> authority.getAuthority().equals(Role.ROLE_BANNED.name()));
	}
}
