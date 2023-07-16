package mju.sw.micro.global.security.jwt;

import java.io.IOException;
import java.util.Collection;

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
		String token = jwtService.resolveToken(request);

		if (token != null && jwtService.isTokenValid(token)) {
			// check access token
			token = token.split(" ")[1].trim();

			Authentication authentication = jwtService.getAuthentication(token);
			if (authentication != null && hasBannedRole(authentication.getAuthorities())) {
				response.sendError(HttpServletResponse.SC_FORBIDDEN, "User is banned");
				return;
			}
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}

		filterChain.doFilter(request, response);
	}

	private boolean hasBannedRole(Collection<? extends GrantedAuthority> authorities) {
		return authorities.stream()
			.anyMatch(authority -> authority.getAuthority().equals(Role.ROLE_BANNED.name()));
	}
}
