package mju.sw.micro.global.config.security;

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
import mju.sw.micro.global.config.CorsConfig;
import mju.sw.micro.global.security.MicroAccessDeniedHandler;
import mju.sw.micro.global.security.jwt.JwtAuthenticationFilter;
import mju.sw.micro.global.security.jwt.JwtExceptionFilter;

@Configuration
@EnableWebSecurity(debug = true)
@RequiredArgsConstructor
public class WebSecurityConfig {
	private final MicroAccessDeniedHandler accessDeniedHandler;
	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	private final JwtExceptionFilter jwtExceptionFilter;
	private final CorsConfig corsConfig;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.httpBasic(AbstractHttpConfigurer::disable)
			.csrf(AbstractHttpConfigurer::disable)
			.formLogin(AbstractHttpConfigurer::disable)
			.sessionManagement(
				sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.cors(cors -> cors.configurationSource(corsConfig.corsConfigurationSource()))
			.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
			.addFilterBefore(jwtExceptionFilter, JwtAuthenticationFilter.class)
			.authorizeHttpRequests(
				authorizeHttpRequests -> authorizeHttpRequests.requestMatchers(
						new AntPathRequestMatcher("/swagger-ui/**"))
					.permitAll()
					.requestMatchers(new AntPathRequestMatcher("/v3/api-docs/**"))
					.permitAll()
					.requestMatchers(new AntPathRequestMatcher("/api/auth/**"))
					.anonymous()
					.requestMatchers(new AntPathRequestMatcher("/api/admin/**"))
					.hasRole(Role.ROLE_ADMIN.getKey())
					.requestMatchers(new AntPathRequestMatcher("/api/president/**"))
					.hasRole(Role.ROLE_PRESIDENT.getKey())
					.anyRequest()
					.authenticated())
			.exceptionHandling(exceptionHandling -> exceptionHandling.accessDeniedHandler(accessDeniedHandler));
		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
}
