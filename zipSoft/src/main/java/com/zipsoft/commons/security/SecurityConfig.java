package com.zipsoft.commons.security;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.DispatcherType;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	private final JwtAuthenticationEntryPoint unauthorizedHandler;
	
	private static final String[] WHITE_LIST = {
            "/users/**",
            "/test/**",
            "/login"
    };
	
	public SecurityConfig (JwtAuthenticationEntryPoint unauthorizedHandler) {
		this.unauthorizedHandler = unauthorizedHandler;
	}
	
	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
		// js, css, image 설정은 보안 설정의 영향 밖에 있도록 만들어주는 설정.
		return (web) -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		http.csrf(csrf -> csrf.disable());
        http.sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.exceptionHandling(e -> e.authenticationEntryPoint(unauthorizedHandler));
        http.authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(WHITE_LIST).permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated());
        http.httpBasic(Customizer.withDefaults());
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
	
	}

	
}
