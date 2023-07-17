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
import mju.sw.micro.domain.user.domain.Role;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	private final JwtService jwtService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		Optional<String> optionalToken = jwtService.extractToken(request);
		if (optionalToken.isEmpty()) {
			filterChain.doFilter(request, response);
			return;
		}
		String token = optionalToken.get();
		if (!jwtService.isTokenValid(token)) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired token");
			return;
		}
		if (!jwtService.isAccessToken(token)) {
			filterChain.doFilter(request, response);
			return;
		}
		Authentication authentication = jwtService.getAuthentication(token);
		if (authentication == null || hasBannedRole(authentication.getAuthorities())) {
			return;
		}
		SecurityContextHolder.getContext().setAuthentication(authentication);
		filterChain.doFilter(request, response);
	}

	private boolean hasBannedRole(Collection<? extends GrantedAuthority> authorities) {
		return authorities.stream().anyMatch(authority -> authority.getAuthority().equals(Role.ROLE_BANNED.name()));
	}
}
