package com.zipsoft.commons.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
	
	private final UserDetailService userDetailService;
	
	private final PasswordEncoder passwordEncoder;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		// TODO Auto-generated method stub
		String loginId = authentication.getName();
        String password = (String) authentication.getCredentials();
        
        UserPrincipal user = (UserPrincipal) userDetailService.loadUserByUsername(loginId);
        
        /*if (isNotMatches(password, user.getPassword())) {
        	throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }*/
        
		return new UsernamePasswordAuthenticationToken(user, password);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
	
	private boolean isNotMatches(String password, String encodePassword) {
        return !passwordEncoder.matches(password, encodePassword);
    }

}
