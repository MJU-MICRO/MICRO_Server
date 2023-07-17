package mju.sw.micro.global.security;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class EntryPointUnauthorizedHandler implements AuthenticationEntryPoint {
	//권한이 없는 접근이나 인증되지 않은 접근 시 예외처리 , ex) Guest가 auth 관련 url 말고 다른 url에 접근할 때 , 액세스 토큰이 아닌 리프레시 토큰으로 접근할 때
	//권한이 ROLE_BANNED인 사용자가 접근할 때
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException authException) throws IOException, SecurityException {
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getLocalizedMessage());
	}
}
