package mju.sw.micro.global.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import lombok.extern.slf4j.Slf4j;
import mju.sw.micro.domain.user.domain.User;

@Slf4j
public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {
	@Override
	public SecurityContext createSecurityContext(WithMockCustomUser customUser) {
		SecurityContext context = SecurityContextHolder.createEmptyContext();

		User user = User.builder()
			.name(customUser.name())
			.email(customUser.email())
			.phoneNumber(customUser.phoneNumber())
			.major(customUser.major())
			.studentId(customUser.studentId())
			.nickName(customUser.nickName())
			.introduction(customUser.introduction())
			.interest(customUser.interest())
			.password(customUser.password())
			.build();
		user.addRole(customUser.role());
		CustomUserDetails principal = new CustomUserDetails(user);
		Authentication auth =
			new UsernamePasswordAuthenticationToken(principal, "", principal.getAuthorities());
		context.setAuthentication(auth);
		return context;
	}
}