package com.flab.todo.common.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.flab.todo.member.MemberService;

@EnableWebSecurity
public class SecurityConfig {
	private final UserDetailsService userDetailsService;
	private final UnauthorizedEntryPoint unauthorizedEntryPoint;

	public SecurityConfig(UserDetailsService userDetailsService, UnauthorizedEntryPoint unauthorizedEntryPoint) {
		this.userDetailsService = userDetailsService;
		this.unauthorizedEntryPoint = unauthorizedEntryPoint;
	}

	@Bean
	public CustomAuthenticationProvider customAuthenticationProvider() {
		return new CustomAuthenticationProvider(userDetailsService, this.passwordEncoder());
	}

	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder,
		MemberService memberService) throws Exception {
		return http.getSharedObject(AuthenticationManagerBuilder.class)
			.userDetailsService(userDetailsService)
			.passwordEncoder(bCryptPasswordEncoder)
			.and()
			.authenticationProvider(customAuthenticationProvider())
			.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.authorizeRequests()
			.anyRequest()
			.permitAll()
			.and()
			.httpBasic()
			.and()
			.sessionManagement()
			.maximumSessions(1) // 설정한 최대 허용 세션의 수가 되었을 때 추가적인 인증 요청(세션 생성)이 있을 경우 어떻게 처리를 할지 정하는 api
			.maxSessionsPreventsLogin(true)
			.and()
			.sessionCreationPolicy(SessionCreationPolicy.ALWAYS); // 항상 HttpSesion 을 만든다.

		http.csrf()
			.disable()
			.headers()
			.frameOptions()
			.disable()
			.and()
			.formLogin()
			.disable()
			.exceptionHandling()
			.authenticationEntryPoint(unauthorizedEntryPoint);
		return http.build();
	}
}
