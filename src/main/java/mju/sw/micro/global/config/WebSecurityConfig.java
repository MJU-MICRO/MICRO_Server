package mju.sw.micro.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import lombok.RequiredArgsConstructor;
import mju.sw.micro.domain.user.domain.Role;
import mju.sw.micro.global.security.jwt.JwtAuthenticationFilter;
import mju.sw.micro.global.security.jwt.JwtService;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
	private final JwtService jwtService;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.httpBasic(AbstractHttpConfigurer::disable)
			.csrf(AbstractHttpConfigurer::disable)
			.formLogin(AbstractHttpConfigurer::disable)
			.sessionManagement(
				sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authorizeHttpRequests(
				authorizeHttpRequests -> authorizeHttpRequests
					.requestMatchers(new AntPathRequestMatcher("/"))
					.permitAll()
					.requestMatchers(new AntPathRequestMatcher("/api/auth/**"))
					.anonymous()
					.requestMatchers(new AntPathRequestMatcher("/api/admin/**"))
					.hasRole(Role.ROLE_ADMIN.getKey())
					.requestMatchers(new AntPathRequestMatcher("/api/president/**"))
					.hasRole(Role.ROLE_PRESIDENT.getKey())
					.anyRequest()
					.authenticated())
			.addFilterBefore(new JwtAuthenticationFilter(jwtService), UsernamePasswordAuthenticationFilter.class);
		//TODO: 인증이 필요한 uri에 게스트가 접근하면 처리할 동작을 정의한 핸들러와, 인증된 사용자가 인가되지 않은 페이지에 접근할 때 처리할 핸들러 추가
		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
}
