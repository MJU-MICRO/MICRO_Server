package mju.sw.micro.global.security;

import java.io.IOException;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class MicroAccessDeniedHandler implements AccessDeniedHandler {
	//인증된 사용자가 인가되지 않은 페이지 접근 시 예외처리 ,ex) User가 Admin url 접근 시
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
		AccessDeniedException accessDeniedException) throws IOException {
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().write("권한이 없는 사용자입니다.");
	}
}
