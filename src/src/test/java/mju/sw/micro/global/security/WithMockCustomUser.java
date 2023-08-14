package mju.sw.micro.global.security;

import static mju.sw.micro.global.utils.MockConstants.*;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.security.test.context.support.WithSecurityContext;

import mju.sw.micro.domain.user.domain.Role;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockCustomUserSecurityContextFactory.class)
public @interface WithMockCustomUser {
	Role role() default Role.ROLE_USER;

	String name() default MOCK_USER_NAME;

	String email() default MOCK_USER_EMAIL;

	String phoneNumber() default MOCK_PHONE_NUMBER;

	String major() default MOCK_MAJOR;

	String studentId() default MOCK_STUDENT_ID;

	String nickName() default MOCK_USER_NICKNAME;

	String introduction() default MOCK_INTRODUCTION;

	String interest() default MOCK_INTEREST;

	String password() default "{noop}password";
}
