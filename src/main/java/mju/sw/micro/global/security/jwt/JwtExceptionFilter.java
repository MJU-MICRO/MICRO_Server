package mju.sw.micro.global.security.jwt;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtExceptionFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		try {
			filterChain.doFilter(request, response);
		} catch (JwtException ex) {
			setErrorResponse(HttpStatus.UNAUTHORIZED, response, ex);
		}
	}

	public void setErrorResponse(HttpStatus status, HttpServletResponse res, Throwable ex) throws IOException {
		res.setStatus(status.value());
		res.setContentType("application/json; charset=UTF-8");

		JwtExceptionResponse jwtExceptionResponse = new JwtExceptionResponse(ex.getMessage(), HttpStatus.UNAUTHORIZED);
		res.getWriter().write(jwtExceptionResponse.convertToJson());
	}
}
